package com.ecommerce.catalog.controller;

import com.ecommerce.catalog.model.SearchModel;
import com.ecommerce.catalog.model.SearchRes;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.data.domain.Pageable;


import java.util.List;

@RestController
@RequestMapping("/api/search")
public class SearchController {
    @Autowired
    private MongoTemplate template;

    @GetMapping
    public SearchRes search(@RequestParam(required = false) String q,
                            @RequestParam(required = false, defaultValue = "1") int page,
                            @RequestParam(required = false, defaultValue = "10") int size) {

        Criteria criteria = new Criteria().orOperator(
                Criteria.where("name").regex(q, "i"),
                Criteria.where("brand").regex(q, "i"),
                Criteria.where("tag").in(q)
        );

        Query query = new Query(criteria).with(PageRequest.of(page - 1, size));

        List<SearchModel> list = template.find(query, SearchModel.class);

        int count = (int) template.count(new Query(criteria), SearchModel.class);
        int totalPages = count / size + (count % size != 0 ? 1 : 0);

        return new SearchRes(page, list.size(), totalPages, list);
    }

}