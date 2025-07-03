package com.example.order.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.example.order.entity.Product;
//import com.tcs.product.entity.Products;

@FeignClient(name = "product-service", url = "http://localhost:8082")
public interface ProductClient {

    @GetMapping("/product/{id}")
    Product getProductById(@PathVariable("id") Long id);
    
    @PostMapping("/admin/addProduct")
    Product addNewProduct(@RequestBody Product product);
    
    @GetMapping("/pincodeAvailable/{id}/{pincode}")
    boolean isPincodeAvailableForProduct(@PathVariable Long id, @PathVariable Integer pincode);
}

