package com.ecommerce.catalog.controller;

import com.ecommerce.catalog.model.AutosuggestCriteria;
import com.ecommerce.catalog.model.SearchReq;
import com.ecommerce.catalog.model.SearchRes;
import com.ecommerce.catalog.model.SearchResWithFilter;
import com.ecommerce.catalog.service.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/search")
public class SearchController {

    @Autowired
    private SearchService searchService;

    @GetMapping
    public SearchRes search(@RequestParam(required = false) String q,
                            @RequestParam(required = false, defaultValue = "1") int page,
                            @RequestParam(required = false, defaultValue = "10") int size,
                            @RequestParam(required = false) String brands,
                            @RequestParam(required = false) String categories
    ) {
        SearchReq searchReq = new SearchReq(q, size, page);
        if (StringUtils.hasLength(brands)) {
            List<String> b = Arrays.stream(brands.split(",")).collect(Collectors.toList());
            searchReq.setBrands(b);
        }
        if (StringUtils.hasLength(categories)) {
            List<String> c = Arrays.stream(categories.split(",")).collect(Collectors.toList());
            searchReq.setCategories(c);
        }

        return searchService.search(searchReq);
    }

    @GetMapping("/search-filters")
    public SearchResWithFilter searchAndFilter(@RequestParam(required = false) String q,
                                               @RequestParam(required = false, defaultValue = "1") int page,
                                               @RequestParam(required = false, defaultValue = "10") int size,
                                               @RequestParam(required = false) String brands,
                                               @RequestParam(required = false) String categories) {
        SearchReq searchReq = new SearchReq(q, size, page);
        if (StringUtils.hasLength(brands)) {
            List<String> b = Arrays.stream(brands.split(",")).collect(Collectors.toList());
            searchReq.setBrands(b);
        }
        if (StringUtils.hasLength(categories)) {
            List<String> c = Arrays.stream(categories.split(",")).collect(Collectors.toList());
            searchReq.setCategories(c);
        }

        return searchService.searchAndFilter(searchReq);
    }

    @GetMapping("/autosuggest")
    public List<AutosuggestCriteria> autosuggestCriteria(@RequestParam(required = false) String q) {
        return searchService.autosuggest(q);
    }
}
