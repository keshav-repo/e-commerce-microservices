package com.ecommerce.inventoryservice.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AdjResponse {
    private int productId;
    private String message;
}
