package com.ecommerce.orderService.service;

import com.ecommerce.orderService.model.OrderCreatedRes;
import com.ecommerce.orderService.model.OrderInfoRes;
import com.ecommerce.orderService.model.OrderPayload;
import com.ecommerce.orderService.model.OrderStatus;
import com.fasterxml.jackson.core.JsonProcessingException;

public interface OrderService {
    public OrderCreatedRes createOrder(OrderPayload orderPayload);

    public OrderInfoRes fetchOrder(int orderId);

    public OrderStatus orderPlaced(int orderId);
}
