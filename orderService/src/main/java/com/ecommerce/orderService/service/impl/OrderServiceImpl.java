package com.ecommerce.orderService.service.impl;

import com.ecommerce.orderService.dao.Order;
import com.ecommerce.orderService.dao.OrderItem;
import com.ecommerce.orderService.model.*;
import com.ecommerce.orderService.repo.OrderItemRepo;
import com.ecommerce.orderService.repo.OrderRepo;
import com.ecommerce.orderService.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Service
@Slf4j
public class OrderServiceImpl implements OrderService {
    @Autowired
    private KafkaTemplate<String, OrderPlacedEvent> kafkaTemplate;
    @Autowired
    private LoadBalancerClient loadBalancerClient;
    @Autowired
    private OrderRepo orderRepo;
    @Autowired
    private OrderItemRepo itemRepo;

    @Override
    public OrderCreatedRes createOrder(OrderPayload orderPayload) {

        List<ProductPayload> productPayloads = new ArrayList<>();

        ServiceInstance  catalogService = loadBalancerClient.choose("CATALOG SERVICE");

        String targetUrl = catalogService.getUri().toString() + "/api/product";

        RestTemplate restTemplate = new RestTemplate();

        List<OrderItem> orderItemList = new ArrayList<>();
        double totalAmount = 0;
        for(ProductPayload productPayload: orderPayload.getProducts()){
            String productIdString = String.valueOf(productPayload.getProductId());
            Product product = restTemplate.getForObject(targetUrl.concat("/").concat(productIdString), Product.class);
            OrderItem orderItem = OrderItem.builder()
                    .quantity(productPayload.getQuantity())
                    .unitPrice(product.getPrice())
                    .productId(product.getProductId())
                    .build();
            orderItemList.add(orderItem);
            totalAmount += product.getPrice()*productPayload.getQuantity();
        }

        Order order = Order.builder()
                .orderDate(new Date())
                .customerId(orderPayload.getCustomerId())
                .totalAmount(totalAmount)
                .paymentStatus("PENDING")
                .shippingStatus("NOT_SHIPPED")
                .address(orderPayload.getShippingAddress())
                .build();
        try{
            orderRepo.save(order);
        }catch (Exception exception){
             log.error(exception.getMessage());
             exception.printStackTrace();
             throw new RuntimeException("Server error");
        }

        for(OrderItem orderItem: orderItemList){
            orderItem.setOrderId(order.getOrderId());
        }
        try{
            itemRepo.saveAll(orderItemList);
        }catch (Exception exception){
            log.error(exception.getMessage());
            exception.printStackTrace();
            throw new RuntimeException("Server error");
        }
        return new OrderCreatedRes( String.valueOf(order.getOrderId()),
                order.getPaymentStatus(),
                String.valueOf(order.getTotalAmount()), "Order created successfully.");
    }

    @Override
    public OrderInfoRes fetchOrder(int orderId) {
        Order order = orderRepo.findById(orderId).orElse(null);
        if(Objects.isNull(order)){
            throw new RuntimeException("Order not found");
        }
       List<OrderItem> orderItems = itemRepo.findByOrderId(orderId);

        OrderInfoRes orderInfoRes = OrderInfoRes.builder()
                .customer_id(order.getCustomerId())
                .order_date(order.getOrderDate())
                .payment_status(order.getPaymentStatus())
                .shipping_status(order.getShippingStatus())
                .shipping_address(order.getAddress())
                .order_items(orderItems)
                .total_amount(order.getTotalAmount())
                .build();

        return orderInfoRes;
    }

    @Override
    public OrderStatus orderPlaced(int orderId) {
        // some logic for order placed
        Order order = orderRepo.findById(orderId).orElse(null);
        if(Objects.isNull(order)){
            throw new RuntimeException("Order not found");
        }
        order.setPaymentStatus("PAID");
        orderRepo.save(order);

        OrderStatus orderStatus = OrderStatus.builder()
                .orderId(orderId)
                .paymentStatus(order.getPaymentStatus())
                .shippingStatus(order.getShippingStatus())
                .build();

        // emit event for order placed
       // kafkaTemplate.sendDefault(String.valueOf(orderId), new OrderPlacedEvent( String.valueOf(orderId), String.valueOf(12) ));

        return orderStatus;
    }
}
