package com.ridhi.inventory.Service;
import com.ridhi.inventory.Model.Inventory;
import com.ridhi.inventory.Repository.InventoryRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;

public class InventoryService {

    @Autowired
    private InventoryRepository inventoryRepository;

    public Inventory createInventory(Inventory inventory) {
        return inventoryRepository.save(inventory);
    }

    public List<Inventory> getAllInventory() {
        return inventoryRepository.findAll();
    }

    public Optional<Inventory> getInventoryById(Long id) {
        return inventoryRepository.findById(id);
    }

    public Optional<Inventory> updateInventory(Long id, Inventory updatedInventory) {
        return inventoryRepository.findById(id).map(inventory -> {
            inventory.setProductId(updatedInventory.getProductId());
            inventory.setCompanyId(updatedInventory.getCompanyId());
            inventory.setStockQuantity(updatedInventory.getStockQuantity());
            inventory.setWarehouseLocation(updatedInventory.getWarehouseLocation());
            inventory.setLastRestockedAt(updatedInventory.getLastRestockedAt());
            return inventoryRepository.save(inventory);
        });
    }

    public boolean deleteInventory(Long id) {
        if (inventoryRepository.existsById(id)) {
            inventoryRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
