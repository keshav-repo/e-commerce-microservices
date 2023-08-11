package com.ecommerce.catalog.model;


import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "products")
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = Book.class, name = "book"),
        @JsonSubTypes.Type(value = Phone.class, name = "phone")
})
public class Product {
    @Id
    private String productId;
    private String name;
    private String description;
    private String category;
    private String imgMapping;
    public Product(String name, String description, String category) {
        this.name = name;
        this.description = description;
        this.category = category;
    }
}
