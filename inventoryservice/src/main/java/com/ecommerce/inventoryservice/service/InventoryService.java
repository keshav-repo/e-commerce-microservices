package com.ecommerce.inventoryservice.service;

import com.ecommerce.inventoryservice.entity.Inventory;
import com.ecommerce.inventoryservice.model.AdjPayload;

public interface InventoryService {
    public Inventory getAvailability(int productId);
    public void adjustInventory(AdjPayload adjPayload, int productId);
}
