package com.demo.spring.cloud.order.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.demo.spring.cloud.order.dto.EditProductQtyRequest;
import com.demo.spring.cloud.order.dto.ProductDTO;
import com.demo.spring.cloud.order.restdto.PostResponse;

@FeignClient("${productms.name}")
public interface ProductServiceFeignClient {

	@GetMapping("/products/{productId}")
	public ResponseEntity<ProductDTO> fetchProductInfo(@PathVariable("productId") long id);

	@PostMapping("/products/{productId}/editQuantity")
	public ResponseEntity<PostResponse> editQuantity(@PathVariable("productId") long productId,
			@RequestBody EditProductQtyRequest request);

}
