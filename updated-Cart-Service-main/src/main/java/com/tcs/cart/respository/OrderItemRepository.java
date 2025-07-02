package com.tcs.cart.respository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tcs.cart.entity.CartItem;
import com.tcs.cart.entity.OrderItem;
import com.tcs.cart.entity.Product;
import com.tcs.cart.entity.User;

public interface OrderItemRepository extends JpaRepository<OrderItem,Long>{
	
	Optional<OrderItem> findByOrdersUserAndProduct( User user,  Product product);
}
