package com.ridhi.Cartmicroservice.Service;

import com.ridhi.Cartmicroservice.Model.Cart;
import com.ridhi.Cartmicroservice.Model.CartItem;
import com.ridhi.Cartmicroservice.Repository.CartItemRepository;
import com.ridhi.Cartmicroservice.Repository.CartRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CartService {

    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;

    public Cart createCart() {
        Cart cart = new Cart();
        return cartRepository.save(cart);
    }

    public CartService(CartRepository cartRepository, CartItemRepository cartItemRepository) {
        this.cartRepository = cartRepository;
        this.cartItemRepository = cartItemRepository;
    }

    public Cart addItemToCart(Long cartId, CartItem item) {
        Cart cart = cartRepository.findById(cartId).orElse(new Cart());
        item.setCart(cart);
        cart.getItems().add(item);
        return cartRepository.save(cart);
    }

    public Cart removeItemFromCart(Long cartId, Long itemId) {
        Cart cart = cartRepository.findById(cartId).orElseThrow(() -> new RuntimeException("Cart not found"));
        cart.getItems().removeIf(item -> item.getItemId().equals(itemId));
        return cartRepository.save(cart);
    }

    public Cart updateItemQuantity(Long cartId, Long itemId, int quantity) {
        Cart cart = cartRepository.findById(cartId).orElseThrow(() -> new RuntimeException("Cart not found"));
        for (CartItem item : cart.getItems()) {
            if (item.getItemId().equals(itemId)) {
                item.setQuantity(quantity);
                cartItemRepository.save(item);
            }
        }
        return cart;
    }

    public Cart getCart(Long cartId) {
        return cartRepository.findById(cartId).orElseThrow(() -> new RuntimeException("Cart not found"));
    }
}

