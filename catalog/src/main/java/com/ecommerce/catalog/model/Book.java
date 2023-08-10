package com.ecommerce.catalog.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.DBRef;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Book extends Product{

    @DBRef
    private Author author;
    private String language;
    private String paperback;
    private String readingAge;
    private String itemWeight;
    private String countryOfOrigin;

//    public static class BookBuilder extends ProductBuilder{
//        BookBuilder() {
//            super();
//        }
//    }

}
