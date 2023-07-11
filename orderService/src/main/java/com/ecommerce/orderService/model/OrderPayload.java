package com.ecommerce.orderService.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class OrderPayload {
    private Address shippingAddress;
    private int customerId;
    private List<ProductPayload> products;
    private PaymentPayload payment;
}
