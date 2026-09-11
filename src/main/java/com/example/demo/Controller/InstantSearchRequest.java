package com.example.demo.Controller;

import lombok.Data;

@Data 
public class InstantSearchRequest {
    private String query;
    // Q: does it make sense to have only one limit?
    private int limit;
}
