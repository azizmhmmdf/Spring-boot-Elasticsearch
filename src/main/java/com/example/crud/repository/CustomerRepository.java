package com.example.crud.repository;

import com.example.crud.models.Customer;
import com.example.crud.util.JerseyRequest;
import com.fasterxml.jackson.databind.JsonNode;

import org.springframework.stereotype.Repository;

import java.util.*;


@Repository
public class CustomerRepository {
    private final JerseyRequest request = new JerseyRequest();

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
            JsonNode customer = request.findById("http://192.168.20.90:9200/customers/_doc/" + paramsId);
            if(customer != null) {
                return customer.get("_source");
            }

            return null;
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

            JsonNode createCustomer = request.create("http://192.168.20.90:9200/customers/_doc/" + customer.get("id"), customer);

            String id = createCustomer.get("_id").asText().replace("\"", "");
            JsonNode response = request.findById("http://192.168.20.90:9200/customers/_doc/" + id);

            return response.get("_source");
        }catch (Exception e){
            e.getMessage();
            return null;
        }
    }

    public Object update(Customer paramsCustomer, String paramsId) throws Exception{
         try{
             JsonNode customer = request.findById("http://192.168.20.90:9200/customers/_doc/" + paramsId);
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

                 JsonNode updateCustomer = request.update("http://192.168.20.90:9200/customers/_update/" + paramsId, bodyMap);
                 return customer.get("_source");
             }

             return null;
        }catch (Exception e){
            e.getMessage();
            return null;
        }
    }

    public Object delete(String paramsId) throws Exception{
        try{
            JsonNode customer = request.findById("http://192.168.20.90:9200/customers/_doc/" + paramsId);
            if(customer != null){
                JsonNode deleteCustomer = request.delete("http://192.168.20.90:9200/customers/_doc/" + paramsId);
                return "Successfully";
            }

            return null;
        }catch (Exception e){
            e.getMessage();
            return null;
        }
    }

}
