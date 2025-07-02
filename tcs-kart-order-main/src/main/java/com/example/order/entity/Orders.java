package com.example.order.entity;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.annotations.CreationTimestamp;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
public class Orders {
	
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderId;

    @ManyToOne
    private User user;

    @JsonIgnore
    @OneToMany(mappedBy = "orders", cascade = CascadeType.ALL)
    private List<OrderItem> orderItems = new ArrayList<>();

    private String status = "pending";

    private Double totalAmount;
    
    private String address;

    @CreationTimestamp
    private LocalDate createdDate;

	public Orders(Long orderId, User user, List<OrderItem> orderItems, String status, Double totalAmount,
			String address, LocalDate createdDate) {
		super();
		this.orderId = orderId;
		this.user = user;
		this.orderItems = orderItems;
		this.status = status;
		this.totalAmount = totalAmount;
		this.address = address;
		this.createdDate = createdDate;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Long getOrderId() {
		return orderId;
	}

	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public List<OrderItem> getOrderItems() {
		return orderItems;
	}

	public void setOrderItems(List<OrderItem> orderItems) {
		this.orderItems = orderItems;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Double getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(Double totalAmount) {
		this.totalAmount = totalAmount;
	}

	public LocalDate getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(LocalDate currentDate) {
		this.createdDate = currentDate;
	}

	public Orders(Long orderId, User user, List<OrderItem> orderItems, String status, Double totalAmount,
			LocalDate createdDate) {
		super();
		this.orderId = orderId;
		this.user = user;
		this.orderItems = orderItems;
		this.status = status;
		this.totalAmount = totalAmount;
		this.createdDate = createdDate;
	}

	public Orders() {
		super();
		// TODO Auto-generated constructor stub
	}
    
    

}
