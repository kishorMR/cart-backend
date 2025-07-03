package com.example.order.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.order.entity.Product;

public interface ProductRepository extends JpaRepository<Product,Long> {

	@Query("SELECT COUNT(p) > 0 FROM Product prod JOIN prod.avaialablePincodes p WHERE prod.productId = :productId AND p = :pincode")
	Boolean isPincodeAvailableForProduct(@Param("productId") Long productId,
	                                     @Param("pincode") Integer pincode);
}
