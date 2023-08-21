package com.ecommerce.catalog.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class SearchResWithFilter extends SearchRes{
    private List<Filter> filters;
    public SearchResWithFilter(SearchRes searchRes) {
        super.setList(searchRes.getList());
        super.setPageNo(searchRes.getPageNo());
        super.setTotalPages(searchRes.getTotalPages());
        super.setSize(searchRes.getSize());
    }
}
