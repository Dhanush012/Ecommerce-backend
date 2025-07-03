package com.ridhi.Cartmicroservice.Model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Objects;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "cart_item")
public class CartItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Auto-incremented primary key
    private Long itemId;

    @Column(nullable = false) // Product name
    private String productName;

    @Column(nullable = false) // Product ID
    private Long productId;

    @Column(nullable = false) // Quantity cannot be null
    private int quantity;

    @Column(nullable = false) // Price cannot be null
    private double price;

    @ManyToOne(fetch = FetchType.LAZY) // Many items belong to one cart
    @JoinColumn(name = "cart_id", nullable = true) // Foreign key to Cart
    @JsonBackReference
    private Cart cart;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CartItem cartItem = (CartItem) o;
        return Objects.equals(itemId, cartItem.itemId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(itemId);
    }
}
