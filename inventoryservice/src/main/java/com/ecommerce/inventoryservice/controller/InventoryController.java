package com.ecommerce.inventoryservice.controller;

import com.ecommerce.inventoryservice.entity.Inventory;
import com.ecommerce.inventoryservice.model.AdjPayload;
import com.ecommerce.inventoryservice.model.AdjResponse;
import com.ecommerce.inventoryservice.service.InventoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/inventory")
public class InventoryController {

    @Autowired
    private InventoryService inventoryService;

    @GetMapping("/{productId}")
    public Inventory getInventory(@PathVariable int productId){
        return inventoryService.getAvailability(productId);
    }

    @PostMapping("/{productId}/adjust")
    public AdjResponse udjustInventory(@RequestBody AdjPayload adjPayload, @PathVariable int productId){
         inventoryService.adjustInventory(adjPayload, productId);
         return new AdjResponse(productId,"Updated");
    }
}
