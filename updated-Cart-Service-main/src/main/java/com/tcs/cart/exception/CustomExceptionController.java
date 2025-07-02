package com.tcs.cart.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class CustomExceptionController {
	
	@ExceptionHandler(CartItemNotFoundException.class)
	public ResponseEntity<String> CartItemNotFoundException(){
		return new ResponseEntity<String>("The Cart is empty", HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(UserNotFoundException.class)
	public ResponseEntity<String> UserNotFoundException(){
		return new ResponseEntity<String>("User Not Found", HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(ProductNotFoundException.class)
	public ResponseEntity<String> ProductNotFoundException(){
		return new ResponseEntity<String>("Product Not Found", HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(InvalidQuantityException.class)
	public ResponseEntity<String> InvalidQuantityException(){
		return new ResponseEntity<String>("Please Enter Valid Quantity", HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(CartNotFoundException.class)
	public ResponseEntity<String> CartNotFoundException(){
		return new ResponseEntity<String>("Cart is Empty", HttpStatus.NO_CONTENT);
	}
	
	@ExceptionHandler(ProductAlreadyExistException.class)
	public ResponseEntity<String> ProductAlreadyExistException(){
		return new ResponseEntity<String>("Product Already Exist in Wishlist", HttpStatus.FOUND);
	}
	
	@ExceptionHandler(ProductDoesNotExistException.class)
	public ResponseEntity<String> ProductDoesNotExistException(){
		return new ResponseEntity<String>("Product Does Not Exist in Wishlist", HttpStatus.NOT_FOUND);
	}
}
