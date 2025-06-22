package com.ridhi.order.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductDTO {
    private Long productId;
    private String name;
    private String description;
    private String category;
    private String brand;

    private BigDecimal price;
    private BigDecimal discount;
    private BigDecimal finalPrice;

    private Boolean available;

    private LocalDate manufacturingDate;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}