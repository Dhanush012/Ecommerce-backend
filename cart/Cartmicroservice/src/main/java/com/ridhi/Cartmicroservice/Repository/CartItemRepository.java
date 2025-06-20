package com.ridhi.Cartmicroservice.Repository;

import com.ridhi.Cartmicroservice.Model.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {
}

