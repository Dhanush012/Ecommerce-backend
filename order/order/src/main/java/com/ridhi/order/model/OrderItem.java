package com.ridhi.order.model;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Table(name = "order_items")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderItemId;

    private Long productId;

    private int quantity;

    private BigDecimal unitPrice;

    private BigDecimal totalPrice;

    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;
}
