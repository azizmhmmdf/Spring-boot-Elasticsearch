package com.example.crud.controllers;

import com.example.crud.models.Customer;
import com.example.crud.models.FilterQuery;
import com.example.crud.models.MessageResponse;
import com.example.crud.services.CustomerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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

    @Operation(summary = "Get Customers")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = MessageResponse.class)) }),
            @ApiResponse(responseCode = "404", description = "Customers Not Found",
                    content = @Content)
    })
    @PostMapping(value = "/get_customers", produces = "application/json")
    public ResponseEntity<?> getCustomers(@RequestBody FilterQuery filter) throws Exception {
        MessageResponse messageResponse = service.getCustomers(filter);

        if(messageResponse.getStatus().equals(true)){
            response = new ResponseEntity<MessageResponse>(messageResponse,HttpStatus.OK);
        }else{
            response = new ResponseEntity<MessageResponse>(messageResponse, HttpStatus.NOT_FOUND);
        }

        return response;
    }


    @Operation(summary = "Get a Customer")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = MessageResponse.class)) }),
            @ApiResponse(responseCode = "404", description = "Customer Not Found",
                    content = @Content),
            @ApiResponse(responseCode = "503", description = "Service Unavailable",
                    content = @Content)
    })
    @GetMapping(value = "/customers/{id}", produces = "application/json")
    public ResponseEntity<?> show(@PathVariable("id") String paramsId) throws Exception {
        MessageResponse messageResponse = service.findById(paramsId);

        if(messageResponse.getMessage().equals("Successfully")){
            response = new ResponseEntity<MessageResponse>(messageResponse,HttpStatus.OK);
        }else{
            if(messageResponse.getMessage().equals("Data Not Found")){
                response = new ResponseEntity<MessageResponse>(messageResponse, HttpStatus.NOT_FOUND);
            }else {
                response = new ResponseEntity<MessageResponse>(messageResponse, HttpStatus.SERVICE_UNAVAILABLE);
            }
        }
        return response;
    }


    @Operation(summary = "Create a Customer")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Successfully",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = MessageResponse.class)) }),
            @ApiResponse(responseCode = "409", description = "Customer Already Exist",
                    content = @Content),
            @ApiResponse(responseCode = "503", description = "Service Unavailable",
                    content = @Content)
    })
    @PostMapping(value = "/customers", produces = "application/json")
    public ResponseEntity<?> create(@RequestBody Customer paramsCustomers) throws Exception{
        MessageResponse messageResponse = service.create(paramsCustomers);

        if(messageResponse.getMessage().equals("Successfully")){
            response = new ResponseEntity<MessageResponse>(messageResponse, HttpStatus.CREATED);
        }else{
            if (messageResponse.getMessage().equals("Customer already exist")){
                response = new ResponseEntity<MessageResponse>(messageResponse, HttpStatus.CONFLICT);
            }else {
                response = new ResponseEntity<MessageResponse>(messageResponse, HttpStatus.SERVICE_UNAVAILABLE);
            }
        }

        return response;
    }

    @Operation(summary = "Update a Customer")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = MessageResponse.class)) }),
            @ApiResponse(responseCode = "404", description = "Customer Not Found",
                    content = @Content),
            @ApiResponse(responseCode = "503", description = "Service Unavailable",
                    content = @Content)
    })
    @PutMapping(value = "/customers", produces = "application/json")
    public ResponseEntity<?> update(@RequestBody Customer paramsCustomers) throws Exception{
        MessageResponse messageResponse = service.update(paramsCustomers);

        if(messageResponse.getMessage().equals("Successfully")){
            response = new ResponseEntity<MessageResponse>(messageResponse, HttpStatus.OK);
        }else{
            if (messageResponse.getMessage().equals("Data Not Found")){
                response = new ResponseEntity<MessageResponse>(messageResponse, HttpStatus.NOT_FOUND);
            }else {
                response = new ResponseEntity<MessageResponse>(messageResponse, HttpStatus.SERVICE_UNAVAILABLE);
            }
        }

        return response;
    }

    @Operation(summary = "Delete a Customer")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = MessageResponse.class)) }),
            @ApiResponse(responseCode = "404", description = "Customer Not Found",
                    content = @Content),
            @ApiResponse(responseCode = "503", description = "Service Unavailable",
                    content = @Content)
    })
    @DeleteMapping(value = "/customers", produces = "application/json")
    public ResponseEntity<?> delete(@RequestParam("id") String paramsId) throws Exception{
        MessageResponse messageResponse = service.delete(paramsId);

        if(messageResponse.getStatus().equals(true)){
            response = new ResponseEntity<MessageResponse>(messageResponse, HttpStatus.OK);
        }else{
            if(messageResponse.getMessage().equals("Data Not Found")){
                response = new ResponseEntity<MessageResponse>(messageResponse, HttpStatus.NOT_FOUND);
            }else{
                response = new ResponseEntity<MessageResponse>(messageResponse, HttpStatus.SERVICE_UNAVAILABLE);
            }
        }

        return response;
    }
}
