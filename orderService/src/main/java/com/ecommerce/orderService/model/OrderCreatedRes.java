package com.ecommerce.orderService.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderCreatedRes {
    private String orderId;
    private String orderStatus;
    private String orderTotal;
    private String message;
}
