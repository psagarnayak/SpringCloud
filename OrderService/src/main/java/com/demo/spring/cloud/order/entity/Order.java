package com.demo.spring.cloud.order.entity;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name="Orders")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class Order {
	
	@Id
	@GeneratedValue
	private long id;
	
	private long customerId;
	
	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
	private List<OrderLine> items;
	
	private LocalDateTime placedAt;
	
	private String status;
	
	private double orderTotal;

}
