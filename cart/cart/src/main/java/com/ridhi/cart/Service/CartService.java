package com.ridhi.cart.Service;

import com.ridhi.cart.Model.Cart;
import com.ridhi.cart.Model.CartItem;
import com.ridhi.cart.Repository.CartItemRepository;
import com.ridhi.cart.Repository.CartRepository;
import org.springframework.stereotype.Service;


@Service
public class CartService {

    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;

    public CartService(CartRepository cartRepository, CartItemRepository cartItemRepository) {
        this.cartRepository = cartRepository;
        this.cartItemRepository = cartItemRepository;
    }

    public Cart createCart() {
        Cart cart = new Cart();
        return cartRepository.save(cart);
    }

    public Cart addItemToCart(Long cartId, CartItem item) {
        Cart cart = cartRepository.findById(cartId)
                .orElseThrow(() -> new RuntimeException("Cart not found"));

        // Associate the item with the cart
        item.setCart(cart);
        cart.getItems().add(item);

        return cartRepository.save(cart); // Cascade persists the item
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
