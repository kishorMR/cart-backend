package com.tcs.cart.respository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tcs.cart.entity.Review;

public interface ReviewRepository extends JpaRepository<Review,Long>{

}
