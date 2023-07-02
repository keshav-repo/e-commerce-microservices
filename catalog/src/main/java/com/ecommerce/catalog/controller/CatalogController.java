package com.ecommerce.catalog.controller;

import com.ecommerce.catalog.model.Product;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

@RestController
@RequestMapping("/api/product")
public class CatalogController {

    private List<Product> productList;

    public CatalogController() {
        this.productList = new ArrayList<>();
        this.productList.add(Product.builder()
            .productId(1).name("Product 1").description("This is product 1.")
            .price(29.99).category("Electronics").build());
        this.productList.add(Product.builder()
                .productId(2).name("Product 2").description("This is product 2.")
                .price(49.99).category("Clothing").build());
    }

    @GetMapping
    public List<Product> getAllProduct(){
        return productList;
    }

    @GetMapping("/{productId}")
    public Product getProduct(@PathVariable String productId){
        int pid = Integer.parseInt(productId);
        return productList.stream().filter(product -> product.getProductId() == pid )
                .findFirst().orElse(null);
    }

    @PostMapping
    public Product saveProduct(@RequestBody Product product){
        product.setProductId(productList.size()+1);
        productList.add(product);
        return product;
    }

    @PutMapping("/{productId}")
    public  Product updateProduct(@RequestBody Product productBody, @PathVariable String productId){
        int pid = Integer.parseInt(productId);
        int productIdx = IntStream.range(0, productList.size())
                .filter(i-> productList.get(i).getProductId() == pid)
                .findFirst()
                .orElse(-1);
        productList.set(productIdx, productBody);
        return productBody;
    }

    @DeleteMapping("/{productId}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void deleteProduct(@PathVariable String productId){
        int pid = Integer.parseInt(productId);
        int productIdx = IntStream.range(0, productList.size())
                .filter(i-> productList.get(i).getProductId() == pid)
                .findFirst()
                .orElse(-1);
        productList.remove(productIdx);
    }
}
