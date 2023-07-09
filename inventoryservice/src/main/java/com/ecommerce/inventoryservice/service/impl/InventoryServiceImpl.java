package com.ecommerce.inventoryservice.service.impl;

import com.ecommerce.inventoryservice.dao.InventoryRepo;
import com.ecommerce.inventoryservice.entity.Inventory;
import com.ecommerce.inventoryservice.model.AdjPayload;
import com.ecommerce.inventoryservice.service.InventoryService;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class InventoryServiceImpl implements InventoryService {

    @Autowired
    private InventoryRepo inventoryRepo;

    @Override
    public Inventory getAvailability(int productId) {
        try{
            return inventoryRepo.findByProductId(productId);
        }catch (Exception exception){
            log.error(exception.getMessage());
            exception.printStackTrace();
            throw new RuntimeException("Some problem occoured in getting invetory");
        }
    }

    @Override
    @Transactional
    public void adjustInventory(AdjPayload adjPayload, int productId) {
        Inventory inventory = getAvailability(productId);
        inventory.setQuantity(inventory.getQuantity()+adjPayload.getQuantity());
        try{
            inventoryRepo.save(inventory);
        }catch (Exception exception){
            log.error(exception.getMessage());
            exception.printStackTrace();
            throw new RuntimeException("Some problem occoured in adjusting inventory");
        }
    }
}
