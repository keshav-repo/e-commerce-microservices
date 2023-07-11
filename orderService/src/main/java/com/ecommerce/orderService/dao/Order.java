package com.ecommerce.orderService.dao;

import com.ecommerce.orderService.model.Address;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Date;

@Table(name = "orderTable")
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int  orderId;
    private int customerId;
    private Date orderDate;
    private double totalAmount;
    private String paymentStatus;//  'PAID', 'PENDING', 'FAILED'
    private String shippingStatus; // "",'SHIPPED', 'DELIVERED')

    @Embedded
    private Address address;

}
