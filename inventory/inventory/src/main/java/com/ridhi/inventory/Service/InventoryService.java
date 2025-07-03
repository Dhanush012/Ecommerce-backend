package com.ridhi.inventory.Service;

import com.ridhi.inventory.Model.Inventory;
import com.ridhi.inventory.Repository.InventoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
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

    public Optional<Inventory> updateInventory(Long id, Inventory inventory) {
        return inventoryRepository.findById(id).map(existingInventory -> {
            existingInventory.setStockQuantity(inventory.getStockQuantity());
            existingInventory.setWarehouseLocation(inventory.getWarehouseLocation());
            existingInventory.setLastRestockedAt(inventory.getLastRestockedAt());
            return inventoryRepository.save(existingInventory);
        });
    }

    public boolean deleteInventory(Long id) {
        if (inventoryRepository.existsById(id)) {
            inventoryRepository.deleteById(id);
            return true;
        }
        return false;
    }

    // New method for stock availability check
    public Inventory checkStock(Long productId, int quantity) {
        Inventory inventory = inventoryRepository.findByProductId(productId);
        return inventory; // Null if not found
    }

}
