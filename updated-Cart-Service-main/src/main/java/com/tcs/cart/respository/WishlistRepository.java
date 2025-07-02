package com.tcs.cart.respository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tcs.cart.entity.Product;
import com.tcs.cart.entity.User;
import com.tcs.cart.entity.Wishlist;

public interface WishlistRepository extends JpaRepository<Wishlist,Long>{

	Wishlist findByUserAndProduct(User user, Product product);

}
