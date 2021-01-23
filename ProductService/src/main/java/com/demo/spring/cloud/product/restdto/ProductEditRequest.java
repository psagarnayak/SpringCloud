package com.demo.spring.cloud.product.restdto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductEditRequest {

	private Long incrementBy;
	private Long decrementBy;

}
