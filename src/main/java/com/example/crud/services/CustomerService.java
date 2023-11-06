package com.example.crud.services;

import com.example.crud.models.Customer;
import com.example.crud.models.FilterQuery;
import com.example.crud.models.MessageResponse;

public interface CustomerService {
    MessageResponse getCustomers(FilterQuery filter) throws Exception;
    MessageResponse findById(String paramsId) throws Exception;
    MessageResponse create(Customer paramsCustomer) throws Exception;
    MessageResponse update(Customer paramsCustoemer) throws Exception;
    MessageResponse delete(String paramsId) throws Exception;
}
