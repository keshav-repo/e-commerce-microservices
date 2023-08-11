package com.ecommerce.catalog.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.DBRef;

import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Book extends Product{

    @DBRef
    private Author author;
    private Map<String, String> specs;

//    public static class BookBuilder extends ProductBuilder{
//        BookBuilder() {
//            super();
//        }
//    }

}
