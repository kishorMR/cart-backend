package com.example.order.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.order.entity.Orders;

//import com.tcs.trainingAssignment.entity.Orders;


public interface OrderRepository extends JpaRepository<Orders,Long>{
	List<Orders> findByUserEmail(String email);

	List<Orders> findByCreatedDateBetween(Date startDate, Date endDate);
}
