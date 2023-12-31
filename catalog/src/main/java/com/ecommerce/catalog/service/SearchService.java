package com.ecommerce.catalog.service;

import com.ecommerce.catalog.model.AutosuggestCriteria;
import com.ecommerce.catalog.model.SearchReq;
import com.ecommerce.catalog.model.SearchRes;
import com.ecommerce.catalog.model.SearchResWithFilter;

import java.util.List;

public interface SearchService {
    public SearchRes search(SearchReq searchReq);
    public List<AutosuggestCriteria> autosuggest(String q);
    public SearchResWithFilter searchAndFilter(SearchReq searchReq);
}
