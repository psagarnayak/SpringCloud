package com.demo.spring.cloud.customer.controllers;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.demo.spring.cloud.customer.CustomerService;
import com.demo.spring.cloud.customer.dto.RequestStatus;
import com.demo.spring.cloud.customer.entity.Customer;
import com.demo.spring.cloud.customer.restdto.PostResponse;
import com.demo.spring.cloud.customer.restdto.BalanceEditRequest;

@RestController
@RequestMapping("/customers")
public class CustomerController {

	@Autowired
	CustomerService service;

	@GetMapping("")
	public List<Customer> getAllCustomers() {
		return service.fetchAllCustomers();
	}

	@GetMapping("{id}")
	public Customer getCustomerById(@PathVariable("id") long customerId, HttpServletResponse response) {
		Customer customer = service.findCustomerById(customerId);
		if (customer == null) {
			response.setStatus(HttpStatus.NOT_FOUND.value());
		}
		return customer;
	}

	@PostMapping("")
	public Customer signUpCustomer(@RequestBody Customer customer) {
		return service.registerCustomer(customer);
	}

	@DeleteMapping("/{id}")
	public void deleteCustomer(@PathVariable("id") long customerId, HttpServletResponse response) {
		boolean customerDeleted = service.deleteCustomer(customerId);
		if (!customerDeleted) {
			response.setStatus(HttpStatus.NOT_FOUND.value());
		}
	}

	@PostMapping("/{id}/editBalance")
	public PostResponse editBalance(@PathVariable("id") long id, @RequestBody BalanceEditRequest request,
			HttpServletResponse response) {
		PostResponse responseBody = null;
		if (request.getIncrementBy() != null && request.getIncrementBy() > 0) {
			RequestStatus status = service.incrementBalance(id, request.getIncrementBy());
			responseBody = status.isSuccess() ? new PostResponse(true, "Increment successful.")
					: new PostResponse(false, "Failed: " + status.getErrorMsg());

		} else if (request.getDecrementBy() != null && request.getDecrementBy() > 0) {
			RequestStatus status = service.decrementBalance(id, request.getDecrementBy());
			responseBody = status.isSuccess() ? new PostResponse(true, "Decrement successful.")
					: new PostResponse(false, "Failed: " + status.getErrorMsg());
		} else {
			response.setStatus(HttpStatus.BAD_REQUEST.value());
			responseBody = new PostResponse(false, "Bad request! Either incrementBy or DecrementBy expected");
		}

		return responseBody;
	}

}
