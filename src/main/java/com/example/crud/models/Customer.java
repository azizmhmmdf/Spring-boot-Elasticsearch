package com.example.crud.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Customer {
    private String id;
    private String username;
    private String first_name;
    private String last_name;
    private String email;
    private String gender;
    private String birth_date;
}
