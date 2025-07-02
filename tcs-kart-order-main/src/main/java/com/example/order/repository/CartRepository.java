package com.example.order.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.order.entity.Cart;
import com.example.order.entity.User;



public interface CartRepository extends JpaRepository<Cart, Long> {
	
	Optional<Cart> findByUser(Optional<User> user);
	
	Cart findByUserEmail(String email);

	void deleteByUser(Optional<User> user);

}
