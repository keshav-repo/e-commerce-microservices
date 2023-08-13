package com.ecommerce.catalog.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "productCard")
@Builder
public class SearchModel {
    @Id
    private String id;
    String url;
    String brand;
    String name;
    String discountedPrice;
    String actualPrice;
    String imageUrl1;
    List<String> tags;
}
