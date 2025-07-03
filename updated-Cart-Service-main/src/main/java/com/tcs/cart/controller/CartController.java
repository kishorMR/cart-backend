package com.tcs.cart.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.tcs.cart.entity.Cart;
import com.tcs.cart.security.JwtUtil;
import com.tcs.cart.service.CartService;
import com.tcs.cart.service.ReviewService;

@RestController
@RequestMapping("/cart")
public class CartController {

	@Autowired
	private CartService cartService;
	
	@Autowired
	private ReviewService reviewService;
	
	@Autowired
    private JwtUtil jwtUtil;
	
	@PostMapping("products/reviews/{productId}")
	public ResponseEntity<String> addReview(@RequestHeader("Authorization") String authHeader, @PathVariable Long productId, @RequestParam Double rating,@RequestParam String review) {
		String token = authHeader.substring(7);
        String email = jwtUtil.extractEmail(token);
		boolean result = reviewService.addReview(email, productId,rating, review);
		if(result)
			return ResponseEntity.ok().body("Review added Succesfully");
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Review cannot be added");
	}
	
	
	@PutMapping("/wishlists/{productId}")
	public ResponseEntity<String> addWishlist(@RequestHeader("Authorization") String authHeader, @PathVariable Long productId) {
		String token = authHeader.substring(7);
        String email = jwtUtil.extractEmail(token);
		boolean result = cartService.addWishlist(email, productId);
		if(result)
			return ResponseEntity.ok().body("wishlist added Succesfully");
	    return ResponseEntity.status(HttpStatus.NOT_FOUND).body("wishlist cannot be added");
		
	}
	@DeleteMapping("/wishlists/{productId}")
	public ResponseEntity<String> removeWishlist(@RequestHeader("Authorization") String authHeader, @PathVariable Long productId) {
		String token = authHeader.substring(7);
        String email = jwtUtil.extractEmail(token);
		boolean result = cartService.removeWishlist(email, productId);
		if(result)
			return ResponseEntity.ok().body("wishlist deleted Succesfully");
	    return ResponseEntity.status(HttpStatus.NOT_FOUND).body("wishlist cannot be deleted");
		
	}
	@PutMapping("/carts/{productId}") //update the cart increment the quantity by 1
	public ResponseEntity<String> updatecartItemQuantity(@RequestHeader("Authorization") String authHeader, @PathVariable Long productId, @RequestParam Integer quantity ) {
		String token = authHeader.substring(7);
        String email = jwtUtil.extractEmail(token);
		boolean result = cartService.updateCartItemQuantity(email,productId,quantity);
		if(result)
			return ResponseEntity.ok().body("updated Succesfully");
	    return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Updation failed!!");
		
	}
	
		
	@DeleteMapping("/carts/{productId}")//delete the cart item by customer id and product id
	public ResponseEntity<String> deletecartItem(@RequestHeader("Authorization") String authHeader, @PathVariable Long productId ) {
		String token = authHeader.substring(7);
        String email = jwtUtil.extractEmail(token);
		boolean result = cartService.removeProductFromCart(email, productId);
		if(result)
			return ResponseEntity.ok().body("deleted Succesfully");
	    return ResponseEntity.status(HttpStatus.NOT_FOUND).body("deletion failed!!");
		
	}
	@GetMapping("/carts")// get all the items by customerId from cart
	public ResponseEntity<Optional<Cart>> getAllcartItemsByCustomerId(@RequestHeader("Authorization") String authHeader) {
		String token = authHeader.substring(7);
        String email = jwtUtil.extractEmail(token);
		Optional<Cart> result = cartService.getAllCartItemsByCustomerId(email);
		if(!result.isEmpty()) return ResponseEntity.ok().body(result);
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);

	}
	@DeleteMapping("/carts")// delete all the items by customer id from cart
	public ResponseEntity<String> clearCart(@RequestHeader("Authorization") String authHeader) {
		String token = authHeader.substring(7);
        String email = jwtUtil.extractEmail(token);
		boolean result = cartService.clearCart(email);
		if(result)
			return ResponseEntity.ok().body("deleted Succesfully");
	    return ResponseEntity.status(HttpStatus.NOT_FOUND).body("deletion failed!!");
	}
	
	@PostMapping("/carts/{productId}")
	ResponseEntity<String> addProductToCart(@RequestHeader("Authorization") String authHeader, @PathVariable Long productId) { 
		String token = authHeader.substring(7);
        String email = jwtUtil.extractEmail(token);
		boolean result = cartService.addProductToCart(email, productId);
		if(result)
			return ResponseEntity.ok().body("Added Sccessfully");
	    return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product cannot be added!!");
		
	}
	
	@PostMapping("/carts/orders")
	ResponseEntity<String> placeOrder(@RequestHeader("Authorization") String authHeader) {
		String token = authHeader.substring(7);
        String email = jwtUtil.extractEmail(token);
		boolean result = cartService.buyProducts(email);
		if(result)
			return ResponseEntity.ok().body("Order Placed Sccessfully");
		 return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Order  cannot be placed!!");
	}
	
	@GetMapping("/mycart")
    public ResponseEntity<?> getMyCart(@RequestHeader("Authorization") String authHeader) {

		String token = authHeader.substring(7);
        String email = jwtUtil.extractEmail(token);
        
		Optional<Cart> result = cartService.getAllCartItemsByCustomerId(email);
		
		if(!result.isEmpty()) return ResponseEntity.ok().body(result);
		
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
    }
	@GetMapping("/my")
    public ResponseEntity<String> getMyNewCart() {

        // Now use email as needed
        return ResponseEntity.ok("Fetching cart for: ");
    }
	}
