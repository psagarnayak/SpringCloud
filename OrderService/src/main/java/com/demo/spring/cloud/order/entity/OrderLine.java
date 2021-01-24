package com.demo.spring.cloud.order.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name="OrderItems")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class OrderLine {
	
	@Id
	@GeneratedValue
	private long id;
	
	private long productId;
	
	private long quantity;
	
	private double pricePerItem;

}
