package com.tcs.user.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.tcs.user.entity.User;

@Repository
public interface UserRepository extends CrudRepository<User,Long>{

	User findByEmail(String email); 


}
