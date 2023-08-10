package com.ecommerce.catalog.service;

import com.ecommerce.catalog.model.Author;
import com.ecommerce.catalog.model.Book;
import com.ecommerce.catalog.model.Product;
import com.ecommerce.catalog.repo.ProductRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@Slf4j
public class ProductServiceImpl implements ProductService {
    @Autowired
    private ProductRepo productRepo;

    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public List<Product> getAllproducts() {
        List<Product> productList = productRepo.findAll();
        return productList;
    }

    @Override
    public Product findProductById(String productId) {
        try {
            return productRepo.findById(productId).orElseThrow();
        } catch (Exception exception) {

            throw new RuntimeException("some exception, please try after sometime");
        }
    }

    @Override
    public Product saveProduct(Product product) {
        try {
            if (product instanceof Book) {
                Book book = (Book) product;
                Author author = book.getAuthor();
                Query query = new Query();
                query.addCriteria(Criteria.where("name").is(author.getName()));
                Author author1 = mongoTemplate.findOne(query, Author.class);
                if (!Objects.isNull(author1) && author1.getName().equals(author.getName())) {
                    book.setAuthor(author1);
                } else {
                    mongoTemplate.save(author);
                }
            }
            productRepo.save(product);
        } catch (Exception e) {
            log.error(e.getMessage());
            e.printStackTrace();
        }
        return product;
    }


    @Override
    public void deleteProduct(String productId) {
        // we will do soft delete of data
        // also delete api will not be public
        try {
            productRepo.deleteById(productId);
        } catch (Exception exception) {
            throw new RuntimeException("some exception, please try after sometime");
        }
    }
}
