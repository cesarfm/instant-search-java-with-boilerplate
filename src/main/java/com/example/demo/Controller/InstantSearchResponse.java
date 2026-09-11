package com.example.demo.Controller;

import lombok.AllArgsConstructor;
import lombok.Data;
import tools.jackson.databind.annotation.JsonNaming;
import tools.jackson.databind.PropertyNamingStrategies.SnakeCaseStrategy;

import java.util.List;
import com.example.demo.Service.InstantSearchResult.QuerySuggestion;
import com.example.demo.Service.InstantSearchResult.ProductSuggestion;

@Data
@AllArgsConstructor
@JsonNaming(SnakeCaseStrategy.class)
public class InstantSearchResponse {

    private List<QuerySuggestion> querySuggestions;
    private List<ProductSuggestion> productSuggestions;

    // Q: what if I want to configure the json of the nested objects?

}
