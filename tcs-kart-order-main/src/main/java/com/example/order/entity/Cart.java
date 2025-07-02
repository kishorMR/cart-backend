package com.example.order.entity;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;


@Entity
public class Cart {
	
	 	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private Long cartId;

	    @OneToOne
	    private User user;
	    
	    @OneToMany(mappedBy = "cart", cascade = CascadeType.ALL)
	    private List<CartItem> cartItems = new ArrayList<>();

		public Long getCartId() {
			return cartId;
		}

		public void setCartId(Long cartId) {
			this.cartId = cartId;
		}

		public User getUser() {
			return user;
		}

		public void setUser(User user) {
			this.user = user;
		}

		public List<CartItem> getCartItems() {
			return cartItems;
		}

		public void setCartItems(List<CartItem> cartItems) {
			this.cartItems = cartItems;
		}

		public Cart(Long cartId, User user, List<CartItem> cartItems) {
			super();
			this.cartId = cartId;
			this.user = user;
			this.cartItems = cartItems;
		}

		public Cart() {
			super();
			// TODO Auto-generated constructor stub
		}

	    
}
