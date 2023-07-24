package com.ecommerce.orderService.service.impl;

import com.ecommerce.orderService.dao.Order;
import com.ecommerce.orderService.dao.OrderItem;
import com.ecommerce.orderService.model.*;
import com.ecommerce.orderService.repo.OrderItemRepo;
import com.ecommerce.orderService.repo.OrderRepo;
import com.ecommerce.orderService.service.OrderService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;

@Service
@Slf4j
public class OrderServiceImpl implements OrderService {
    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;
    @Autowired
    private LoadBalancerClient loadBalancerClient;
    @Autowired
    private OrderRepo orderRepo;
    @Autowired
    private OrderItemRepo itemRepo;

    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public OrderCreatedRes createOrder(OrderPayload orderPayload) {

        List<ProductPayload> productPayloads = new ArrayList<>();

        ServiceInstance catalogService = loadBalancerClient.choose("CATALOG SERVICE");

        String targetUrl = catalogService.getUri().toString() + "/api/product";

        RestTemplate restTemplate = new RestTemplate();

        List<OrderItem> orderItemList = new ArrayList<>();
        double totalAmount = 0;
        for (ProductPayload productPayload : orderPayload.getProducts()) {
            String productIdString = String.valueOf(productPayload.getProductId());
            Product product = restTemplate.getForObject(targetUrl.concat("/").concat(productIdString), Product.class);
            OrderItem orderItem = OrderItem.builder()
                    .quantity(productPayload.getQuantity())
                    .unitPrice(product.getPrice())
                    .productId(product.getProductId())
                    .build();
            orderItemList.add(orderItem);
            totalAmount += product.getPrice() * productPayload.getQuantity();
        }

        Order order = Order.builder()
                .orderDate(new Date())
                .customerId(orderPayload.getCustomerId())
                .totalAmount(totalAmount)
                .paymentStatus("PENDING")
                .shippingStatus("NOT_SHIPPED")
                .address(orderPayload.getShippingAddress())
                .build();
        try {
            orderRepo.save(order);
        } catch (Exception exception) {
            log.error(exception.getMessage());
            exception.printStackTrace();
            throw new RuntimeException("Server error");
        }

        for (OrderItem orderItem : orderItemList) {
            orderItem.setOrderId(order.getOrderId());
        }
        try {
            itemRepo.saveAll(orderItemList);
        } catch (Exception exception) {
            log.error(exception.getMessage());
            exception.printStackTrace();
            throw new RuntimeException("Server error");
        }
        return new OrderCreatedRes(String.valueOf(order.getOrderId()),
                order.getPaymentStatus(),
                String.valueOf(order.getTotalAmount()), "Order created successfully.");
    }

    @Override
    public OrderInfoRes fetchOrder(int orderId) {
        Order order = orderRepo.findById(orderId).orElse(null);
        if (Objects.isNull(order)) {
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

    /**
     *
     * @param orderId
     * @return OrderStatus
     */
    @Override
    public OrderStatus orderPlaced(int orderId) {
        // some logic for order placed
        Order order = orderRepo.findById(orderId).orElse(null);
        if (Objects.isNull(order)) {
            throw new RuntimeException("Order not found");
        }
        order.setPaymentStatus("PAID");
        orderRepo.save(order);

        OrderStatus orderStatus = OrderStatus.builder()
                .orderId(orderId)
                .paymentStatus(order.getPaymentStatus())
                .shippingStatus(order.getShippingStatus())
                .build();

        List<OrderItem> orderItems = itemRepo.findByOrderId(orderId);

        // emit event for order placed
        List<ProductPayload> productPayloads = new ArrayList<>();
        for (OrderItem orderItem : orderItems) {
            productPayloads.add(new ProductPayload(orderItem.getProductId(), orderItem.getQuantity()));
        }

        OrderPlacedEvent orderPlacedEvent = new OrderPlacedEvent(String.valueOf(orderId), String.valueOf(12), productPayloads);
        String orderPlacedEventString = null;
        try {
            orderPlacedEventString = objectMapper.writeValueAsString(orderPlacedEvent);
        } catch (JsonProcessingException jsonProcessingException) {
            jsonProcessingException.printStackTrace();
        }

        CompletableFuture<SendResult<String, String>> completableFuture = kafkaTemplate.sendDefault(String.valueOf(orderId), orderPlacedEventString);

        completableFuture
            .exceptionally(ex -> {
                log.error(ex.getMessage());
                ex.printStackTrace();
                return null;
            })
            .thenAccept(result -> System.out.println(result));

        return orderStatus;
    }
}
