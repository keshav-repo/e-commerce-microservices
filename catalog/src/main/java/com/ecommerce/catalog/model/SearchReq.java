package com.ecommerce.catalog.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class SearchReq {
    private String q;
    private int size;
    private int page;
    public SearchReq(String q, int size, int page) {
        this.q = q;
        this.size = size;
        this.page = page;
    }
    List<String> brands;
    List<String> categories;
}
