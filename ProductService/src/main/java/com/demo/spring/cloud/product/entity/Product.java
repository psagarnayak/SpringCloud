package com.demo.spring.cloud.product.entity;

import java.time.LocalDate;

import javax.persistence.Column;
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
@Table(name="Products")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class Product {
	
	@Id
	@GeneratedValue
	private long id;
	
	private String name;
	
	private double price;
	
	@Column(name="Quant_Avail")
	private long availableQuantity;

}
