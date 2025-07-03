package com.tcs.cart.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tcs.cart.entity.OrderItem;
import com.tcs.cart.entity.Product;
import com.tcs.cart.entity.Review;
import com.tcs.cart.entity.User;
import com.tcs.cart.exception.ProductNotFoundException;
import com.tcs.cart.exception.UserNotFoundException;
import com.tcs.cart.respository.OrderItemRepository;
import com.tcs.cart.respository.OrderRepository;
import com.tcs.cart.respository.ProductRepository;
import com.tcs.cart.respository.UserRepository;

@Service
public class ReviewService {
	
	@Autowired
	private UserRepository userRepository;

	@Autowired
	private ProductRepository productRepository;
	
	@Autowired
	private OrderRepository orderRepository;
	
	@Autowired
	private OrderItemRepository orderItemRepository;
	
	
	public boolean addReview(String email, Long productId,Double rating, String userReview) {
		Optional<User> optionalUser = userRepository.findByEmail(email);
		if (optionalUser.isEmpty())
			throw new UserNotFoundException();

		Optional<Product> optionalProduct = productRepository.findById(productId);
		if (optionalProduct.isEmpty())
			throw new ProductNotFoundException();
		
		User user = optionalUser.get();
		Product product = optionalProduct.get();
		
		Optional<OrderItem> orderItem = orderItemRepository.findByOrdersUserAndProduct(user,product);
		if(orderItem.isEmpty()) return false;
		
		Review review = new Review();
		review.setUser(user);
		review.setProduct(product);
		review.setRating(rating);
		review.setReviewText(userReview);
		
		product.getReviews().add(review);
		
		Product result = productRepository.save(product);
		if(result != null)
		return true;
		return false;
	}

}
