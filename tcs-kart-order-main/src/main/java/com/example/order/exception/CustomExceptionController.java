package com.example.order.exception;

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
	
	@ExceptionHandler(ProductNotInPincodeException.class)
	public ResponseEntity<String> ProductNotInPincodeException(){
		return new ResponseEntity<String>("Product can't be delivered to the location", HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(OrdersNotFoundException.class)
	public ResponseEntity<String> OrdersNotFoundException(){
		return new ResponseEntity<String>("Orders Not Found", HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(InvalidQuantityException.class)
	public ResponseEntity<String> InvalidQuantityException(){
		return new ResponseEntity<String>("Please Enter Quantity Greater Than 0", HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(CartNotFoundException.class)
	public ResponseEntity<String> CartNotFoundException(){
		return new ResponseEntity<String>("Cart is Empty", HttpStatus.NO_CONTENT);
	}
	
	@ExceptionHandler(NoEnoughQuantityException.class)
	public ResponseEntity<String> NoEnoughQuantityException(){
		return new ResponseEntity<String>("There is no enough stocks", HttpStatus.INSUFFICIENT_STORAGE);
	}
}
