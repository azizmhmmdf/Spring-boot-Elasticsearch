package com.example.crud.services.impls;

import com.example.crud.models.Customer;
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

    private MessageResponse messageResponse = new MessageResponse();
    @Override
    public MessageResponse getCustomers() throws Exception {
        Object customers = repository.getCustomers(); // return map
        JSONObject customersJson = new JSONObject(customers.toString()); // convert to json
        int totalData = customersJson.getInt("total_data");

        if(totalData > 0){
            messageResponse.setData(customers);
            messageResponse.setMessage("Successfully");
            messageResponse.setStatus(true);
        }else{
            messageResponse.setData(customers);
            messageResponse.setStatus(false);
            messageResponse.setMessage("Not Found");
        }
        return messageResponse;
    }

    @Override
    public MessageResponse findById(String paramsId) throws Exception {
        Object customer = repository.findById(paramsId);

        if(customer != null){
            messageResponse.setData(customer);
            messageResponse.setStatus(true);
            messageResponse.setMessage("Successfully");
        }else{
            messageResponse.setMessage("Data Not Found");
            messageResponse.setStatus(false);
        }

        return messageResponse;
    }

    @Override
    public MessageResponse create(Customer paramsCustomer) throws Exception {
        Object customer = repository.create(paramsCustomer);
        if(customer != null){
            messageResponse.setData(customer);
            messageResponse.setMessage("Create data Successfully");
            messageResponse.setStatus(true);
        }else{
            messageResponse.setStatus(false);
            messageResponse.setMessage("failed");
        }

        return messageResponse;
    }

    @Override
    public MessageResponse update(Customer paramsCustoemer, String paramsId) throws Exception {
        Object customer = repository.update(paramsCustoemer, paramsId);

        if(customer != null){
            messageResponse.setData(customer);
            messageResponse.setMessage("Update data Successfully");
            messageResponse.setStatus(true);
        }else{
            messageResponse.setStatus(false);
            messageResponse.setMessage("failed");
        }
        return messageResponse;
    }

    @Override
    public MessageResponse delete(String paramsId) throws Exception {
        Object response = repository.delete(paramsId);
        if(response.equals("Successfully")){
            messageResponse.setMessage("Delete data Successfully");
            messageResponse.setStatus(true);
        }else{
            messageResponse.setMessage("failed");
            messageResponse.setStatus(false);
        }

        return messageResponse;
    }
}
