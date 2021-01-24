package com.demo.spring.cloud.order.exceptions;

public class OrderPlacementException  extends Exception{

	private static final long serialVersionUID = 1L;
	
	public OrderPlacementException(String message) {
		super(message);
	}
	
	public OrderPlacementException(String message, Throwable throwable) {
		super(message, throwable);
	}
	
}

