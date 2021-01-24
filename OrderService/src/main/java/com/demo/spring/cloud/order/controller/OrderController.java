package com.demo.spring.cloud.order.controller;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.demo.spring.cloud.order.dto.RequestStatusDTO;
import com.demo.spring.cloud.order.entity.Order;
import com.demo.spring.cloud.order.restdto.OrderEditRequest;
import com.demo.spring.cloud.order.restdto.PostResponse;
import com.demo.spring.cloud.order.restdto.PostResponse.PostResponseBuilder;
import com.demo.spring.cloud.order.service.OrderService;

@RestController
@RequestMapping("/orders")
public class OrderController {

	@Autowired
	private OrderService service;

	@GetMapping
	public List<Order> fetchOrders(@RequestParam(name = "placedBy", required = false) Long customerId) {

		List<Order> orders = null;

		if (customerId != null && customerId > 0) {
			orders = service.findByCustomerId(customerId);
		} else {
			orders = service.findAllOrders();
		}
		return orders;
	}

	@GetMapping("/{id}")
	public Order fetchOrderbyId(@PathVariable long id, HttpServletResponse response) {
		Order order = service.findById(id);
		if (order == null) {
			response.setStatus(HttpStatus.NOT_FOUND.value());
		}
		return order;
	}

	@PostMapping
	public PostResponse placeOrder(@RequestBody Order order) {

		RequestStatusDTO placementStatus = service.placeOrder(order);

		PostResponseBuilder response = PostResponse.builder();
		if (placementStatus.isSuccess()) {
			response.success(true);
			response.message("Order placed successfully");
			response.order(placementStatus.getOrder());
		} else {
			response.success(false);
			response.message(placementStatus.getErrorMsg());
			response.order(order);
		}
		return response.build();
	}

	@PostMapping("/{id}/editQuantity")
	public PostResponse updateOrderStatus(@PathVariable("id") long id, @RequestBody OrderEditRequest request,
			HttpServletResponse response) {

		PostResponseBuilder updateResponse = PostResponse.builder();
		if (StringUtils.hasLength(request.getNewStatus())) {
			RequestStatusDTO updateStatus = service.updateOrderStatus(id, request.getNewStatus());
			
			if(updateStatus.isSuccess()) {
				updateResponse.success(true);
				updateResponse.message("Status Updated Successfully");
				updateResponse.order(updateStatus.getOrder());
			} else {
				updateResponse.success(false);
				updateResponse.message(updateStatus.getErrorMsg());
				response.setStatus(HttpStatus.BAD_REQUEST.value());
			}
		} else {
			updateResponse.success(false);
			updateResponse.message("Bad request! 'newStatus' expected.");
			response.setStatus(HttpStatus.BAD_REQUEST.value());
		}

		return updateResponse.build();
	}
	
	@DeleteMapping("/{id}")
	public void deleteOrderbyId(@PathVariable long id, HttpServletResponse response) {	
		boolean deleted = service.deleteOrder(id);
		if (!deleted) {
			response.setStatus(HttpStatus.NOT_FOUND.value());
		}
	}
}
