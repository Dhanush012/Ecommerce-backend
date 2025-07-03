package com.ridhi.inventory.Controller;

import com.ridhi.inventory.Model.Inventory;
import com.ridhi.inventory.Service.InventoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/inventory")
public class InventoryController {

    @Autowired
    private InventoryService inventoryService;

    @PostMapping
    public ResponseEntity<Inventory> createInventory(@RequestBody Inventory inventory) {
        return ResponseEntity.ok(inventoryService.createInventory(inventory));
    }

    @GetMapping
    public ResponseEntity<List<Inventory>> getAllInventory() {
        return ResponseEntity.ok(inventoryService.getAllInventory());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Inventory> getInventoryById(@PathVariable Long id) {
        return inventoryService.getInventoryById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Inventory> updateInventory(@PathVariable Long id, @RequestBody Inventory inventory) {
        return inventoryService.updateInventory(id, inventory)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteInventory(@PathVariable Long id) {
        if (inventoryService.deleteInventory(id)) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // New endpoint for stock availability check
//    @GetMapping("/check")
//    public ResponseEntity<Inventory> checkStock(@RequestParam Long productId, @RequestParam int quantity) {
//        Inventory inventory = inventoryService.checkStock(productId, quantity);
//        if (inventory != null) {
//            return ResponseEntity.ok(inventory);
//        } else {
//            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
//        }
//    }
    @GetMapping("/check")
    public ResponseEntity<Inventory> checkStock(@RequestParam Long productId, @RequestParam int quantity) {
        Inventory inventory = inventoryService.checkStock(productId, quantity);

        if (inventory != null && inventory.getStockQuantity() >= quantity) {
            // Stock is sufficient
            return ResponseEntity.ok(inventory);
        } else if (inventory != null) {
            // Stock is insufficient but product exists
            return ResponseEntity.ok(inventory); // Returning inventory with the available stock
        } else {
            // Product not found
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }


}
