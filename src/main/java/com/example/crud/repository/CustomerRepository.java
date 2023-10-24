package com.example.crud.repository;

import com.example.crud.models.Customer;
import com.example.crud.util.JerseyRequest;
import com.fasterxml.jackson.databind.JsonNode;

import org.springframework.stereotype.Repository;

import java.util.*;


@Repository
public class CustomerRepository {
    private JerseyRequest request = new JerseyRequest();

    public Object getCustomers() throws Exception{
        Map<String, Object> finalResult = new LinkedHashMap<>();
        JsonNode response = request.getCustomers("http://192.168.20.90:9200/customers/_search");

        List<JsonNode> customers = new ArrayList<>();
        for (JsonNode customer : response.get("hits").get("hits")) {
            customers.add(customer.get("_source"));
        }

        finalResult.put("total_data", response.get("hits").get("total").get("value"));
        finalResult.put("data", customers);

        return finalResult;
    }

    public Object findById(String paramsId) throws Exception{
        try{
            String queryTerm = "{\"query\":{\"term\":{\"id\":\""+ paramsId +"\"}}}";
            JsonNode customer = request.getCustomersWithBody("http://192.168.20.90:9200/customers/_search/", queryTerm);

            if(customer != null){
                String replaceId = customer.get("hits").get("hits").get(0).get("_id").asText().replace("\"", "");
                JsonNode findCustomer = request.findById("http://192.168.20.90:9200/customers/_doc/" + replaceId);

                return findCustomer.get("_source");
            }else{
                return null;
            }
        }catch (Exception e){
            e.getMessage();
            return null;
        }
    }
    public Object create(Customer paramsCustomer) throws Exception{
        try{
            Map<String, Object> customer = new LinkedHashMap<>();
            UUID uuid = UUID.randomUUID();
            customer.put("id", uuid);
            customer.put("username", paramsCustomer.getUsername());
            customer.put("first_name", paramsCustomer.getFirst_name());
            customer.put("last_name", paramsCustomer.getLast_name());
            customer.put("gender", paramsCustomer.getGender());
            customer.put("email", paramsCustomer.getEmail());
            customer.put("birth_date", paramsCustomer.getBirth_date());

            JsonNode responseCreate = request.create("http://192.168.20.90:9200/customers/_doc/", customer);

            String id = responseCreate.get("_id").asText().replace("\"", "");
            JsonNode response = request.findById("http://192.168.20.90:9200/customers/_doc/" + id);

            return response.get("_source");
        }catch (Exception e){
            e.getMessage();
            return null;
        }
    }

    public Object update(Customer paramsCustomer, String paramsId) throws Exception{
         try{
             String queryTerm = "{\"query\":{\"term\":{\"id\":\""+ paramsId +"\"}}}";
             JsonNode customer = request.getCustomersWithBody("http://192.168.20.90:9200/customers/_search/", queryTerm);

             if( customer != null){
                 Map<String, Object> bodyMap = new LinkedHashMap<>();
                 Map<String, Object> customerMap = new LinkedHashMap<>();

                 Optional.ofNullable(paramsCustomer.getUsername()).ifPresent(username -> customerMap.put("username", username));
                 Optional.ofNullable(paramsCustomer.getFirst_name()).ifPresent(firstName -> customerMap.put("first_name", firstName));
                 Optional.ofNullable(paramsCustomer.getLast_name()).ifPresent(lastName -> customerMap.put("last_name", lastName));
                 Optional.ofNullable(paramsCustomer.getGender()).ifPresent(gender -> customerMap.put("gender", gender));
                 Optional.ofNullable(paramsCustomer.getEmail()).ifPresent(email -> customerMap.put("email", email));
                 Optional.ofNullable(paramsCustomer.getBirth_date()).ifPresent(birthDate -> customerMap.put("birth_date", birthDate));

                 bodyMap.put("doc", customerMap);

                 String replaceId = customer.get("hits").get("hits").get(0).get("_id").asText().replace("\"", "");
                 JsonNode responseUpdate = request.update("http://192.168.20.90:9200/customers/_update/" + replaceId, bodyMap);

                 String id = responseUpdate.get("_id").asText().replace("\"", "");
                 JsonNode response = request.findById("http://192.168.20.90:9200/customers/_doc/" + id);

                 return response.get("_source");
             }else{
                 return null;
             }
        }catch (Exception e){
            e.getMessage();
            return null;
        }
    }

    public Object delete(String paramsId) throws Exception{
        try{
            String queryTerm = "{\"query\":{\"term\":{\"id\":\""+ paramsId +"\"}}}";
            JsonNode customer = request.getCustomersWithBody("http://192.168.20.90:9200/customers/_search/", queryTerm);

            if(customer != null){
                String replaceId = customer.get("hits").get("hits").get(0).get("_id").asText().replace("\"", "");
                JsonNode responseDelete = request.delete("http://192.168.20.90:9200/customers/_doc/" + replaceId);

                return "Successfully";
            }else{
                return null;
            }
        }catch (Exception e){
            e.getMessage();
            return null;
        }
    }

}
