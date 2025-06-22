package com.ridhi.order.Dto;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class OrderResponseDTO {
    private Long orderId;
    private Long customerId;
    private String status;
    private String paymentStatus;
    private double totalAmount;
    private double discountAmount;
    private double finalAmount;
    private LocalDateTime orderDate;
    private List<CartItemDTO> products;
}
