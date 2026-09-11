package com.example.demo.Service;
import java.util.List;

import lombok.Data;

@Data 
public class SearchData {
    private String query;
    private int requestCount;
    private List<String> productIds;
}
