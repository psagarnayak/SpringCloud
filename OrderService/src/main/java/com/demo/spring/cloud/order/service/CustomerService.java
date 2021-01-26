package com.demo.spring.cloud.order.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;

import com.demo.spring.cloud.order.dto.RequestStatusDTO;
import com.demo.spring.cloud.order.dto.RequestStatusDTO.RequestStatusDTOBuilder;
import com.demo.spring.cloud.order.restdto.PostResponse;

@Service
public class CustomerService {

	@Autowired
	RestTemplate rest;

	@Value("${customerms.name}")
	private String customerMSName;
	
	public RequestStatusDTO chargeCustomer(long customerId, double amountToCharge) {
		
		RequestStatusDTOBuilder status = RequestStatusDTO.builder();
		
		Map<String, Object> requestBody = new HashMap<>();
		requestBody.put("decrementBy", amountToCharge);
		
		HttpEntity<Map<String, Object>> requestEntity =  new HttpEntity<>(requestBody);
		ResponseEntity<PostResponse> responseEntity = rest.exchange(
				String.format("http://%s/customers/%s/editBalance", customerMSName, customerId),
				HttpMethod.POST, requestEntity, PostResponse.class);
		
		PostResponse response = responseEntity.getBody();
		
		if(responseEntity.getStatusCode() != HttpStatus.OK ||
				!response.isSuccess()) {
			status.success(false);
			status.errorMsg(
					StringUtils.hasLength(response.getMessage()) ? response.getMessage() : "Charges Rejected!" );
			
		}
		else {
			status.success(true);
		}
		
		return status.build();
	}

	public RequestStatusDTO addBalance(long customerId, double amountToAdd) {
		
		RequestStatusDTOBuilder status = RequestStatusDTO.builder();
		
		Map<String, Object> requestBody = new HashMap<>();
		requestBody.put("incrementBy", amountToAdd);
		
		HttpEntity<Map<String, Object>> requestEntity =  new HttpEntity<>(requestBody);
		ResponseEntity<PostResponse> responseEntity = rest.exchange(
				String.format("http://%s/customers/%s/editBalance", customerMSName, customerId),
				HttpMethod.POST, requestEntity, PostResponse.class);
		
		PostResponse response = responseEntity.getBody();
		
		if(responseEntity.getStatusCode() != HttpStatus.OK ||
				!response.isSuccess()) {
			status.success(false);
			status.errorMsg(
					StringUtils.hasLength(response.getMessage()) ? response.getMessage() : "Unable to add balance!" );
			
		}
		else {
			status.success(true);
		}
		
		return status.build();
	}

}
