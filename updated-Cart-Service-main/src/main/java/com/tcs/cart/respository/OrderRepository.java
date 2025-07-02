package com.tcs.cart.respository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tcs.cart.entity.Orders;


public interface OrderRepository extends JpaRepository<Orders,Long>{

}
