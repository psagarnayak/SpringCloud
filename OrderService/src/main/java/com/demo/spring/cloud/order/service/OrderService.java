package com.demo.spring.cloud.order.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.demo.spring.cloud.order.dto.RequestStatusDTO;
import com.demo.spring.cloud.order.dto.RequestStatusDTO.RequestStatusDTOBuilder;
import com.demo.spring.cloud.order.entity.Order;
import com.demo.spring.cloud.order.repo.OrderRepo;

@Service
public class OrderService {

	@Autowired
	private OrderRepo repo;

	@Autowired
	private CustomerService customerService;

	public List<Order> findAllOrders() {
		return repo.findAll();
	}

	public Order findById(long id) {
		return repo.findById(id).orElse(null);
	}

	public List<Order> findByCustomerId(long id) {
		return repo.findByCustomerId(id);
	}

	public RequestStatusDTO updateOrderStatus(long id, String status) {

		RequestStatusDTOBuilder builder = RequestStatusDTO.builder();
		Order order = repo.findById(id).orElse(null);
		if (order == null) {
			builder.success(false);
			builder.errorMsg("Error! Order not found!");
		} else {
			order.setStatus(status);
			Order savedOrder = repo.save(order);
			builder.success(true);
			builder.order(savedOrder);
		}

		return builder.build();
	}

	public RequestStatusDTO placeOrder(Order order) {

		RequestStatusDTO chargeCustomer = customerService.chargeCustomer(order.getCustomerId(), 10.0);

		RequestStatusDTOBuilder status = RequestStatusDTO.builder();
		if (!chargeCustomer.isSuccess()) {
			status.success(false);
			status.errorMsg("Error: " + chargeCustomer.getErrorMsg());
		} else {
			order.setStatus("PLACED");
			order.setOrderTotal(10.0);
			order.setPlacedAt(LocalDateTime.now());
			Order placedOrder = repo.save(order);
			if (placedOrder == null) {
				status.success(false);
				status.errorMsg("Error placing order");
			} else {
				status.success(true);
				status.order(placedOrder);
			}
		}
		return status.build();
	}
	
	public boolean deleteOrder(long id) {
		// TODO Auto-generated method stub
		boolean orderExists = repo.existsById(id);
		if (orderExists) {
			repo.deleteById(id);
		}
		return orderExists && !repo.existsById(id);
	}
	
}
