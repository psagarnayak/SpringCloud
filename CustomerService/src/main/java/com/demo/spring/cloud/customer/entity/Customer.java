package com.demo.spring.cloud.customer.entity;

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
@Table(name = "Customers")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class Customer {
	
	@Id
	@GeneratedValue
	@Column(name = "id")
	private long customerId;

	@Column(name = "name")
	private String customerName;
	
	@Column(name = "balance")
	private double accountBalance;
	
	private LocalDate signupDate;

}
