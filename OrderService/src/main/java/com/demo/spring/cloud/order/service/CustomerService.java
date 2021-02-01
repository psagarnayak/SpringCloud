package com.demo.spring.cloud.order.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.demo.spring.cloud.order.dto.BalanceEditRequest;
import com.demo.spring.cloud.order.dto.RequestStatusDTO;
import com.demo.spring.cloud.order.dto.RequestStatusDTO.RequestStatusDTOBuilder;
import com.demo.spring.cloud.order.restdto.PostResponse;

@Service
public class CustomerService {

	@Autowired
	private CustomerServiceFeignClient customermsFeignClient;

	public RequestStatusDTO chargeCustomer(long customerId, double amountToCharge) {

		RequestStatusDTOBuilder status = RequestStatusDTO.builder();

		ResponseEntity<PostResponse> responseEntity = customermsFeignClient.editBalance(customerId,
				BalanceEditRequest.builder().decrementBy(amountToCharge).build());
		PostResponse responseBody = responseEntity.getBody();
		if (responseEntity.getStatusCode() != HttpStatus.OK || !responseBody.isSuccess()) {
			status.success(false);
			status.errorMsg(
					StringUtils.hasLength(responseBody.getMessage()) ? responseBody.getMessage() : "Charges Rejected!");
		} else {
			status.success(true);
		}
		return status.build();
	}

	public RequestStatusDTO addBalance(long customerId, double amountToAdd) {

		RequestStatusDTOBuilder status = RequestStatusDTO.builder();

		ResponseEntity<PostResponse> responseEntity = customermsFeignClient.editBalance(customerId,
				BalanceEditRequest.builder().incrementBy(amountToAdd).build());
		PostResponse responseBody = responseEntity.getBody();
		if (responseEntity.getStatusCode() != HttpStatus.OK || !responseBody.isSuccess()) {
			status.success(false);
			status.errorMsg(StringUtils.hasLength(responseBody.getMessage()) ? responseBody.getMessage()
					: "Unable to add balance!");
		} else {
			status.success(true);
		}

		return status.build();
	}

}
