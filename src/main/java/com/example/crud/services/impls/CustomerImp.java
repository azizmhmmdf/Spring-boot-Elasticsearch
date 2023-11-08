package com.example.crud.services.impls;

import com.example.crud.models.Customer;
import com.example.crud.models.FilterQuery;
import com.example.crud.models.MessageResponse;
import com.example.crud.repository.CustomerRepository;
import com.example.crud.services.CustomerService;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CustomerImp implements CustomerService {
    @Autowired
    CustomerRepository repository;

    @Override
    public MessageResponse getCustomers(FilterQuery filter) throws Exception {
        MessageResponse messageResponse = new MessageResponse();
        Object customers = repository.getCustomers(filter); // return map

        if(customers != null){
            messageResponse.setData(customers);
            messageResponse.setMessage("Successfully");
            messageResponse.setStatus(true);
        }else{
            messageResponse.setStatus(false);
            messageResponse.setMessage("Not Found");
        }
        return messageResponse;
    }

    @Override
    public MessageResponse findById(String paramsId) throws Exception {
        MessageResponse messageResponse = new MessageResponse();
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
    public MessageResponse create(Customer paramsCustomer) throws Exception {
        MessageResponse messageResponse = new MessageResponse();
        Object customer = repository.create(paramsCustomer);

        if(customer != null && customer != "Customer already exist"){
            messageResponse.setStatus(true);
            messageResponse.setMessage("Successfully");
            messageResponse.setData(customer);
        } else if (customer == "Customer already exist") {
            messageResponse.setStatus(false);
            messageResponse.setMessage("Customer already exist");
        } else {
            messageResponse.setStatus(false);
            messageResponse.setMessage("Service Unavailable");
        }

        return messageResponse;
    }

    @Override
    public MessageResponse update(Customer paramsCustomer) throws Exception {
        MessageResponse messageResponse = new MessageResponse();
        Object customer = repository.update(paramsCustomer);

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
    public MessageResponse delete(String paramsId) throws Exception {
        MessageResponse messageResponse = new MessageResponse();
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
