package com.ridhi.cart.Model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "cart_item")
public class CartItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Auto-incremented primary key
    private Long itemId;

    @Column(nullable = false) // Product name can be null in database
    private String productName;

    @Column(nullable = false) // Quantity cannot be null
    private int quantity;

    @Column(nullable = false) // Price cannot be null
    private double price;

    @ManyToOne(fetch = FetchType.LAZY) // Many items belong to one cart
    @JoinColumn(name = "cart_id", nullable = true) // Foreign key to Cart, can be null initially
    @JsonBackReference
    private Cart cart;
}
