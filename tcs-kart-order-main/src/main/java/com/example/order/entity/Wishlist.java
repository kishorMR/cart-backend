package com.example.order.entity;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name= "user_wishlist")
public class Wishlist {
 @Id
 @GeneratedValue(strategy = GenerationType.IDENTITY)
 private Long wishlistId;
 
 @JsonIgnore
 @ManyToOne
 @JoinColumn(name = "user_uid")
 private User user;
 
 @ManyToOne
 @JoinColumn(name = "product_product_id")
 private Product product;
}
