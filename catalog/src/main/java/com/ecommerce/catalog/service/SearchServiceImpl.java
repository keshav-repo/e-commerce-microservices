package com.ecommerce.catalog.service;

import com.ecommerce.catalog.model.Product;
import com.ecommerce.catalog.model.SearchModel;
import com.ecommerce.catalog.model.SearchReq;
import com.ecommerce.catalog.model.SearchRes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SearchServiceImpl implements SearchService {
    @Autowired
    private MongoTemplate template;

    @Override
    public SearchRes search(SearchReq searchReq) {

        Criteria criteria = new Criteria().orOperator(
                Criteria.where("name").regex(searchReq.getQ(), "i"),
                Criteria.where("brand").regex(searchReq.getQ(), "i")
        );

        Query query = new Query(criteria).with(PageRequest.of(searchReq.getPage() - 1, searchReq.getSize()));

        List<Product> list = template.find(query, Product.class);

        int count = (int) template.count(new Query(criteria), SearchModel.class);
        int totalPages = count / searchReq.getSize() + (count % searchReq.getSize() != 0 ? 1 : 0);

        return new SearchRes(searchReq.getPage(), list.size(), totalPages, list);
    }
}
