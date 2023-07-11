package com.ecommerce.orderService.controller;

import com.ecommerce.orderService.model.OrderCreatedRes;
import com.ecommerce.orderService.model.OrderInfoRes;
import com.ecommerce.orderService.model.OrderPayload;
import com.ecommerce.orderService.model.OrderStatus;
import com.ecommerce.orderService.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping
    public OrderCreatedRes createOrder(@RequestBody OrderPayload orderPayload) {
        return orderService.createOrder(orderPayload);
    }

    @GetMapping("{orderId}")
    public OrderInfoRes orderInfo(@PathVariable Integer orderId) {
        return orderService.fetchOrder(orderId);
    }

    // we can also trigger the event to place order
    @PostMapping("{orderId}/place")
    public OrderStatus placeOrder(@PathVariable int orderId){
        return orderService.orderPlaced(orderId);
    }

}
