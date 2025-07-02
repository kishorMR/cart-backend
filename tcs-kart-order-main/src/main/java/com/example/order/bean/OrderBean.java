package com.example.order.bean;

import java.time.LocalDate;
import java.util.List;

import org.hibernate.annotations.CreationTimestamp;
import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Component
@Data
@ToString
@NoArgsConstructor
public class OrderBean {

	private String email;
	private String address;
	private String status= "pending";
	
	@CreationTimestamp
	private LocalDate orderedDate;
	private List<OrderItemBean> orderItemsBean;
	public OrderBean(String email, String address, List<OrderItemBean> orderItemsBean) {
		super();
		this.email = email;
		this.address = address;
		this.orderItemsBean = orderItemsBean;
	}
	
	
}
