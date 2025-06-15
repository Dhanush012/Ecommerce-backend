package com.ridhi.order.Service;

import com.ridhi.order.Repository.OrderRepository;
import com.ridhi.order.model.Order;
import com.ridhi.order.model.OrderProduct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

//    @Autowired
//    private RestTemplate restTemplate; // You can use WebClient if preferred
//
//    public Order createOrder(Order order) {
//        if (order.getProducts() != null) {
//            for (OrderProduct op : order.getProducts()) {
//                // Fetch price from Product microservice
//                ProductResponse product = restTemplate.getForObject(
//                        "http://PRODUCT-SERVICE/api/products/" + op.getProductId(),
//                        ProductResponse.class
//                );
//
//                if (product != null) {
//                    op.setPrice(product.getPrice());
//                }
//
//                op.setOrder(order);
//            }
//        }
//
//        // Optionally calculate finalAmount
//        BigDecimal total = order.getProducts().stream()
//                .map(p -> p.getPrice().multiply(BigDecimal.valueOf(p.getQuantity())))
//                .reduce(BigDecimal.ZERO, BigDecimal::add);
//
//        order.setTotalAmount(total);
//        order.setFinalAmount(total.subtract(order.getDiscountAmount() != null ? order.getDiscountAmount() : BigDecimal.ZERO));
//
//        return orderRepository.save(order);
//    }


    public Order createOrder(Order order) {
        if (order.getProducts() != null && !order.getProducts().isEmpty()) {

            BigDecimal total = BigDecimal.ZERO;

            for (OrderProduct op : order.getProducts()) {
                if (op.getPrice() == null || op.getQuantity() == null) {
                    throw new IllegalArgumentException("Price and quantity must be provided for each product.");
                }

                op.setOrder(order); // establish relationship
                total = total.add(op.getPrice().multiply(BigDecimal.valueOf(op.getQuantity())));
            }

            order.setTotalAmount(total);

            BigDecimal discount = order.getDiscountAmount() != null ? order.getDiscountAmount() : BigDecimal.ZERO;
            order.setFinalAmount(total.subtract(discount));
        }

        return orderRepository.save(order);
    }



    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    public Optional<Order> getOrderById(Long id) {
        return orderRepository.findById(id);
    }

//    public Optional<Order> updateOrder(Long id, Order updatedOrder) {
//        return orderRepository.findById(id).map(order -> {
//            order.setStatus(updatedOrder.getStatus());
//            order.setPaymentMethod(updatedOrder.getPaymentMethod());
//            order.setPaymentStatus(updatedOrder.getPaymentStatus());
//            order.setShippingAddress(updatedOrder.getShippingAddress());
//            order.setBillingAddress(updatedOrder.getBillingAddress());
//            order.setDeliveryDate(updatedOrder.getDeliveryDate());
//            order.setTrackingNumber(updatedOrder.getTrackingNumber());
//            order.setDiscountAmount(updatedOrder.getDiscountAmount());
//
//            order.getProducts().clear();
//            if (updatedOrder.getProducts() != null) {
//                for (OrderProduct op : updatedOrder.getProducts()) {
//                    ProductResponse product = restTemplate.getForObject(
//                            "http://PRODUCT-SERVICE/api/products/" + op.getProductId(),
//                            ProductResponse.class
//                    );
//                    if (product != null) {
//                        op.setPrice(product.getPrice());
//                    }
//                    op.setOrder(order);
//                    order.getProducts().add(op);
//                }
//            }
//
//            BigDecimal total = order.getProducts().stream()
//                    .map(p -> p.getPrice().multiply(BigDecimal.valueOf(p.getQuantity())))
//                    .reduce(BigDecimal.ZERO, BigDecimal::add);
//
//            order.setTotalAmount(total);
//            order.setFinalAmount(total.subtract(order.getDiscountAmount() != null ? order.getDiscountAmount() : BigDecimal.ZERO));
//
//            return orderRepository.save(order);
//        });
//    }

    public Optional<Order> updateOrder(Long id, Order updatedOrder) {
        return orderRepository.findById(id).map(order -> {
            // Update basic fields
            order.setStatus(updatedOrder.getStatus());
            order.setPaymentMethod(updatedOrder.getPaymentMethod());
            order.setPaymentStatus(updatedOrder.getPaymentStatus());
            order.setShippingAddress(updatedOrder.getShippingAddress());
            order.setBillingAddress(updatedOrder.getBillingAddress());
            order.setDeliveryDate(updatedOrder.getDeliveryDate());
            order.setTrackingNumber(updatedOrder.getTrackingNumber());
            order.setDiscountAmount(updatedOrder.getDiscountAmount());

            // Clear and replace existing products
            order.getProducts().clear();

            BigDecimal total = BigDecimal.ZERO;

            if (updatedOrder.getProducts() != null && !updatedOrder.getProducts().isEmpty()) {
                for (OrderProduct op : updatedOrder.getProducts()) {
                    if (op.getPrice() == null || op.getQuantity() == null) {
                        throw new IllegalArgumentException("Price and quantity must be provided for each product.");
                    }

                    op.setOrder(order); // maintain the relationship
                    total = total.add(op.getPrice().multiply(BigDecimal.valueOf(op.getQuantity())));
                    order.getProducts().add(op); // add to the list
                }
            }

            order.setTotalAmount(total);

            BigDecimal discount = order.getDiscountAmount() != null ? order.getDiscountAmount() : BigDecimal.ZERO;
            order.setFinalAmount(total.subtract(discount));

            return orderRepository.save(order);
        });
    }


    public boolean deleteOrder(Long id) {
        if (orderRepository.existsById(id)) {
            orderRepository.deleteById(id);
            return true;
        }
        return false;
    }
}