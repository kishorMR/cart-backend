package com.example.order.entity;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.*;
import lombok.Data;

@Entity
public class Product {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer productId;
	
	@Column(nullable = false)
	private String productName;
	
	@Column(nullable = false)
	private Integer quantity;
	
	@Column(nullable = false)
	private String description;
	
	@Column(nullable = false)
	private String category;
	
	@Column(nullable = false)
	private Double price;
	
	@Column(nullable = false)
	private Double rating;
	
	@Column(nullable=false)
	private String availabilityStatus;	
	
	@OneToMany(mappedBy="product")
	private List<ProductImage> productImageList = new ArrayList<>();
	
	public Integer getProductId() {
		return productId;
	}
	public void setProductId(Integer productId) {
		this.productId = productId;
	}
	public Integer getQuantity() {
		return quantity;
	}
	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public Double getPrice() {
		return price;
	}
	public void setPrice(Double price) {
		this.price = price;
	}
	public Double getRating() {
		return rating;
	}
	public void setRating(Double rating) {
		this.rating = rating;
	}
	
	public String getAvailabilityStatus() {
		return availabilityStatus;
	}
	public void setAvailabilityStatus(String availabilityStatus) {
		this.availabilityStatus = availabilityStatus;
	}
	
	public List<ProductImage> getProductImageList() {
		return productImageList;
	}
	public void setProductImageList(List<ProductImage> productImageList) {
		this.productImageList = productImageList;
	}
	public Product() {
	
	}
	public Product(Integer productId, String productName, Integer quantity, String description, String category,
			Double price, Double rating, String availabilityStatus, List<ProductImage> productImageList) {
		super();
		this.productId = productId;
		this.productName = productName;
		this.quantity = quantity;
		this.description = description;
		this.category = category;
		this.price = price;
		this.rating = rating;
		this.availabilityStatus = availabilityStatus;
		this.productImageList = productImageList;
	}
	
	@ElementCollection
	private List<Integer> avaialablePincodes;

	public List<Integer> getAvaialablePincodes() {
		return avaialablePincodes;
	}
	public void setAvaialablePincodes(List<Integer> avaialablePincodes) {
		this.avaialablePincodes = avaialablePincodes;
	}
	public Product(Integer productId, String productName, Integer quantity, String description, String category,
			Double price, Double rating, String availabilityStatus, List<ProductImage> productImageList,
			List<Integer> avaialablePincodes) {
		super();
		this.productId = productId;
		this.productName = productName;
		this.quantity = quantity;
		this.description = description;
		this.category = category;
		this.price = price;
		this.rating = rating;
		this.availabilityStatus = availabilityStatus;
		this.productImageList = productImageList;
		this.avaialablePincodes = avaialablePincodes;
	}
	
		
}
