package com.ridhi.Cartmicroservice.Controller;

import com.ridhi.Cartmicroservice.Model.Cart;
import com.ridhi.Cartmicroservice.Model.CartItem;
import com.ridhi.Cartmicroservice.Service.CartService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cart")
public class CartController {

    private final CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @PostMapping
    public ResponseEntity<Cart> createCart() {
        return new ResponseEntity<>(cartService.createCart(), HttpStatus.CREATED);
    }

    @PostMapping("/{cartId}/items")
    public ResponseEntity<?> addItemToCart(@PathVariable Long cartId, @RequestBody CartItem item) {
        try {
            Cart updatedCart = cartService.addItemToCart(cartId, item);
            return new ResponseEntity<>(updatedCart, HttpStatus.CREATED);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @DeleteMapping("/{cartId}/items/{itemId}")
    public ResponseEntity<?> removeItemFromCart(@PathVariable Long cartId, @PathVariable Long itemId) {
        try {
            Cart updatedCart = cartService.removeItemFromCart(cartId, itemId);
            return new ResponseEntity<>(updatedCart, HttpStatus.OK);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Item or Cart not found.");
        }
    }

    @PatchMapping("/{cartId}/items/{itemId}")
    public ResponseEntity<?> updateItemQuantity(
            @PathVariable Long cartId,
            @PathVariable Long itemId,
            @RequestParam int quantity) {
        try {
            Cart updatedCart = cartService.updateItemQuantity(cartId, itemId, quantity);
            return new ResponseEntity<>(updatedCart, HttpStatus.OK);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @GetMapping("/{cartId}")
    public ResponseEntity<?> getCart(@PathVariable Long cartId) {
        try {
            Cart cart = cartService.getCart(cartId);
            return new ResponseEntity<>(cart, HttpStatus.OK);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Cart not found.");
        }
    }
}
