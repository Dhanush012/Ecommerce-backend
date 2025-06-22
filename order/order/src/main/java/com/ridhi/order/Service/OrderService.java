package com.ridhi.order.Service;

import com.ridhi.order.Dto.*;
import com.ridhi.order.model.Order;
import com.ridhi.order.model.OrderProduct;
import com.ridhi.order.Repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final RestTemplate restTemplate;

    // Replace with hardcoded base URLs for now
    private final String cartServiceUrl = "http://localhost:8085";     // Cart Microservice
    private final String userServiceUrl = "http://localhost:8084";     // User Microservice
    private final String productServiceUrl = "http://localhost:8083";  // Product Microservice

    public Order placeOrder(OrderRequestDTO requestDTO) {
        // 1. Fetch cart by customer
        ResponseEntity<CartDTO> cartResponse = restTemplate.getForEntity(
                cartServiceUrl + "/api/cart/customer/" + requestDTO.getCustomerId(), CartDTO.class);
        CartDTO cartDTO = cartResponse.getBody();

        if (cartDTO == null || cartDTO.getItems().isEmpty()) {
            throw new RuntimeException("Cart is empty or not found");
        }

        // 2. Fetch customer details
        ResponseEntity<CustomerDTO> customerResponse = restTemplate.getForEntity(
                userServiceUrl + "/api/customeruser/" + requestDTO.getCustomerId(), CustomerDTO.class);
        CustomerDTO customer = customerResponse.getBody();

        if (customer == null) {
            throw new RuntimeException("Customer not found");
        }

        // 3. Prepare order entity
        Order order = new Order();
        order.setCustomerId(requestDTO.getCustomerId());
        order.setOrderDate(LocalDateTime.now());
        order.setStatus("PLACED");
        order.setPaymentMethod(requestDTO.getPaymentMethod());
        order.setPaymentStatus("PENDING");
        order.setShippingAddress(requestDTO.getShippingAddress());
        order.setBillingAddress(requestDTO.getBillingAddress());

        // 4. For each item in cart, fetch price from product service and calculate total
        final BigDecimal[] totalAmount = {BigDecimal.ZERO};

        List<OrderProduct> products = cartDTO.getItems().stream().map(item -> {
            // Fetch product info by product name or id (you may decide based on your cart model)
            ResponseEntity<ProductDTO> productResponse = restTemplate.getForEntity(
                    productServiceUrl + "/api/products/" + item.getProductName(), ProductDTO.class);
            ProductDTO product = productResponse.getBody();

            if (product == null) {
                throw new RuntimeException("Product not found: " + item.getProductName());
            }

            BigDecimal price = product.getPrice();
            BigDecimal itemTotal = price.multiply(BigDecimal.valueOf(item.getQuantity()));
            totalAmount[0] = totalAmount[0].add(itemTotal);

            OrderProduct orderProduct = new OrderProduct();
            orderProduct.setProductId(product.getProductId());
            orderProduct.setQuantity(item.getQuantity());
            orderProduct.setPrice(price);
            orderProduct.setOrder(order);

            return orderProduct;
        }).collect(Collectors.toList());

        order.setTotalAmount(totalAmount[0]);
        order.setDiscountAmount(BigDecimal.ZERO);
        order.setFinalAmount(totalAmount[0]);
        order.setProducts(products);

        return orderRepository.save(order);
    }

    public Order getOrderById(Long orderId) {
        return orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));
    }

    public List<Order> getOrdersByCustomerId(Long customerId) {
        return orderRepository.findByCustomerId(customerId);
    }

    public void cancelOrder(Long orderId) {
        Order order = getOrderById(orderId);
        order.setStatus("CANCELLED");
        orderRepository.save(order);
    }

}
