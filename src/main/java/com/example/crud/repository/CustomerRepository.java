package com.example.crud.repository;

import com.example.crud.models.Customer;
import com.example.crud.models.FilterQuery;
import com.example.crud.models.FilterTerms;
import com.example.crud.util.JerseyRequest;
import com.fasterxml.jackson.databind.JsonNode;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.stereotype.Repository;

import java.util.*;


@Repository
public class CustomerRepository {
    private final JerseyRequest request = new JerseyRequest();

    public Object getCustomers(FilterQuery filter) throws Exception{
        List<Map<String, Object>> listTerms = new ArrayList<Map<String, Object>>();
        Map<String, Object> finalResult = new LinkedHashMap<>();

        for (FilterTerms query : filter.getQuery()) {
            Map<String, Object> terms = new HashMap<>();
            Map<String, Object> field = new HashMap<>();
            Map<String, Object> search = new HashMap<>();

            if (query.getField().equals("search")){
                search.put("default_field", "*");
                search.put("query", "*" + query.getValue() + "*");
                terms.put("query_string", search);
                listTerms.add(terms);
            }else {
                field.put("default_field", query.getField());
                field.put("query", "*" + query.getValue() + "*");
                terms.put("query_string", field);
                listTerms.add(terms);
            }
        }

        ObjectMapper mapper = new ObjectMapper();
        String query = mapper.writeValueAsString(listTerms);

        Integer size = filter.getSize();
        Integer page = filter.getPage() - 1;
        Integer from = size * page;
        String body = "{\"from\":" + from + ",\"size\":" + size + ",\"query\":{\"bool\":{\"must\":" + query + "}}}";

        JsonNode queryResult = request.getWithBody("http://192.168.20.90:9200/customers/_search", body).get("hits");

        List<JsonNode> objList = new ArrayList<JsonNode>();
        if(queryResult != null){
            for (JsonNode object : queryResult.get("hits")) {
                objList.add(object.get("_source"));
            }
            finalResult.put("total_customer_list", objList.size());
            finalResult.put("total_customer_data", queryResult.get("total").get("value").asInt());
            finalResult.put("Customer_list", objList);

            return finalResult;
        }
        return null;
    }

    public Object findById(String paramsId) throws Exception{
        JsonNode customer = request.findById("http://192.168.20.90:9200/customers/_doc/" + paramsId);

        if(customer != null) {
            if(customer.get("found").asBoolean()){
                return customer.get("_source");
            }
            return "Data Not Found";
        }

        return null;
    }

    public Object create(Customer paramsCustomer) throws Exception{
        String hasheId = hasheId(paramsCustomer);
        JsonNode checkCustomer = request.findById("http://192.168.20.90:9200/customers/_doc/" + hasheId);

        if(checkCustomer != null){
            if(!checkCustomer.get("found").asBoolean() ){
                Map<String, Object> customer = new LinkedHashMap<>();

                customer.put("id", hasheId);
                customer.put("username", paramsCustomer.getUsername());
                customer.put("first_name", paramsCustomer.getFirst_name());
                customer.put("last_name", paramsCustomer.getLast_name());
                customer.put("gender", paramsCustomer.getGender());
                customer.put("email", paramsCustomer.getEmail());
                customer.put("birth_date", paramsCustomer.getBirth_date());

                JsonNode createCustomer = request.create("http://192.168.20.90:9200/customers/_doc/" + hasheId, customer);
                JsonNode response = request.findById("http://192.168.20.90:9200/customers/_doc/" + hasheId);
                return response.get("_source");
            }
            return "Customer already exist";
        }
        return null;
    }

    public String hasheId(Customer paramsCustomer){
        String dataTohash = paramsCustomer.getId() + paramsCustomer.getUsername() + paramsCustomer.getGender() + paramsCustomer.getEmail() + paramsCustomer.getFirst_name() + paramsCustomer.getLast_name() + paramsCustomer.getBirth_date();
        return DigestUtils.md5Hex(dataTohash);
    }

    public Object update(Customer paramsCustomer) throws Exception{
        JsonNode checkCustomer = request.findById("http://192.168.20.90:9200/customers/_doc/" + paramsCustomer.getId());
        if(checkCustomer != null){
            if( checkCustomer.get("found").asBoolean()){
                Map<String, Object> bodyMap = new LinkedHashMap<>();
                Map<String, Object> customerMap = new LinkedHashMap<>();

                Optional.ofNullable(paramsCustomer.getUsername()).ifPresent(username -> customerMap.put("username", username));
                Optional.ofNullable(paramsCustomer.getFirst_name()).ifPresent(firstName -> customerMap.put("first_name", firstName));
                Optional.ofNullable(paramsCustomer.getLast_name()).ifPresent(lastName -> customerMap.put("last_name", lastName));
                Optional.ofNullable(paramsCustomer.getGender()).ifPresent(gender -> customerMap.put("gender", gender));
                Optional.ofNullable(paramsCustomer.getEmail()).ifPresent(email -> customerMap.put("email", email));
                Optional.ofNullable(paramsCustomer.getBirth_date()).ifPresent(birthDate -> customerMap.put("birth_date", birthDate));

                bodyMap.put("doc", customerMap);

                JsonNode updateCustomer = request.update("http://192.168.20.90:9200/customers/_update/" + paramsCustomer.getId(), bodyMap);
                JsonNode getCustomer = request.findById("http://192.168.20.90:9200/customers/_doc/" + paramsCustomer.getId());
                return getCustomer.get("_source");
            }
            return "Data Not Found";
        }
        return null;
    }

    public Object delete(String paramsId) throws Exception{
        JsonNode customer = request.findById("http://192.168.20.90:9200/customers/_doc/" + paramsId);
        if(customer != null ){
            if(customer.get("found").asBoolean()){
                JsonNode deleteCustomer = request.delete("http://192.168.20.90:9200/customers/_doc/" + paramsId);
                return "Successfully";
            }
            return "Data Not Found";
        }
        return null;
    }
}
