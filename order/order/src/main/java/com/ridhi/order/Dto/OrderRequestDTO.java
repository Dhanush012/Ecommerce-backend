package com.ridhi.order.Dto;

import lombok.Data;

@Data
public class OrderRequestDTO {
    private Long customerId;
    private String shippingAddress;
    private String billingAddress;
    private String paymentMethod;
}
