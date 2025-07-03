package com.tcs.cart.service;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.hamcrest.CoreMatchers.any;
import static org.junit.jupiter.api.Assertions.*;
import com.tcs.cart.entity.Cart;
import com.tcs.cart.entity.CartItem;
import com.tcs.cart.entity.Product;
import com.tcs.cart.entity.User;
import com.tcs.cart.entity.Wishlist;
import com.tcs.cart.exception.ProductAlreadyExistException;
import com.tcs.cart.exception.ProductDoesNotExistException;
import com.tcs.cart.respository.CartItemRepository;
import com.tcs.cart.respository.CartRepository;
import com.tcs.cart.respository.OrderItemRepository;
import com.tcs.cart.respository.OrderRepository;
import com.tcs.cart.respository.ProductRepository;
import com.tcs.cart.respository.UserRepository;
import com.tcs.cart.respository.WishlistRepository;

@ExtendWith(MockitoExtension.class)
public class CartServiceTest {
	
	@InjectMocks
	private CartService cartService;
	
	@Mock
	private CartRepository cartRepository;
	@Mock
	private CartItemRepository cartItemRepository;
	@Mock
	private UserRepository userRepository;
	@Mock
	private ProductRepository productRepository;
	@Mock
	private OrderRepository orderRepository;
	@Mock
	private OrderItemRepository orderItemRepository;
	@Mock
	private WishlistRepository wishlistRepository;
	
	private User user;
    private Product product;
    private Cart cart;
    private CartItem cartItem;
    
    @BeforeEach
    void setUp(){
    MockitoAnnotations.openMocks(this);
    
    user = new User();
    user.setUid(1);
    user.setName("testuser");
    user.setEmail("test@gmail.com");

    product = new Product();
    product.setProductId(1L);
    product.setProductName("Test Product");
    product.setPrice(100.0);
    product.setQuantity(1);

    cart = new Cart();
    cart.setUser(user);
    cart.setCartId(1L);
    

    cartItem = new CartItem();
    cartItem.setProduct(product);
    cartItem.setQuantity(1);
    cartItem.setCart(cart);
   
    }

    @Test 
    void addProductToWishlist() {
    	 when(userRepository.findByEmail("test@gmail.com")).thenReturn(Optional.of(user));
    	 when(productRepository.findById(1L)).thenReturn(Optional.of(product));
    	 when(wishlistRepository.findByUserAndProduct(user, product)).thenReturn(null);
    	 when(userRepository.save(user)).thenReturn(user);
    	  
    	 boolean result = cartService.addWishlist("test@gmail.com", 1L);
    	 
    	 assertTrue(result);
    }
    
    @Test 
    void removeProductToWishlist() {
    	 Wishlist wishlist = new Wishlist();
    	 wishlist.setUser(user);
    	 wishlist.setProduct(product);
    	 wishlist.setWishlistId(1L);
    	 when(userRepository.findByEmail("test@gmail.com")).thenReturn(Optional.of(user));
    	 when(productRepository.findById(1L)).thenReturn(Optional.of(product));
    	 when(wishlistRepository.findByUserAndProduct(user, product)).thenReturn(wishlist);
    	 when(userRepository.save(user)).thenReturn(user);
    	 boolean result = cartService.removeWishlist("test@gmail.com", 1L);
    	 
    	 assertTrue(result);
    	 verify(wishlistRepository, times(1)).delete(wishlist);
    }
    
    @Test
    void addWishlist_productExist() {
    	 Wishlist wishlist = new Wishlist();
    	 wishlist.setUser(user);
    	 wishlist.setProduct(product);
    	 wishlist.setWishlistId(1L);
    	 when(userRepository.findByEmail("test@gmail.com")).thenReturn(Optional.of(user));
        when(productRepository.findById(1L)).thenReturn(Optional.of(product));
        when(wishlistRepository.findByUserAndProduct(user, product)).thenReturn(wishlist);

        assertThrows(ProductAlreadyExistException.class, () -> cartService.addWishlist("test@gmail.com", 1L));
    }
    @Test
    void removeWishlist_productDoesNotExist() {
    	 when(userRepository.findByEmail("test@gmail.com")).thenReturn(Optional.of(user));
        when(productRepository.findById(1L)).thenReturn(Optional.of(product));
        when(wishlistRepository.findByUserAndProduct(user, product)).thenReturn(null);

        assertThrows(ProductDoesNotExistException.class, () -> cartService.removeWishlist("test@gmail.com", 1L));
    }
	@Test
	void addProductToCartTest() {
		cartItem = new CartItem();
	    cartItem.setProduct(product);
	    cartItem.setQuantity(1);
	    cartItem.setCart(cart);
	    when(userRepository.findByEmail("test@gmail.com")).thenReturn(Optional.of(user));
   	    when(productRepository.findById(1L)).thenReturn(Optional.of(product));
   	    when(cartItemRepository.findByCartUserAndProduct(Optional.of(user), 
   	    		Optional.of(product))).thenReturn(Optional.empty());
   	    when(cartRepository.findByUser(Optional.of(user))).thenReturn(Optional.of(cart));
   	    when(cartItemRepository.save(cartItem)).thenReturn(cartItem);
   	    boolean result = cartService.addProductToCart("test@gmail.com", 1L);
   	    assertTrue(result);
	}
	
	@Test
	void  removeProductFromCartTest() {
		cartItem = new CartItem();
	    cartItem.setProduct(product);
	    cartItem.setQuantity(1);
	    cartItem.setCart(cart);
	    cartItem.setCartId(1L);
	    when(userRepository.findByEmail("test@gmail.com")).thenReturn(Optional.of(user));
   	    when(productRepository.findById(1L)).thenReturn(Optional.of(product));
   	    when(cartItemRepository.findByCartUserAndProduct(Optional.of(user), 
   	    		Optional.of(product))).thenReturn(Optional.of(cartItem));
   	    
   	    boolean result = cartService.removeProductFromCart("test@gmail.com", 1L);
  
   	    assertTrue(result);
   	    verify(cartItemRepository, times(1)).deleteById(1L);
	}
	
	@Test
	void  updateCartQuantityTest() {
		
		 when(userRepository.findByEmail("test@gmail.com")).thenReturn(Optional.of(user));
   	    when(productRepository.findById(1L)).thenReturn(Optional.of(product));
   	    when(cartItemRepository.findByCartUserAndProduct(Optional.of(user), 
   	    		Optional.of(product))).thenReturn(Optional.of(cartItem));
   	    when(cartItemRepository.save(cartItem)).thenReturn(cartItem);
   	    
   	    boolean result = cartService.updateCartItemQuantity("test@gmail.com", 1L,10);
  
   	    assertTrue(result);
   	    
	}
	
	@Test
	void  getAllCartItemsTest() {
		
		 when(userRepository.findByEmail("test@gmail.com")).thenReturn(Optional.of(user));
   	    
   	    when(cartRepository.findByUser(Optional.of(user))).thenReturn(Optional.of(cart));
   	  
   	    Optional<Cart> result = cartService.getAllCartItemsByCustomerId("test@gmail.com");
  
   	    assertTrue(result.isPresent());
   	    assertEquals(cart, result.get());
	}
	
	@Test
	void  clearCartTest() {
		
		 when(userRepository.findByEmail("test@gmail.com")).thenReturn(Optional.of(user));
   	    
   	    when(cartRepository.findByUser(Optional.of(user))).thenReturn(Optional.of(cart));
   	  
   	    boolean result = cartService.clearCart("test@gmail.com");
  
   	    assertTrue(result);
	    verify(cartRepository, times(1)).deleteById(1L);
	}
}
