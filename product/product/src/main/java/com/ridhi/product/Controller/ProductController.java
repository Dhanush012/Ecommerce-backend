package com.ridhi.product.Controller;

import com.ridhi.product.Model.Product;
import com.ridhi.product.Service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/product")
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping("/getallproduct")
    public ResponseEntity<List<Product>> getallproducts(){
        return new ResponseEntity<>(productService.getallproducts(), HttpStatus.OK);
    }

    @PostMapping("/addproduct")
    public ResponseEntity<Product> addproduct(@RequestBody Product product){
        return new ResponseEntity<>(productService.addproduct(product),HttpStatus.OK);
    }
    @DeleteMapping("/deletebyid")
    public void deleteProduct(@RequestParam Long id) {
        productService.deleteProductById(id);
    }
}
