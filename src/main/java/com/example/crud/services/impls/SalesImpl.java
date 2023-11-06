package com.example.crud.services.impls;

import com.example.crud.models.Customer;
import com.example.crud.models.FilterQuery;
import com.example.crud.models.MessageResponse;
import com.example.crud.models.Sales;
import com.example.crud.repository.CustomerRepository;
import com.example.crud.repository.SalesRepository;
import com.example.crud.services.SalesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SalesImpl implements SalesService {
    @Autowired
    SalesRepository repository;

    private final MessageResponse messageResponse = new MessageResponse();
    @Override
    public MessageResponse getSales(FilterQuery filter) throws Exception {
        Object sales = repository.getSales(filter); // return map

        if(sales != null){
            messageResponse.setData(sales);
            messageResponse.setMessage("Successfully");
            messageResponse.setStatus(true);
        }else{
            messageResponse.setStatus(false);
            messageResponse.setMessage("Not Found");
        }
        return messageResponse;
    }

    @Override
    public MessageResponse getTotalSales() throws Exception {
        Object sales = repository.getTotalSalesAmount();

        if(sales != null && sales != "Data Not Found"){
            messageResponse.setData(sales);
            messageResponse.setMessage("Successfully");
            messageResponse.setStatus(true);
        }else if (sales == "Data Not Found") {
            messageResponse.setStatus(false);
            messageResponse.setMessage("Not Found");
        }else {
            messageResponse.setStatus(false);
            messageResponse.setMessage("Service Unavailable");
        }

        return messageResponse;
    }

    @Override
    public MessageResponse getTotalSalesByRegion() throws Exception {
        Object sales = repository.getTotalSalesByRegion();

        if(sales != null && sales != "Data Not Found"){
            messageResponse.setData(sales);
            messageResponse.setMessage("Successfully");
            messageResponse.setStatus(true);
        }else if (sales == "Data Not Found") {
            messageResponse.setStatus(false);
            messageResponse.setMessage("Not Found");
        }else {
            messageResponse.setStatus(false);
            messageResponse.setMessage("Service Unavailable");
        }

        return messageResponse;
    }

    @Override
    public MessageResponse getSalesChanges() throws Exception {
        Object sales = repository.getSalesChanges();

        if(sales != null && sales != "Data Not Found"){
            messageResponse.setData(sales);
            messageResponse.setMessage("Successfully");
            messageResponse.setStatus(true);
        }else if (sales == "Data Not Found") {
            messageResponse.setStatus(false);
            messageResponse.setMessage("Not Found");
        }else {
            messageResponse.setStatus(false);
            messageResponse.setMessage("Service Unavailable");
        }

        return messageResponse;    }

    @Override
    public MessageResponse findById(String paramsId) throws Exception {
        Object customer = repository.findById(paramsId);

        if(customer != null && customer != "Data Not Found"){
            messageResponse.setStatus(true);
            messageResponse.setMessage("Successfully");
            messageResponse.setData(customer);
        } else if (customer == "Data Not Found") {
            messageResponse.setStatus(false);
            messageResponse.setMessage("Data Not Found");
        } else {
            messageResponse.setStatus(false);
            messageResponse.setMessage("Service Unavailable");
        }

        return messageResponse;
    }

    @Override
    public MessageResponse create(Sales paramsSales) throws Exception {
        Object sales = repository.create(paramsSales);

        if(sales != null && sales != "Sales already exist"){
            messageResponse.setStatus(true);
            messageResponse.setMessage("Successfully");
            messageResponse.setData(sales);
        } else if (sales == "Sales already exist") {
            messageResponse.setStatus(false);
            messageResponse.setMessage("Sales already exist");
        } else {
            messageResponse.setStatus(false);
            messageResponse.setMessage("Service Unavailable");
        }

        return messageResponse;
    }

    @Override
    public MessageResponse update(Sales paramsSales) throws Exception {
        Object sales = repository.update(paramsSales);

        if(sales != null && sales != "Data Not Found"){
            messageResponse.setStatus(true);
            messageResponse.setMessage("Successfully");
            messageResponse.setData(sales);
        } else if (sales == "Data Not Found") {
            messageResponse.setStatus(false);
            messageResponse.setMessage("Data Not Found");
        } else {
            messageResponse.setStatus(false);
            messageResponse.setMessage("Service Unavailable");
        }


        return messageResponse;
    }

    @Override
    public MessageResponse delete(String paramsId) throws Exception {
        Object response = repository.delete(paramsId);

        if(response == "Successfully"){
            messageResponse.setStatus(true);
            messageResponse.setMessage("Successfully");
        } else if (response == "Data Not Found") {
            messageResponse.setStatus(false);
            messageResponse.setMessage("Data Not Found");
        } else {
            messageResponse.setStatus(false);
            messageResponse.setMessage("Service Unavailable");
        }

        return messageResponse;
    }
}
