package com.tcs.cart.service;

import static org.mockito.Mockito.when;

import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import com.tcs.cart.entity.OrderItem;
import com.tcs.cart.entity.Orders;
import com.tcs.cart.entity.Product;
import com.tcs.cart.entity.Review;
import com.tcs.cart.entity.User;
import com.tcs.cart.respository.OrderItemRepository;
import com.tcs.cart.respository.OrderRepository;
import com.tcs.cart.respository.ProductRepository;
import com.tcs.cart.respository.ReviewRepository;
import com.tcs.cart.respository.UserRepository;

@ExtendWith(MockitoExtension.class)
public class ReviewServiceTest {
	@Mock
	private UserRepository userRepository;
	@Mock
	private ProductRepository productRepository;
	@Mock
	private OrderRepository orderRepository;
	@Mock
	private OrderItemRepository orderItemRepository;
	@InjectMocks
	private ReviewService reviewService;
	
    private User user;
    private Product product;
    private Orders orders;
    private OrderItem orderItem;
	
	@BeforeEach
    void setUp(){
    MockitoAnnotations.openMocks(this);
    
    user = new User();
    user.setUid(1);
    user.setName("testuser");
    user.setEmail("test@gmail.com");

    product = new Product();
    product.setProductId(1L);
    product.setProductName("Test Product");
    product.setPrice(100.0);
    product.setQuantity(1);

    orders = new Orders();
    orders.setUser(user);
    orders.setOrderId(1L);

    orderItem = new OrderItem();
    orderItem.setProduct(product);
    orderItem.setQuantity(1);
    orderItem.setOrders(orders);
    }
	
	@Test
	void addReviewTest() {
		when(userRepository.findByEmail("test@gmail.com")).thenReturn(Optional.of(user));
   	    when(productRepository.findById(1L)).thenReturn(Optional.of(product));
   	    when(orderItemRepository.findByOrdersUserAndProduct(user,product)).thenReturn(Optional.of(orderItem));
        when(productRepository.save(product)).thenReturn(product);
        
        boolean result = reviewService.addReview("test@gmail.com", 1L,4.1, "product is good");
        assertTrue(result);
        
	}
}
