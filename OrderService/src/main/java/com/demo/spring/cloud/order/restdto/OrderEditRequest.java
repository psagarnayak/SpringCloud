package com.demo.spring.cloud.order.restdto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderEditRequest {

	private String newStatus;
}
