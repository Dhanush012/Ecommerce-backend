package com.ridhi.order.model;


import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Order {
    private Long orderId;
    private Long userId;             // FK to User
    private LocalDateTime orderDate;
    private BigDecimal totalAmount;
    private String status;          // e.g. PENDING, SHIPPED, DELIVERED
    private String paymentMethod;   // e.g. CARD, UPI, COD
    private String shippingAddress;
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<OrderItem> items;
}
