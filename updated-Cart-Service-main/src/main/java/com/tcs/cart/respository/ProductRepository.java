package com.tcs.cart.respository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tcs.cart.entity.Product;

public interface ProductRepository extends JpaRepository<Product,Long> {

}
