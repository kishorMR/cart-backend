package com.tcs.user.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductImage {
	
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long productImageId;

    private String url;

    @ManyToOne
	@JoinColumn(name="product_id") //F.K connects with Product table
	private Product product;
}
