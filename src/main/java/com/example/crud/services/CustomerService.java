package com.example.crud.services;

import com.example.crud.models.Customer;
import com.example.crud.models.MessageResponse;

public interface CustomerService {
    MessageResponse getCustomers() throws Exception;
    MessageResponse findById(String paramsId) throws Exception;
    MessageResponse create(Customer paramsCustomer) throws Exception;
    MessageResponse update(Customer paramsCustoemer, String paramsId) throws Exception;
    MessageResponse delete(String paramsId) throws Exception;
}
