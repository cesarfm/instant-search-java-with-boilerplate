package com.example.demo.Controller;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import com.example.demo.Service.InstantSearchService;
import com.example.demo.Service.InstantSearchParams;
import com.example.demo.Service.InstantSearchResult;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor 
@RequestMapping("/instant-search")
public class InstartSearchController {
    
    private final InstantSearchService service;

    // Q: how to handle errors cleaner?
    @GetMapping
    public InstantSearchResponse getInstantSearch(@RequestParam String query, @RequestParam(defaultValue = "5") int limit) {
        var params = new InstantSearchParams(query, limit);
        var result = service.search(params);
        return toResponse(result);
    }

    @PostMapping
    public InstantSearchResponse postInstantSearch(@RequestBody InstantSearchRequest request) {
        var params = new InstantSearchParams(request.getQuery(), request.getLimit());
        var result = service.search(params);
        return toResponse(result);
    }

    // Q: records vs classes with @Data
    private InstantSearchResponse toResponse(InstantSearchResult result) {
        return new InstantSearchResponse(result.querySuggestions(), result.productSuggestions());
    }
}
