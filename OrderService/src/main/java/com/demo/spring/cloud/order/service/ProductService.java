package com.demo.spring.cloud.order.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.demo.spring.cloud.order.dto.EditProductQtyRequest;
import com.demo.spring.cloud.order.dto.ProductDTO;
import com.demo.spring.cloud.order.dto.RequestStatusDTO;
import com.demo.spring.cloud.order.dto.RequestStatusDTO.RequestStatusDTOBuilder;
import com.demo.spring.cloud.order.restdto.PostResponse;

@Service
public class ProductService {

	@Autowired
	private ProductServiceFeignClient productMsClient;

	public ProductDTO fetchProductInfo(long id) {

		ResponseEntity<ProductDTO> response = productMsClient.fetchProductInfo(id);

		if (response == null || response.getStatusCode() != HttpStatus.OK || response.getBody() == null) {
			return null;
		}
		return response.getBody();
	}

	public RequestStatusDTO decrementAvailableQuantity(long productId, long decrementBy) {

		RequestStatusDTOBuilder status = RequestStatusDTO.builder();

		ResponseEntity<PostResponse> responseEntity = productMsClient.editQuantity(productId,
				EditProductQtyRequest.builder().decrementBy(decrementBy).build());

		PostResponse response = responseEntity.getBody();

		if (responseEntity.getStatusCode() != HttpStatus.OK || !response.isSuccess()) {
			status.success(false);
			status.errorMsg(StringUtils.hasLength(response.getMessage()) ? response.getMessage()
					: "Could not block order quantity for productId: " + productId);

		} else {
			status.success(true);
		}

		return status.build();
	}

	public RequestStatusDTO incrementAvailableQuantity(long productId, long incrementBy) {

		RequestStatusDTOBuilder status = RequestStatusDTO.builder();

		ResponseEntity<PostResponse> responseEntity = productMsClient.editQuantity(productId,
				EditProductQtyRequest.builder().incrementBy(incrementBy).build());

		PostResponse response = responseEntity.getBody();

		if (responseEntity.getStatusCode() != HttpStatus.OK || !response.isSuccess()) {
			status.success(false);
			status.errorMsg(StringUtils.hasLength(response.getMessage()) ? response.getMessage()
					: "Could not increment product quantity for: " + productId + " by: " + incrementBy);

		} else {
			status.success(true);
		}

		return status.build();
	}

}
