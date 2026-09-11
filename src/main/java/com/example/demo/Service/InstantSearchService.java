package com.example.demo.Service;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import tools.jackson.databind.ObjectMapper;

@Slf4j
@Service
public class InstantSearchService {

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    private ConcurrentMap<String, SearchData> searchDataMap = new ConcurrentHashMap<>();

    @PostConstruct
    private void init() throws Exception {
        var records = loadSearchDataFromFile("data/searches_bq.json");

        // GOTCHA: what to do with duplicated keys?
        var dataMap = new HashMap<String, SearchData>();
        for (var record : records) {
            var existing = dataMap.putIfAbsent(record.getQuery(), record);
            if (existing != null) {
                log.warn("Duplicate key found for query '{}', keeping the first occurrence.", record.getQuery());
            }
            // Q: what else could we do here instead?
        }

        searchDataMap.putAll(dataMap);
    }

    public InstantSearchResult search(InstantSearchParams params) {
        // super simplistic logic from now
        // Q: what would make sense here? implement it!!
        var data = searchDataMap.get(params.query());
        if (data == null) {
            return InstantSearchResult.EMPTY;
        }

        var querySuggestion = new InstantSearchResult.QuerySuggestion(data.getQuery(), data.getRequestCount());
        var productSuggestions = data.getProductIds().stream()
                                     .map(InstantSearchResult.ProductSuggestion::new)
                                     .limit(params.limit())
                                     .toList();

        return new InstantSearchResult(List.of(querySuggestion), productSuggestions);
    }

    private static List<SearchData> loadSearchDataFromFile(String path) {
        try {
            var resource = new ClassPathResource(path);
            var json = resource.getContentAsString(StandardCharsets.UTF_8);
            var records = OBJECT_MAPPER.readValue(json, SearchData[].class);
            return List.of(records);

        } catch (Exception e) {
            log.error("Failed to load search data from path: {}", path, e);
            return List.of();
        }
    }
}
