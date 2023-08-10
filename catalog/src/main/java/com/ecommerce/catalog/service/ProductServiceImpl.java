package com.ecommerce.catalog.service;

import com.ecommerce.catalog.model.Product;
import com.ecommerce.catalog.repo.ProductRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService{
    @Autowired
    private ProductRepo productRepo;
    @Override
    public List<Product> getAllproducts() {
        return productRepo.findAll();
    }

    @Override
    public Product findProductById(String productId) {
        try{
           return productRepo.findById(productId).orElseThrow();
        }catch (Exception exception){
            throw new RuntimeException("some exception, please try after sometime");
        }
    }

    @Override
    public Product saveProduct(Product product) {
        try{
            productRepo.save(product);
        }catch (Exception e){

        }
        return product;
    }

    @Override
    public void deleteProduct(String productId) {
        // we will do soft delete of data
        // also delete api will not be public
        try{
            productRepo.deleteById(productId);
        }catch (Exception exception){
            throw new RuntimeException("some exception, please try after sometime");
        }
    }
}
