package com.ridhi.Cartmicroservice.DTO;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InventoryDTO {
    private Long productId;
    private Integer stockQuantity;
    private String warehouseLocation;
}

