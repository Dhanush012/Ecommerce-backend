package com.ridhi.product.Service;

import com.ridhi.product.Model.Product;
import com.ridhi.product.Repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    public List<Product> getallproducts() {
        return productRepository.findAll();
    }

    public Product addproduct(Product product) {
        product.setCreatedAt(LocalDateTime.now());
        product.setUpdatedAt(LocalDateTime.now());

        // Optional: calculate final price based on discount
        // Example: 10% discount = 0.1 * price
        if (product.getDiscount() != null && product.getPrice() != null) {

            double discountAmount = product.getPrice().doubleValue() * (product.getDiscount().doubleValue() / 100);
            product.setFinalPrice(product.getPrice().subtract(java.math.BigDecimal.valueOf(discountAmount)));
        }

        return productRepository.save(product);
    }

    public void deleteProductById(Long id) {
        if (!productRepository.existsById(id)) {
            throw new RuntimeException("Product with ID " + id + " not found.");
        }
        productRepository.deleteById(id);
    }

    public Product getproductbyid(Long id) {
        Optional<Product> dummyproduct = productRepository.findById(id);
        if(dummyproduct.isPresent()){
            Product s = dummyproduct.get();
            return s;
        }
        throw new RuntimeException("Product Id not found!!");
    }
}
