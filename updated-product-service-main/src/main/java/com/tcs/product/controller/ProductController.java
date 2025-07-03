package com.tcs.product.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tcs.product.entity.Product;
import com.tcs.product.entity.ProductImage;
import com.tcs.product.exception.ImageFormatException;
import com.tcs.product.security.JwtUtil;
import com.tcs.product.service.ProductService;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
@RequestMapping("/product")
public class ProductController {

	@Autowired
	ProductService productService;
	
	@Autowired
	JwtUtil jwtUtil;
	
	@GetMapping("/products") 
	public Page<Product> getAllProducts(@RequestHeader("Authorization") String authHeader,@RequestParam Integer page, @RequestParam Integer size){
		return productService.getAllProducts(page,size);
	}
	
	@GetMapping("/products/{name}") 
	public List<Product> getAllProductByName(@PathVariable String name){
		return productService.getAllProductsByName(name);
	}
	
	@GetMapping("/products/category/{category}") 
	public List<Product> getProductByCategories(@PathVariable String category) {
		return productService.getProductByCategories(category);
	}
	
	@GetMapping("/products/id/{id}")
	public Optional<Product> getProductById(@PathVariable Long id) {
		return productService.getProductById(id);
	}
	
	@GetMapping("/products/productImage/{id}")
	public List<ProductImage> getProductImageById(@PathVariable Long id){
		return productService.getProductImageById(id);
	}
	
	@PostMapping("/admin/addProduct")
	public Product addNewProducts(@RequestBody Product product) {
		return productService.addNewProduct(product);
	}
	
	@DeleteMapping("/admin/products/{id}")
	public String deleteProduct(@PathVariable Long id) {
		return productService.deleteProduct(id);
	}
	
	@PutMapping("/admin/products/{id}")
	public String updateProduct(@PathVariable Long id, @RequestBody Product product, @RequestParam String imageUrl, @RequestParam Integer imgId) throws ImageFormatException {
		return productService.updateProduct(id, product, imageUrl, imgId);
	}
	
	@PostMapping("/admin/products/{id}/image")
	public void uploadProductImages(@PathVariable Long id, @RequestParam String url) throws ImageFormatException {
		productService.uploadProductImages(id,url);
	}
	
	@GetMapping("/myproduct")
    public ResponseEntity<?> getMyCart(@RequestHeader("Authorization") String authHeader) {

        String token = authHeader.substring(7); 
        String email = jwtUtil.extractEmail(token);

        return ResponseEntity.ok("Fetching cart for: " + email);
    }
	
	
}
