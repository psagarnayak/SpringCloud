package com.demo.spring.cloud.order.dto;

import com.demo.spring.cloud.order.entity.Order;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;

@Data
@Builder
@ToString
public class RequestStatusDTO {
	
	private boolean success;
	private String errorMsg;
	private Order order;
}
