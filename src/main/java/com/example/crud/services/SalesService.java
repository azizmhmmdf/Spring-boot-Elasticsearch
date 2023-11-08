package com.example.crud.services;

import com.example.crud.models.Customer;
import com.example.crud.models.FilterQuery;
import com.example.crud.models.MessageResponse;
import com.example.crud.models.Sales;

public interface SalesService {
    MessageResponse getSales(FilterQuery filter) throws Exception;
    MessageResponse getTotalSales() throws Exception;
    MessageResponse maxSalesPerDay() throws Exception;
    MessageResponse getTotalSalesByRegion() throws Exception;
    MessageResponse getSalesChanges() throws Exception;
    MessageResponse findById(String paramsId) throws Exception;
    MessageResponse create(Sales paramsSales) throws Exception;
    MessageResponse update(Sales paramsSales) throws Exception;
    MessageResponse delete(String paramsId) throws Exception;
}
