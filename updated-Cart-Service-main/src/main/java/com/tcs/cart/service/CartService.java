package com.tcs.cart.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tcs.cart.entity.Cart;
import com.tcs.cart.entity.CartItem;
import com.tcs.cart.entity.OrderItem;
import com.tcs.cart.entity.Orders;
import com.tcs.cart.entity.Product;
import com.tcs.cart.entity.User;
import com.tcs.cart.entity.Wishlist;
import com.tcs.cart.exception.*;
import com.tcs.cart.respository.CartItemRepository;
import com.tcs.cart.respository.CartRepository;
import com.tcs.cart.respository.OrderItemRepository;
import com.tcs.cart.respository.OrderRepository;
import com.tcs.cart.respository.ProductRepository;
import com.tcs.cart.respository.UserRepository;
import com.tcs.cart.respository.WishlistRepository;

@Service
public class CartService {

	@Autowired
	private CartRepository cartRepository;
	@Autowired
	private CartItemRepository cartItemRepository;
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private ProductRepository productRepository;
	@Autowired
	private OrderRepository orderRepository;
	@Autowired
	private OrderItemRepository orderItemRepository;
	@Autowired 
	private WishlistRepository wishlistRepository;
	
	
	public boolean addWishlist(String email, Long productId) {
		Optional<User> optionalUser = userRepository.findByEmail(email);
		if (optionalUser.isEmpty())
			throw new UserNotFoundException();

		Optional<Product> optionalProduct = productRepository.findById(productId);
		if (optionalProduct.isEmpty())
			throw new ProductNotFoundException();
		
		User user = optionalUser.get();
		Product product = optionalProduct.get();
		Wishlist wishlist = wishlistRepository.findByUserAndProduct(user, product);
		if(wishlist != null) throw new ProductAlreadyExistException();
		Wishlist wish = new Wishlist();
		 wish.setUser(user);
		 wish.setProduct(product);
		 user.getWishlists().add(wish);
		User result = userRepository.save(user);
		if(result != null)
			return true;
		return false;
	}
	

	public boolean removeWishlist(String email, Long productId) {
		Optional<User> optionalUser = userRepository.findByEmail(email);
		if (optionalUser.isEmpty())
			throw new UserNotFoundException();

		Optional<Product> optionalProduct = productRepository.findById(productId);
		if (optionalProduct.isEmpty())
			throw new ProductNotFoundException();
		
		
		User user = optionalUser.get();
		Product product = optionalProduct.get();
		Wishlist wishlist = wishlistRepository.findByUserAndProduct(user, product);
		if(wishlist == null) throw new ProductDoesNotExistException();
		wishlistRepository.delete(wishlist);
		User result = userRepository.save(user);
		if(result != null)
			return true;
		return false;
	}

	public boolean addProductToCart(String email, Long productId) {

		Cart cart = new Cart();
		CartItem cartItem = new CartItem();

		Optional<User> user = userRepository.findByEmail(email);
		if (user.isEmpty())
			throw new UserNotFoundException();

		Optional<Product> product = productRepository.findById(productId);
		if (product.isEmpty())
			throw new ProductNotFoundException();

		Optional<CartItem> optionalCartItem = cartItemRepository.findByCartUserAndProduct(user, product);
		if(optionalCartItem.isPresent()) {
			CartItem getCartItem = optionalCartItem.get();
			Integer quant = getCartItem.getQuantity();
			updateCartItemQuantity(email,productId,quant+1 );
				return true;
		
		}
		Optional<Cart> getCart = cartRepository.findByUser(user);
		if (getCart.isEmpty()) {
			cart.setUser(user.get());
			cartItem.setProduct(product.get());
			cartItem.setCart(cart);
			cart.getCartItems().add(cartItem);
			Cart result = cartRepository.save(cart);

			if (result != null)
				return true;
			return false;
		}
		cartItem.setProduct(product.get());
		cartItem.setCart(getCart.get());
		CartItem result = cartItemRepository.save(cartItem);
		if (result != null)
			return true;
		return false;

	}



	public boolean updateCartItemQuantity(String email, Long productId, Integer quantity) {
		Optional<User> user = userRepository.findByEmail(email);
		if (user.isEmpty())
			throw new UserNotFoundException();

		Optional<Product> product = productRepository.findById(productId);
		if (product.isEmpty())
			throw new ProductNotFoundException();
	
		Optional<CartItem> getCartItem = cartItemRepository.findByCartUserAndProduct(user, product);
		
	    if(getCartItem.isEmpty())throw new CartItemNotFoundException();
	    
		if(getCartItem.get().getQuantity() < 1 || getCartItem.get().getQuantity() > product.get().getQuantity()) throw new InvalidQuantityException();
		
	    getCartItem.get().setQuantity(quantity);
	    
		
	    cartItemRepository.save(getCartItem.get());
		return true;
	}

	public boolean removeProductFromCart(String email, Long productId) {
		Optional<User> user = userRepository.findByEmail(email);
		if (user.isEmpty())
		    throw new UserNotFoundException();

		Optional<Product> product = productRepository.findById(productId);
		if (product.isEmpty())
			throw new ProductNotFoundException();
	
		Optional<CartItem> getCartItem = cartItemRepository.findByCartUserAndProduct(user, product);
		
	    if(getCartItem.isEmpty())throw new CartItemNotFoundException();
	    
	    Long id = getCartItem.get().getCartId();
	    cartItemRepository.deleteById(id);
	    return true;

	}

	public Optional<Cart> getAllCartItemsByCustomerId(String email) {
		Optional<User> user = userRepository.findByEmail(email);
		System.out.println(email);
		if (user.isEmpty())
			throw new UserNotFoundException();

	      Optional<Cart> cartDetail = cartRepository.findByUser(user);
	      if(cartDetail.isEmpty()) throw new CartNotFoundException();
	      return cartDetail;
	}

	public boolean clearCart(String email) {
		
		Optional<User> user = userRepository.findByEmail(email);
		if (user.isEmpty())
			throw new UserNotFoundException();
		
		Optional<Cart> getCart = cartRepository.findByUser(user);
		cartRepository.deleteById(getCart.get().getCartId());
		return true;

	}
	
	public boolean buyProducts(String email) {
		
		Optional<User> user = userRepository.findByEmail(email);
		if (user.isEmpty())
			throw new UserNotFoundException();
		
		Optional<Cart> getCart = cartRepository.findByUser(user);
		if(getCart.isEmpty())
            throw new CartNotFoundException();
		
		double totalAmount = 0;
		List<OrderItem> orderItems = new ArrayList<>();
		Orders order = new Orders();
		order.setUser(user.get());
		
		for(CartItem c : getCart.get().getCartItems()) {
			totalAmount = c.getProduct().getPrice()*c.getQuantity() + totalAmount;
			OrderItem orderItem = new OrderItem();
			orderItem.setProduct(c.getProduct());
			orderItem.setQuantity(c.getQuantity());
			orderItem.setOrders(order);
		
			orderItems.add(orderItem);
		}
		order.setAddress(user.get().getAddress());
		order.setOrderItems(orderItems);
		order.setTotalAmount(totalAmount);
		
		orderRepository.save(order);
		
		cartRepository.delete(getCart.get());
			
		return true;
	}


}