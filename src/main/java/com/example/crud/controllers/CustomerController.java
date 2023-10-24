package com.example.crud.controllers;

import com.example.crud.models.Customer;
import com.example.crud.models.MessageResponse;
import com.example.crud.services.CustomerService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class CustomerController {
    @Autowired
    CustomerService service;

    private ResponseEntity<?> response = null;

    @GetMapping(value = "/customers", produces = "application/json")
    public ResponseEntity<?> index() throws Exception {
        MessageResponse messageResponse = service.getCustomers();

        if(messageResponse.getStatus().equals(true)){
            response = new ResponseEntity<MessageResponse>(messageResponse,HttpStatus.OK);
        }else{
            response = new ResponseEntity<MessageResponse>(messageResponse, HttpStatus.NOT_FOUND);
        }

        return response;
    }

    @GetMapping(value = "/customers/{id}", produces = "application/json")
    public ResponseEntity<?> show(@PathVariable("id") String paramsId) throws Exception {
        MessageResponse messageResponse = service.findById(paramsId);

        if(messageResponse.getStatus().equals(true)){
            response = new ResponseEntity<MessageResponse>(messageResponse,HttpStatus.OK);
        }else{
            response = new ResponseEntity<MessageResponse>(messageResponse, HttpStatus.NOT_FOUND);
        }

        return response;
    }

    @PostMapping(value = "/customers", produces = "application/json")
    public ResponseEntity<?> create(@RequestBody Customer paramsCustomers) throws Exception{
        MessageResponse messageResponse = service.create(paramsCustomers);

        if(messageResponse.getStatus().equals(true)){
            response = new ResponseEntity<MessageResponse>(messageResponse, HttpStatus.CREATED);
        }else{
            response = new ResponseEntity<MessageResponse>(messageResponse, HttpStatus.BAD_REQUEST);
        }

        return response;
    }

    @PutMapping(value = "/customers", produces = "application/json")
    public ResponseEntity<?> update(@RequestBody Customer paramsCustomers, @RequestParam("id") String paramsId) throws Exception{
        MessageResponse messageResponse = service.update(paramsCustomers, paramsId);

        if(messageResponse.getStatus().equals(true)){
            response = new ResponseEntity<MessageResponse>(messageResponse, HttpStatus.OK);
        }else{
            response = new ResponseEntity<MessageResponse>(messageResponse, HttpStatus.PAYMENT_REQUIRED);
        }

        return response;
    }

    @DeleteMapping(value = "/customers", produces = "application/json")
    public ResponseEntity<?> delete(@RequestParam("id") String paramsId) throws Exception{
        MessageResponse messageResponse = service.delete(paramsId);

        if(messageResponse.getStatus().equals(true)){
            response = new ResponseEntity<MessageResponse>(messageResponse, HttpStatus.OK);
        }else{
            response = new ResponseEntity<MessageResponse>(messageResponse, HttpStatus.PAYMENT_REQUIRED);
        }

        return response;
    }
}
