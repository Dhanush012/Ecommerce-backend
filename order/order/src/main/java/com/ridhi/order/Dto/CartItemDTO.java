package com.ridhi.order.Dto;

import lombok.Data;

@Data
public class CartItemDTO {
    private Long itemId;
    private String productName;
    private int quantity;
    private double price;
}
