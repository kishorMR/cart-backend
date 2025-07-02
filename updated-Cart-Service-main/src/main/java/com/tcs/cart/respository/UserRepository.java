package com.tcs.cart.respository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tcs.cart.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {

}
