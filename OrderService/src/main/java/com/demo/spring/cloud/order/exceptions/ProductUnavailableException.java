package com.demo.spring.cloud.order.exceptions;

public class ProductUnavailableException extends Exception{

	private static final long serialVersionUID = 1L;
	
	public ProductUnavailableException(String message) {
		super(message);
	}
	
	public ProductUnavailableException(String message, Throwable throwable) {
		super(message, throwable);
	}
	
}
