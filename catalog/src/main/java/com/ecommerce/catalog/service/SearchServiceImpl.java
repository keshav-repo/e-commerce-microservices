package com.ecommerce.catalog.service;

import com.ecommerce.catalog.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.BasicQuery;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

    @Override
    public List<AutosuggestCriteria> autosuggest(String q) {
        Criteria criteria = new Criteria().orOperator(
                Criteria.where("category").regex(q, "i"),
                Criteria.where("name").regex(q, "i"),
                Criteria.where("brand").regex(q, "i")
        );
        List<Product> productList = template.find(new Query(criteria), Product.class);
        Set<AutosuggestCriteria> criteriaSet = new HashSet<>();
        for (Product product : productList) {
            if (product.getBrand() != null && isPatternMatch(product.getBrand(), q)) {
                criteriaSet.add(new AutosuggestCriteria("brand", product.getBrand()));
            }
            if (product.getCategory() != null && isPatternMatch(product.getCategory(), q)) {
                criteriaSet.add(new AutosuggestCriteria("category", product.getCategory() ));
            }
        }
        return new ArrayList<>(criteriaSet);
    }

    private boolean isPatternMatch(String text, String pattern) {
        Pattern regexPattern = Pattern.compile(pattern, Pattern.CASE_INSENSITIVE);
        Matcher matcher = regexPattern.matcher(text);
        return matcher.find();
    }
}
