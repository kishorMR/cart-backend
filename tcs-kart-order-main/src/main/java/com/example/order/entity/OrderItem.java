package com.example.order.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
public class OrderItem {
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderItemId;

    @ManyToOne
    private Product product;

    private Integer quantity;
    
    private Double price;

    @ManyToOne
    private Orders orders;

	public Long getOrderItemId() {
		return orderItemId;
	}

	public void setOrderItemId(Long orderItemId) {
		this.orderItemId = orderItemId;
	}
	

	public Double getPrice() {
		return price;
	}

	public OrderItem(Long orderItemId, Product product, Integer quantity, Double price, Orders orders) {
		super();
		this.orderItemId = orderItemId;
		this.product = product;
		this.quantity = quantity;
		this.price = price;
		this.orders = orders;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public Orders getOrders() {
		return orders;
	}

	public void setOrders(Orders orders) {
		this.orders = orders;
	}

	public OrderItem(Long orderItemId, Product product, Integer quantity, Orders orders) {
		super();
		this.orderItemId = orderItemId;
		this.product = product;
		this.quantity = quantity;
		this.orders = orders;
	}

	public OrderItem() {
		super();
		// TODO Auto-generated constructor stub
	}

}
