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
    public ResponseEntity<Cart> addItemToCart(@PathVariable Long cartId, @RequestBody CartItem item) {
        return new ResponseEntity<>(cartService.addItemToCart(cartId, item), HttpStatus.CREATED);
    }

    @DeleteMapping("/{cartId}/items/{itemId}")
    public ResponseEntity<Cart> removeItemFromCart(@PathVariable Long cartId, @PathVariable Long itemId) {
        return new ResponseEntity<>(cartService.removeItemFromCart(cartId, itemId), HttpStatus.OK);
    }

    @PatchMapping("/{cartId}/items/{itemId}")
    public ResponseEntity<Cart> updateItemQuantity(
            @PathVariable Long cartId,
            @PathVariable Long itemId,
            @RequestParam int quantity) {
        return new ResponseEntity<>(cartService.updateItemQuantity(cartId, itemId, quantity), HttpStatus.OK);
    }

    @GetMapping("/{cartId}")
    public ResponseEntity<Cart> getCart(@PathVariable Long cartId) {
        return new ResponseEntity<>(cartService.getCart(cartId), HttpStatus.OK);
    }
}

