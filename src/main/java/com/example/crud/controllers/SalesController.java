package com.example.crud.controllers;

import com.example.crud.models.FilterQuery;
import com.example.crud.models.MessageResponse;
import com.example.crud.models.Sales;
import com.example.crud.services.SalesService;
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
public class SalesController {
    @Autowired
    SalesService service;

    private ResponseEntity<?> response = null;

    @Operation(summary = "Get Sales")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = MessageResponse.class)) }),
            @ApiResponse(responseCode = "404", description = "Sales Not Found",
                    content = @Content)
    })
    @PostMapping(value = "/get_sales", produces = "application/json")
    public ResponseEntity<?> getSales(@RequestBody FilterQuery filter) throws Exception {
        MessageResponse messageResponse = service.getSales(filter);

        if(messageResponse.getStatus().equals(true)){
            response = new ResponseEntity<MessageResponse>(messageResponse, HttpStatus.OK);
        }else{
            response = new ResponseEntity<MessageResponse>(messageResponse, HttpStatus.NOT_FOUND);
        }

        return response;
    }

    @Operation(summary = "Get Total Sales Amount")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = MessageResponse.class)) }),
            @ApiResponse(responseCode = "404", description = "Sales Not Found",
                    content = @Content),
            @ApiResponse(responseCode = "503", description = "Service Unavailable",
                    content = @Content)
    })
    @GetMapping(value = "/get_total_sales_amount", produces = "application/json")
    public ResponseEntity<?> getTotalSalesAmount() throws Exception{
        MessageResponse messageResponse = service.getTotalSales();

        if(messageResponse.getStatus().equals(true)){
            response = new ResponseEntity<MessageResponse>(messageResponse, HttpStatus.OK);
        }else{
            if(messageResponse.getMessage().equals("Data Not Found")){
                response = new ResponseEntity<MessageResponse>(messageResponse, HttpStatus.NOT_FOUND);
            }else {
                response = new ResponseEntity<MessageResponse>(messageResponse, HttpStatus.SERVICE_UNAVAILABLE);
            }
        }

        return response;
    }

    @Operation(summary = "Get Total Sales By Region")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = MessageResponse.class)) }),
            @ApiResponse(responseCode = "404", description = "Sales Not Found",
                    content = @Content),
            @ApiResponse(responseCode = "503", description = "Service Unavailable",
                    content = @Content)
    })
    @GetMapping(value = "/get_total_sales_by_region", produces = "application/json")
    public ResponseEntity<?> getTotalSalesByRegion() throws Exception{
        MessageResponse messageResponse = service.getTotalSalesByRegion();

        if(messageResponse.getStatus().equals(true)){
            response = new ResponseEntity<MessageResponse>(messageResponse, HttpStatus.OK);
        }else{
            if(messageResponse.getMessage().equals("Data Not Found")){
                response = new ResponseEntity<MessageResponse>(messageResponse, HttpStatus.NOT_FOUND);
            }else {
                response = new ResponseEntity<MessageResponse>(messageResponse, HttpStatus.SERVICE_UNAVAILABLE);
            }
        }

        return response;
    }

    @Operation(summary = "Get Sales Changes")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = MessageResponse.class)) }),
            @ApiResponse(responseCode = "404", description = "Sales Not Found",
                    content = @Content),
            @ApiResponse(responseCode = "503", description = "Service Unavailable",
                    content = @Content)
    })
    @GetMapping(value = "/get_sales_changes", produces = "application/json")
    public ResponseEntity<?> getSalesChanges() throws Exception{
        MessageResponse messageResponse = service.getSalesChanges();

        if(messageResponse.getStatus().equals(true)){
            response = new ResponseEntity<MessageResponse>(messageResponse, HttpStatus.OK);
        }else{
            if(messageResponse.getMessage().equals("Data Not Found")){
                response = new ResponseEntity<MessageResponse>(messageResponse, HttpStatus.NOT_FOUND);
            }else {
                response = new ResponseEntity<MessageResponse>(messageResponse, HttpStatus.SERVICE_UNAVAILABLE);
            }
        }

        return response;
    }

    @Operation(summary = "Get a sales")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = MessageResponse.class)) }),
            @ApiResponse(responseCode = "404", description = "Sales Not Found",
                    content = @Content),
            @ApiResponse(responseCode = "503", description = "Service Unavailable",
                    content = @Content)
    })
    @GetMapping(value = "/sales/{id}", produces = "application/json")
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


    @Operation(summary = "Create a Sales")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Successfully",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = MessageResponse.class)) }),
            @ApiResponse(responseCode = "409", description = "Sales Already Exist",
                    content = @Content),
            @ApiResponse(responseCode = "503", description = "Service Unavailable",
                    content = @Content)
    })
    @PostMapping(value = "/sales", produces = "application/json")
    public ResponseEntity<?> create(@RequestBody Sales paramsSales) throws Exception{
        MessageResponse messageResponse = service.create(paramsSales);

        if(messageResponse.getMessage().equals("Successfully")){
            response = new ResponseEntity<MessageResponse>(messageResponse, HttpStatus.CREATED);
        }else{
            if (messageResponse.getMessage().equals("Sales already exist")){
                response = new ResponseEntity<MessageResponse>(messageResponse, HttpStatus.CONFLICT);
            }else {
                response = new ResponseEntity<MessageResponse>(messageResponse, HttpStatus.SERVICE_UNAVAILABLE);
            }
        }

        return response;
    }

    @Operation(summary = "Update a Sales")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = MessageResponse.class)) }),
            @ApiResponse(responseCode = "404", description = "Sales Not Found",
                    content = @Content),
            @ApiResponse(responseCode = "503", description = "Service Unavailable",
                    content = @Content)
    })
    @PutMapping(value = "/sales", produces = "application/json")
    public ResponseEntity<?> update(@RequestBody Sales paramsSales) throws Exception{
        MessageResponse messageResponse = service.update(paramsSales);

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

    @Operation(summary = "Delete a Sales")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = MessageResponse.class)) }),
            @ApiResponse(responseCode = "404", description = "Sales Not Found",
                    content = @Content),
            @ApiResponse(responseCode = "503", description = "Service Unavailable",
                    content = @Content)
    })
    @DeleteMapping(value = "/sales", produces = "application/json")
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
