package com.ridhi.order.Dto;

import lombok.Data;

import java.util.List;

@Data
public class CartDTO {
    private Long cartId;
    private List<CartItemDTO> items;
}
