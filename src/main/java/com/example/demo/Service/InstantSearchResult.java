package com.example.demo.Service;
import java.util.List;

public record InstantSearchResult(List<QuerySuggestion> querySuggestions, List<ProductSuggestion> productSuggestions) {
    public record QuerySuggestion(String query, int requestCount) {}
    public record ProductSuggestion(String productId) {}

    // Q: could there be a problem with this approach?
    public static final InstantSearchResult EMPTY = new InstantSearchResult(List.of(), List.of());
}
