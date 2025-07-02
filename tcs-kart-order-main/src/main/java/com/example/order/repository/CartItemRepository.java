package com.example.order.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.order.entity.CartItem;
import com.example.order.entity.Product;
import com.example.order.entity.User;




public interface CartItemRepository extends JpaRepository<CartItem,Long>{

	Optional<CartItem> findByCartUserAndProduct(Optional<User> user, Optional<Product> product);

	void deleteByCartUserAndProduct(Optional<User> user, Optional<Product> product);
	
	List<CartItem> findByCartId(Long Id);

}
