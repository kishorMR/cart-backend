package com.tcs.cart.respository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tcs.cart.entity.CartItem;
import com.tcs.cart.entity.Product;
import com.tcs.cart.entity.User;

public interface CartItemRepository extends JpaRepository<CartItem,Long>{

	Optional<CartItem> findByCartUserAndProduct(Optional<User> user, Optional<Product> product);

	void deleteByCartUserAndProduct(Optional<User> user, Optional<Product> product);

}
