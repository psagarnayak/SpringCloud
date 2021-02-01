package com.demo.spring.cloud.order.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.demo.spring.cloud.order.dto.BalanceEditRequest;
import com.demo.spring.cloud.order.restdto.PostResponse;

@FeignClient("${customerms.name}")
public interface CustomerServiceFeignClient {

	
	@PostMapping(value="/customers/{customerId}/editBalance") 
	public ResponseEntity<PostResponse> editBalance(@PathVariable("customerId") long customerId,
			@RequestBody BalanceEditRequest request);

}
