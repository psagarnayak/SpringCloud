package com.demo.spring.cloud.order.exceptions;

public class InvalidOrderException extends Exception {

	private static final long serialVersionUID = 1L;
	
	public InvalidOrderException(String message) {
		super(message);
	}
	
	public InvalidOrderException(String message, Throwable throwable) {
		super(message, throwable);
	}
	
}
