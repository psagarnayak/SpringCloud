package com.demo.spring.cloud.order.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.demo.spring.cloud.order.entity.Order;

public interface OrderRepo extends JpaRepository<Order, Long> {
	
	List<Order> findByCustomerId(long id);

}
