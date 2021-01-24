package com.demo.spring.cloud.order.service;

import java.net.URI;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import com.demo.spring.cloud.order.dto.ProductDTO;
import com.demo.spring.cloud.order.dto.RequestStatusDTO;
import com.demo.spring.cloud.order.dto.RequestStatusDTO.RequestStatusDTOBuilder;
import com.demo.spring.cloud.order.restdto.PostResponse;

@Service
public class ProductService {

	@Autowired
	RestTemplate rest;

	@Value("${productms.name}")
	private String productMSName;
	
	@Autowired
	DiscoveryClient discoveryClient;

	public ProductDTO fetchProductInfo(long id) {

		ResponseEntity<ProductDTO> response = null;
		try {
		response = rest.getForEntity(URI.create(fetchProductBaseUrl() + "/products/" + id),
				ProductDTO.class);
		} catch(HttpClientErrorException e) {
			if(e.getRawStatusCode() != HttpStatus.NOT_FOUND.value()) {
				throw e;
			}
		}

		if (response == null || response.getStatusCode() != HttpStatus.OK || response.getBody() == null) {
			return null;
		}
		return response.getBody();
	}

	public RequestStatusDTO decrementAvailableQuantity(long productId, long decrementBy) {

		RequestStatusDTOBuilder status = RequestStatusDTO.builder();

		Map<String, Object> requestBody = new HashMap<>();
		requestBody.put("decrementBy", decrementBy);

		HttpEntity<Map<String, Object>> requestEntity = new HttpEntity<>(requestBody);

		ResponseEntity<PostResponse> responseEntity = rest.exchange(
				fetchProductBaseUrl() + "/products/" + productId + "/editQuantity", HttpMethod.POST, requestEntity,
				PostResponse.class);

		PostResponse response = responseEntity.getBody();

		if (responseEntity.getStatusCode() != HttpStatus.OK || !response.isSuccess()) {
			status.success(false);
			status.errorMsg(StringUtils.hasLength(response.getMessage()) ? response.getMessage() : "Could not block order quantity for productId: " + productId);

		} else {
			status.success(true);
		}

		return status.build();
	}
	
	public RequestStatusDTO incrementAvailableQuantity(long productId, long incrementBy) {

		RequestStatusDTOBuilder status = RequestStatusDTO.builder();

		Map<String, Object> requestBody = new HashMap<>();
		requestBody.put("incrementBy", incrementBy);

		HttpEntity<Map<String, Object>> requestEntity = new HttpEntity<>(requestBody);

		ResponseEntity<PostResponse> responseEntity = rest.exchange(
				fetchProductBaseUrl() + "/products/" + productId + "/editQuantity", HttpMethod.POST, requestEntity,
				PostResponse.class);

		PostResponse response = responseEntity.getBody();

		if (responseEntity.getStatusCode() != HttpStatus.OK || !response.isSuccess()) {
			status.success(false);
			status.errorMsg(StringUtils.hasLength(response.getMessage()) ? response.getMessage() : "Could not increment product quantity for: " + productId + " by: " + incrementBy);

		} else {
			status.success(true);
		}

		return status.build();
	}
	
	private URI fetchProductBaseUrl() {
		List<ServiceInstance> instances = discoveryClient.getInstances(productMSName); 
		ServiceInstance instance=instances.get(0); 
		URI msEndpointURI= instance.getUri();
		return msEndpointURI;
	}

}
