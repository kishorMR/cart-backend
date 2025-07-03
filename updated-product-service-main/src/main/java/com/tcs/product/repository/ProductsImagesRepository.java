package com.tcs.product.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tcs.product.entity.ProductImage;

public interface ProductsImagesRepository extends JpaRepository<ProductImage, Integer>{

	List<ProductImage> findByProductProductId(Long productId);

}
