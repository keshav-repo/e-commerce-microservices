package com.ecommerce.catalog.service;

import com.ecommerce.catalog.model.*;
import com.ecommerce.catalog.util.Commonutils;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.GroupOperation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.*;

@Service
@Slf4j
public class SearchServiceImpl implements SearchService {
    @Autowired
    private MongoTemplate template;

    @Override
    public SearchRes search(SearchReq searchReq) {

//        List<Criteria> orCriteriaList = new LinkedList<>();
//        List<Criteria> andCriteriaList = new LinkedList<>();
//
//        orCriteriaList.add(Criteria.where("name").regex(searchReq.getQ(), "i"));
//
//        if (!Objects.isNull(searchReq.getBrands()) && !searchReq.getBrands().isEmpty()) {
//            andCriteriaList.add(Criteria.where("brand").in(searchReq.getBrands()));
//        }else {
//            orCriteriaList.add(Criteria.where("brand").regex(searchReq.getQ(), "i"));
//        }
//
//        if (!Objects.isNull(searchReq.getCategory()) && !searchReq.getCategory().isEmpty()) {
//            andCriteriaList.add(Criteria.where("category").in(searchReq.getCategory()));
//        }
//        Criteria criteria = new Criteria().orOperator(orCriteriaList).andOperator(andCriteriaList);

        Criteria criteria = buildCriteria(searchReq);

        Query query = new Query(criteria)
                .with(PageRequest.of(searchReq.getPage() - 1, searchReq.getSize()));

        List<Product> list = template.find(query, Product.class);

        int count = (int) template.count(new Query(criteria), SearchModel.class);
        int totalPages = count / searchReq.getSize() + (count % searchReq.getSize() != 0 ? 1 : 0);

        return new SearchRes(searchReq.getPage(), list.size(), totalPages, list);
    }

    public Criteria buildCriteria(SearchReq searchReq) {
        List<Criteria> orCriteriaList = new LinkedList<>();
        List<Criteria> andCriteriaList = new LinkedList<>();

        orCriteriaList.add(Criteria.where("name").regex(searchReq.getQ(), "i"));

        if (!Objects.isNull(searchReq.getBrands()) && !searchReq.getBrands().isEmpty()) {
            andCriteriaList.add(Criteria.where("brand").in(searchReq.getBrands()));
        }
        orCriteriaList.add(Criteria.where("brand").regex(searchReq.getQ(), "i"));

        if (!Objects.isNull(searchReq.getCategories()) && !searchReq.getCategories().isEmpty()) {
            andCriteriaList.add(Criteria.where("category").in(searchReq.getCategories()));
        }
        Criteria criteria = new Criteria(); // .andOperator(andCriteriaList).orOperator(orCriteriaList);
        if (!andCriteriaList.isEmpty()) {
            criteria = criteria.andOperator(andCriteriaList);
        }
        criteria = criteria.orOperator(orCriteriaList);

        return criteria;
    }

    public List<String> getDistinctValue(String field, String collectionName, Criteria criteria) {
        GroupOperation groupByBrand = Aggregation.group(field);

        Aggregation aggregation = Aggregation.newAggregation(Aggregation.match(criteria), groupByBrand);

        List<String> distinctvalue = template.aggregate(aggregation, collectionName, String.class)
                .getMappedResults();

        List<String> distinctvalueRes = new LinkedList<>();
        ObjectMapper objectMapper = new ObjectMapper();
        for (String s : distinctvalue) {
            try {
                JsonNode jsonNode = objectMapper.readTree(s);
                String idValue = jsonNode.get("_id").asText();
                distinctvalueRes.add(idValue);
            } catch (JsonProcessingException e) {
                log.info("error parsing json");
            }
        }

        return distinctvalueRes;
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
            if (product.getBrand() != null && Commonutils.isPatternMatch(product.getBrand(), q)) {
                criteriaSet.add(new AutosuggestCriteria("brand", product.getBrand()));
            }
            if (product.getCategory() != null && Commonutils.isPatternMatch(product.getCategory(), q)) {
                criteriaSet.add(new AutosuggestCriteria("category", product.getCategory()));
            }
        }
        return new ArrayList<>(criteriaSet);
    }

    @Override
    public SearchResWithFilter searchAndFilter(SearchReq searchReq) {
        SearchRes searchRes = this.search(searchReq);

        Criteria criteria = buildCriteria(searchReq);

        SearchResWithFilter searchResWithFilter = new SearchResWithFilter(searchRes);
        Filter filter = new Filter("brand", getDistinctValue("brand", "products", criteria));
        Filter filter2 = new Filter("category", getDistinctValue("category", "products", criteria));
        searchResWithFilter.setFilters(List.of(filter, filter2));
        return searchResWithFilter;
    }

}
