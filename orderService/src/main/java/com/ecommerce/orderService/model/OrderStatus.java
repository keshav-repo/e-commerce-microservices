package com.ecommerce.orderService.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class OrderStatus {
    private int orderId;
    private String paymentStatus;
    private String shippingStatus;
}
