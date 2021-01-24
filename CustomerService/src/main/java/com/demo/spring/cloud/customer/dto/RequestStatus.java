package com.demo.spring.cloud.customer.dto;

import com.demo.spring.cloud.customer.entity.Customer;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;

@Data
@Builder
@ToString
public class RequestStatus {
	
	private boolean success;
	private String errorMsg;
	private Customer customer;
}
