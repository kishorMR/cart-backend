package com.tcs.cart.respository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tcs.cart.entity.Cart;
import com.tcs.cart.entity.User;

public interface CartRepository extends JpaRepository<Cart, Long> {
	Optional<Cart> findByUser(Optional<User> user);

	void deleteByUser(Optional<User> user);

}
