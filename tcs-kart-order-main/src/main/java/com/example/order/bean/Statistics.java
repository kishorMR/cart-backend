package com.example.order.bean;

import java.util.ArrayList;
import java.util.List;

import com.example.order.entity.Product;

import lombok.Data;
@Data
public class Statistics {
	private Double totalRevenue;
	private Integer noOfOrders;
	private List<Product> products = new ArrayList<>();

}
