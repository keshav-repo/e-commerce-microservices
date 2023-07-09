package com.ecommerce.inventoryservice.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AdjPayload {
    private int quantity;
    private String adjustmentType;
    private String reason;
}
