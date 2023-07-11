package com.ecommerce.orderService.model;

import com.ecommerce.orderService.dao.OrderItem;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderInfoRes {
    private int orderId;
    private int customer_id;
    private Date order_date;
    private double total_amount;
    private String payment_status;
    private Address shipping_address;
    private String shipping_status;
    private List<OrderItem> order_items;
}
