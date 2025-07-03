package com.ridhi.Cartmicroservice.Service;

import com.ridhi.Cartmicroservice.DTO.InventoryDTO;
import com.ridhi.Cartmicroservice.DTO.ProductDTO;
import com.ridhi.Cartmicroservice.Model.Cart;
import com.ridhi.Cartmicroservice.Model.CartItem;
import com.ridhi.Cartmicroservice.Repository.CartRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class CartService {

    private final CartRepository cartRepository;
    private final RestTemplate restTemplate;

    private final String productServiceUrl = "http://localhost:8083/api/product";
    private final String inventoryServiceUrl = "http://localhost:8081/api/inventory";

    public CartService(CartRepository cartRepository, RestTemplate restTemplate) {
        this.cartRepository = cartRepository;
        this.restTemplate = restTemplate;
    }

    public Cart createCart() {
        Cart cart = new Cart();
        return cartRepository.save(cart);
    }

    public Cart addItemToCart(Long cartId, CartItem item) {
        // Check if the product exists in the Product microservice
        ProductDTO product = checkProductExistence(item.getProductId());
        if (product == null) {
            throw new RuntimeException("Product not found.");
        }

        // Check if the product has sufficient inventory
        boolean isInStock = checkInventory(item.getProductId(), item.getQuantity());
        if (!isInStock) {
            throw new RuntimeException("Product is out of stock or insufficient quantity available.");
        }

        // Set product details in CartItem (optional for better UI display)
        item.setProductName(product.getName());
        item.setPrice(product.getPrice().doubleValue());

        // Add the item to the cart
        Cart cart = cartRepository.findById(cartId)
                .orElseThrow(() -> new RuntimeException("Cart not found"));
        item.setCart(cart); // Associate the item with the cart
        cart.getItems().add(item);

        return cartRepository.save(cart);
    }

    public Cart removeItemFromCart(Long cartId, Long itemId) {
        Cart cart = cartRepository.findById(cartId)
                .orElseThrow(() -> new RuntimeException("Cart not found"));
        cart.getItems().removeIf(item -> item.getItemId().equals(itemId));
        return cartRepository.save(cart);
    }

    public Cart updateItemQuantity(Long cartId, Long itemId, int quantity) {
        Cart cart = cartRepository.findById(cartId)
                .orElseThrow(() -> new RuntimeException("Cart not found"));
        cart.getItems().stream()
                .filter(item -> item.getItemId().equals(itemId))
                .forEach(item -> item.setQuantity(quantity));
        return cartRepository.save(cart);
    }

    public Cart getCart(Long cartId) {
        return cartRepository.findById(cartId)
                .orElseThrow(() -> new RuntimeException("Cart not found"));
    }

    public ProductDTO checkProductExistence(Long productId) {
        String url = productServiceUrl + "/" + productId;
        try {
            return restTemplate.getForObject(url, ProductDTO.class);
        } catch (Exception e) {
            throw new RuntimeException("Product not found");
        }
    }

    public boolean checkInventory(Long productId, int quantity) {
        String url = inventoryServiceUrl + "/check?productId=" + productId + "&quantity=" + quantity;
        try {
            InventoryDTO inventory = restTemplate.getForObject(url, InventoryDTO.class);
            if (inventory == null) {
                throw new RuntimeException("Product not found in Inventory.");
            }
            return inventory.getStockQuantity() >= quantity;
        } catch (Exception e) {
            throw new RuntimeException("Error communicating with Inventory service: " + e.getMessage());
        }
    }

}
