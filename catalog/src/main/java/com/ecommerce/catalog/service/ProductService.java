package com.ecommerce.catalog.service;

import com.ecommerce.catalog.model.Product;

import java.util.List;

public interface ProductService {
    public List<Product> getAllproducts();
    public Product findProductById(String productId);
    public Product saveProduct(Product product);
    public void deleteProduct(String productId);

}
