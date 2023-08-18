package com.ecommerce.catalog.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Clothes extends Product{
    private String brand;
    private String stars;
    private String rating;
    private String disPrice;
    private List<String> imageList;
    private String actualPrice;
    private String discountPct;
    private List<String> sizes;
    private Map<String, String> details;
    private Map<String, String> specs;
}

