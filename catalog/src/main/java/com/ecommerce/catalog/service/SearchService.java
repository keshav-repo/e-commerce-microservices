package com.ecommerce.catalog.service;

import com.ecommerce.catalog.model.SearchReq;
import com.ecommerce.catalog.model.SearchRes;

public interface SearchService {
    public SearchRes search(SearchReq searchReq);
}
