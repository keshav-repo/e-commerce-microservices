package com.ecommerce.catalog.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Phone extends Product{
    private String display;
    private String capacity;
    private String rearCamera;
    private String frontCamera;
    private String batteryBackup;
    private String warrenty;
    private String height;
    private Map<String, String> specs;
}
