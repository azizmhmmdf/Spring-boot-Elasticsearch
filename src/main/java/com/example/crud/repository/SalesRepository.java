package com.example.crud.repository;

import com.example.crud.models.FilterQuery;
import com.example.crud.models.FilterTerms;
import com.example.crud.models.Sales;
import com.example.crud.util.JerseyRequest;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Repository
public class SalesRepository {
    private final JerseyRequest request = new JerseyRequest();

    public Object getSales(FilterQuery filter) throws Exception{
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

        Integer size = Integer.parseInt(filter.getSize());
        Integer page = Integer.parseInt(filter.getPage()) - 1;
        Integer from = size * page;
        String body = "{\"from\":" + from + ",\"size\":" + size + ",\"query\":{\"bool\":{\"must\":" + query + "}}}";

        JsonNode queryResult = request.getWithBody("http://192.168.20.90:9200/sales_v2/_search", body).get("hits");

        List<JsonNode> objList = new ArrayList<JsonNode>();
        if(queryResult != null){
            for (JsonNode object : queryResult.get("hits")) {
                objList.add(object.get("_source"));
            }
            finalResult.put("total_sales_list", objList.size());
            finalResult.put("total_sales_data", queryResult.get("total").get("value").asInt());
            finalResult.put("Sales_list", objList);

            return finalResult;
        }
        return null;
    }

    public Object getTotalSalesAmount() throws Exception{
        String body = "{\"size\":0,\"aggs\":{\"total_sales\":{\"sum\":{\"field\":\"sales_amount\"}}}}";
        JsonNode queryResult = request.getWithBody("http://192.168.20.90:9200/sales_v2/_search", body);
        if (queryResult != null) {
            if (queryResult.has("aggregations")) {
                return queryResult.get("aggregations");
            } else {
                return "Data not found";
            }
        } else {
            return null;
        }
    }

    public Object getTotalSalesByRegion() throws Exception{
        String body = "{\"size\":0,\"aggs\":{\"sales_by_region\":{\"terms\":{\"field\":\"region\"},\"aggs\":{\"total_sales_per_region\":{\"sum\":{\"field\":\"sales_amount\"}}}}}}";
        JsonNode queryResult = request.getWithBody("http://192.168.20.90:9200/sales_v2/_search", body);
        if (queryResult != null) {
            if (queryResult.has("aggregations")) {
                return queryResult.get("aggregations");
            } else {
                return "Data not found";
            }
        } else {
            return null;
        }
    }

    public Object getSalesChanges() throws Exception{
        String body = "{\"size\":0,\"aggs\":{\"sales_per_day\":{\"date_histogram\":{\"field\":\"timestamp\",\"calendar_interval\":\"day\"},\"aggs\":{\"total_sales_per_day\":{\"sum\":{\"field\":\"sales_amount\"}},\"sales_diff\":{\"derivative\":{\"buckets_path\":\"total_sales_per_day\"}}}}}}";
        JsonNode queryResult = request.getWithBody("http://192.168.20.90:9200/sales_v2/_search", body);
        if (queryResult != null) {
            if (queryResult.has("aggregations")) {
                return queryResult.get("aggregations");
            } else {
                return "Data not found";
            }
        } else {
            return null;
        }
    }

    public Object findById(String paramsId) throws Exception{
        JsonNode sales = request.findById("http://192.168.20.90:9200/sales_v2/_doc/" + paramsId);

        if(sales != null) {
            if(sales.get("found").asBoolean()){
                return sales.get("_source");
            }
            return "Data Not Found";
        }

        return null;
    }

    public Object create(Sales paramsSales) throws Exception{
        String hasheId = hasheId(paramsSales.getId());
        JsonNode checkSales = request.findById("http://192.168.20.90:9200/sales_v2/_doc/" + hasheId);

        if(checkSales != null){
            if(!checkSales.get("found").asBoolean() ){
                Map<String, Object> sales = new LinkedHashMap<>();

                sales.put("id", hasheId);
                sales.put("product_name", paramsSales.getProduct_name());
                sales.put("sales_amount", paramsSales.getSales_amount());
                sales.put("region", paramsSales.getRegion());
                sales.put("timestamp", formatDate());

                JsonNode createSales = request.create("http://192.168.20.90:9200/sales_v2/_doc/" + hasheId, sales);

                String id = createSales.get("_id").asText().replace("\"", "");
                JsonNode response = request.findById("http://192.168.20.90:9200/sales_v2/_doc/" + id);

                return response.get("_source");
            }
            return "Sales already exist";
        }
        return null;
    }

    public String hasheId(String paramsId){
        String hashedId = DigestUtils.md5Hex(paramsId);
        return hashedId;
    }

    public String formatDate(){
        LocalDateTime dateTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
        return dateTime.format(formatter);
    }

    public Object update(Sales paramsSales) throws Exception{
        JsonNode sales = request.findById("http://192.168.20.90:9200/customers/_doc/" + paramsSales.getId());
        if(sales != null){
            if( sales.get("found").asBoolean()){
                Map<String, Object> bodyMap = new LinkedHashMap<>();
                Map<String, Object> salesMap = new LinkedHashMap<>();

                Optional.ofNullable(paramsSales.getProduct_name()).ifPresent(product_name -> salesMap.put("product_name", product_name));
                Optional.ofNullable(paramsSales.getSales_amount()).ifPresent(sales_amount -> salesMap.put("sales_amount", sales_amount));
                Optional.ofNullable(paramsSales.getRegion()).ifPresent(region -> salesMap.put("region", region));
                Optional.ofNullable(paramsSales.getTimestamp()).ifPresent(timestamp -> salesMap.put("timestamp", timestamp));

                bodyMap.put("doc", salesMap);

                JsonNode updateCustomer = request.update("http://192.168.20.90:9200/sales_v2/_update/" + paramsSales.getId(), bodyMap);
                return sales.get("_source");
            }
            return "Data Not Found";
        }
        return null;
    }

    public Object delete(String paramsId) throws Exception{
        JsonNode sales = request.findById("http://192.168.20.90:9200/sales_v2/_doc/" + paramsId);
        if(sales != null ){
            if(sales.get("found").asBoolean()){
                JsonNode deleteSales = request.delete("http://192.168.20.90:9200/sales_v2/_doc/" + paramsId);
                return "Successfully";
            }
            return "Data Not Found";
        }
        return null;
    }
}
