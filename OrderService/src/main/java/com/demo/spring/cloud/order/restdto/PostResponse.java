package com.demo.spring.cloud.order.restdto;

import com.demo.spring.cloud.order.entity.Order;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PostResponse {
	private boolean success;
	private String message;
	private Order order;
}