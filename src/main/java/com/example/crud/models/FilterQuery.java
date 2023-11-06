package com.example.crud.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

@NoArgsConstructor
@AllArgsConstructor
@Data
    public class FilterQuery {
    private String size;
    private String page;
    private List<FilterTerms> query;
}
