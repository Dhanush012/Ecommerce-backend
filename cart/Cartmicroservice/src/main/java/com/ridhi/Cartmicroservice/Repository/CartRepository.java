package com.ridhi.Cartmicroservice.Repository;

import com.ridhi.Cartmicroservice.Model.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartRepository extends JpaRepository<Cart, Long> {
}
