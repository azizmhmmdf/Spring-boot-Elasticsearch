package com.example.crud.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Sales {
    private String id;
    private String product_name;
    private int sales_amount;
    private String region;
    private String timestamp;
}
