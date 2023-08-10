package com.ecommerce.catalog.controller;

import com.ecommerce.catalog.model.Product;
import com.ecommerce.catalog.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/product")
public class CatalogController {

    @Autowired
    private ProductService productService;

    @GetMapping
    public List<Product> getAllProduct(){
        return productService.getAllproducts();
    }

    @GetMapping("/{productId}")
    public Product getProduct(@PathVariable String productId){
        return productService.findProductById(productId);
    }

    @PostMapping
    public Product saveProduct(@RequestBody Product product){
       return productService.saveProduct(product);
    }

    @PutMapping("/{productId}")
    public  Product updateProduct(@RequestBody Product productBody, @PathVariable String productId){
        return productService.saveProduct(productBody);
    }

    @DeleteMapping("/{productId}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void deleteProduct(@PathVariable String productId){
        productService.deleteProduct(productId);
    }
}
