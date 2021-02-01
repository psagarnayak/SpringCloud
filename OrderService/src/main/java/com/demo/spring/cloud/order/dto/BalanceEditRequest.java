package com.demo.spring.cloud.order.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class BalanceEditRequest {
	
	private Double decrementBy;
	
	private Double incrementBy;

}
