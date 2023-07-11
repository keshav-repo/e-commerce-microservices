package com.ecommerce.orderService.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class PaymentPayload {
    private String method;
    private String billingAddress;
    private String cardNumber;
}
