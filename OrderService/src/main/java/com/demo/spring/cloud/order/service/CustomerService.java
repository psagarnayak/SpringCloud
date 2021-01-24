package com.demo.spring.cloud.order.service;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
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

	@Value("${customerms.baseurl}")
	private String customerMSBaseUrl;

	public RequestStatusDTO chargeCustomer(long customerId, double charge) {
		
		RequestStatusDTOBuilder status = RequestStatusDTO.builder();
		
		Map<String, Object> requestBody = new HashMap<>();
		requestBody.put("decrementBy", charge);
		
		RequestEntity<Map<String, Object>> request = RequestEntity.post(
				URI.create(customerMSBaseUrl + "/" + customerId + "/editBalance" )).body(requestBody);
		
		ResponseEntity<PostResponse> responseEntity = rest.exchange(request, PostResponse.class);
		
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

}
