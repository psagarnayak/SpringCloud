package com.demo.spring.cloud.customer.restdto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BalanceEditRequest {

	private Long incrementBy;
	private Long decrementBy;

}
