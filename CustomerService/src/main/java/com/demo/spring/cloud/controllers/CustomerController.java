package com.demo.spring.cloud.controllers;

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

import com.demo.spring.cloud.CustomerService;
import com.demo.spring.cloud.entity.Customer;
import com.demo.spring.cloud.repo.CustomerRepo;

@RestController
@RequestMapping("/customers")
public class CustomerController {

	@Autowired
	CustomerService customerService;
	
	@GetMapping("")
	public List<Customer> getAllCustomers() {
		return customerService.fetchAllCustomers();
	}
	
	@GetMapping("{id}")
	public Customer getCustomerById(@PathVariable("id") long customerId, HttpServletResponse response) {
		Customer customer = customerService.findCustomerById(customerId);
		if(customer == null) {
			response.setStatus(HttpStatus.NOT_FOUND.value());
		}
		return customer;
	}
	
	@PostMapping("")
	public Customer signUpCustomer(@RequestBody Customer customer) {
		return customerService.registerCustomer(customer);
	}
	
	@DeleteMapping("/{id}")
	public void deleteCustomer(@PathVariable("id") long customerId, HttpServletResponse response) {
		boolean customerDeleted = customerService.deleteCustomer(customerId);
		if(!customerDeleted) {
			response.setStatus(HttpStatus.NOT_FOUND.value());
		}
	}
}
