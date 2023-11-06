package com.example.crud.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

import javax.ws.rs.core.MediaType;
import java.util.Map;

public class JerseyRequest {
    // Object mapper ini kegunaannya untuk mengonversikan data JSON berupa object java dan juga sebaliknya;
    private ObjectMapper objectMapper = new ObjectMapper();

    // Client ini adalah instance object untuk berinteraksi dengan resfull
    private Client client = Client.create();

    public JsonNode getCustomers(String url) throws JsonMappingException, JsonProcessingException {
        // ini berfungsi untuk mengarahkan url yang ingin kita tuju
        WebResource webResource = client.resource(url);

        // ini berfungsi untuk membuat permintaan http client
        // dan fungsi dari .get ini untuk mengirim dan menerima response dari http client yang sudah kita kirim
        ClientResponse response = webResource.accept(MediaType.APPLICATION_JSON)
                                             .get(ClientResponse.class);

        // ini berfungsi untuk mengonversi dari data json ke string
        String outputString = response.getEntity(String.class);

        // response dari jsonNode berupa structur json tapi buka semata mata json mentah
        JsonNode jsonNode = objectMapper.readValue(outputString, JsonNode.class);
        return jsonNode;
    }

    public JsonNode findById(String url) throws JsonMappingException, JsonProcessingException{
        WebResource webResource = client.resource(url);
        ClientResponse response = webResource.type(MediaType.APPLICATION_JSON)
                                             .get(ClientResponse.class);
        String outputString = response.getEntity(String.class);
        JsonNode jsonNode = objectMapper.readValue(outputString, JsonNode.class);
        return jsonNode;
    }

    public JsonNode getCustomersWithBody(String url, Object body) throws JsonMappingException, JsonProcessingException{
        WebResource webResource = client.resource("http://192.168.20.90:9200/customers/_search");
        ClientResponse response = webResource.type("application/json").post(ClientResponse.class, body);
//        System.out.println(response);
        String output = response.getEntity(String.class);
//        System.out.println(output);
        JsonNode  jsonNode = objectMapper.readValue(output, JsonNode.class);
//        System.out.println(jsonNode);
        return jsonNode;
    }

    public JsonNode create(String url, Map<String, Object> body) throws JsonMappingException, JsonProcessingException{
        WebResource webResource = client.resource(url);
        ClientResponse response = webResource.type(MediaType.APPLICATION_JSON)
                                             .post(ClientResponse.class, objectMapper.writeValueAsString(body));
        String outputString = response.getEntity(String.class);
        JsonNode jsonNode = objectMapper.readValue(outputString, JsonNode.class);
        return jsonNode;
    }

    public JsonNode update(String url, Map<String, Object> body) throws JsonMappingException, JsonProcessingException{
        WebResource webResource = client.resource(url);
        ClientResponse response = webResource.type(MediaType.APPLICATION_JSON)
                                             .post(ClientResponse.class, objectMapper.writeValueAsString(body));
        String outputString = response.getEntity(String.class);
        JsonNode jsonNode = objectMapper.readValue(outputString, JsonNode.class);
        return jsonNode;
    }

    public JsonNode delete(String url) throws JsonMappingException, JsonProcessingException{
        WebResource webResource = client.resource(url);
        ClientResponse response = webResource.type(MediaType.APPLICATION_JSON)
                                             .delete(ClientResponse.class);
        String outputString = response.getEntity(String.class);
        JsonNode jsonNode = objectMapper.readValue(outputString, JsonNode.class);
        return jsonNode;
    }
}
