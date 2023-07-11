package com.ecommerce.inventoryservice.consumer;

import com.ecommerce.inventoryservice.model.AdjPayload;
import com.ecommerce.inventoryservice.model.OrderPlacedEvent;
import com.ecommerce.inventoryservice.model.ProductPayload;
import com.ecommerce.inventoryservice.service.InventoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class OrderPlacedConsumer {

    @Autowired
    private InventoryService inventoryService;

    @KafkaListener(topics = "orders-topic", groupId = "order-consumer-group")
    public void consumeOrder(OrderPlacedEvent orderPlacedEvent) {

        System.out.println("Received Order: ");
        System.out.println(orderPlacedEvent.toString());

        List<ProductPayload> productList = orderPlacedEvent.getProductPurchased();
        for(ProductPayload productPayload: productList){
            AdjPayload adjPayload = new AdjPayload(productPayload.getQuantity()*-1, "Remove", "product purchased" );
            inventoryService.adjustInventory(adjPayload, productPayload.getProductId());
        }

    }

}
