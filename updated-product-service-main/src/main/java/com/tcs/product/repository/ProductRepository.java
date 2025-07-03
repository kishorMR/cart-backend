package com.tcs.product.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tcs.product.entity.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long>{
	List<Product> findByProductNameContainingIgnoreCase(String name);

	List<Product> findByCategoryContainingIgnoreCase(String category);

//	List<Products> findAllById(Integer cid);
}
