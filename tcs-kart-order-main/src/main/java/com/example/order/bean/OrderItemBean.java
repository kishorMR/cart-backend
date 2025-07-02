package com.example.order.bean;

import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Component
@Data
@ToString
@NoArgsConstructor
public class OrderItemBean {

	private Long productId;
	private int quantity;
	private Double price;
	public OrderItemBean(Long productId, int quantity) {
		super();
		this.productId = productId;
		this.quantity = quantity;
//		this.price = price;
	}
	
	
}
