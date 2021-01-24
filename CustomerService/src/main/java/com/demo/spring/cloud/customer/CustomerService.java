package com.demo.spring.cloud.customer;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.demo.spring.cloud.customer.dto.RequestStatus;
import com.demo.spring.cloud.customer.dto.RequestStatus.RequestStatusBuilder;
import com.demo.spring.cloud.customer.entity.Customer;
import com.demo.spring.cloud.customer.repo.CustomerRepo;

@Service
public class CustomerService {

	@Autowired
	CustomerRepo repo;

	public List<Customer> fetchAllCustomers() {
		return repo.findAll();
	}

	public Customer findCustomerById(long id) {
		return repo.findById(id).orElse(null);
	}

	public Customer registerCustomer(Customer customer) {
		customer.setSignupDate(LocalDate.now());
		// TODO Auto-generated method stub
		return repo.save(customer);
	}

	public boolean deleteCustomer(long customerId) {
		// TODO Auto-generated method stub
		boolean customerExists = repo.existsById(customerId);
		if (customerExists) {
			repo.deleteById(customerId);
			;
		}
		return customerExists && !repo.existsById(customerId);
	}

	public RequestStatus incrementBalance(long id, double incrementBy) {
		RequestStatusBuilder builder = RequestStatus.builder();
		Customer customer = repo.findById(id).orElse(null);
		if (customer == null) {
			builder.success(false);
			builder.errorMsg("Customer Not found");
		}else if( incrementBy < 1) {
			builder.success(false);
			builder.errorMsg("Invalid incrementBy value: " + incrementBy);
		} else {
			customer.setAccountBalance(customer.getAccountBalance() + incrementBy);
			Customer savedCustomer = repo.save(customer);
			builder.success(true);
			builder.customer(savedCustomer);
		}
		return builder.build();
	}

	public RequestStatus decrementBalance(long id, long decrementBy) {
		System.out.println("Attempting to decrement balance by: " + decrementBy);
		RequestStatusBuilder builder = RequestStatus.builder();
		Customer customer = repo.findById(id).orElse(null);
		if (customer == null) {
			builder.success(false);
			builder.errorMsg("Customer Not found");
		} else if( decrementBy < 1 ) {
			builder.success(false);
			builder.errorMsg("Invalid decrementBy value: " + decrementBy);
		}else if( customer.getAccountBalance() < decrementBy) {
			builder.success(false);
			builder.errorMsg("Insufficient Balance in Customer Account");
		} else {
			customer.setAccountBalance(customer.getAccountBalance() - decrementBy);
			Customer savedCustomer = repo.save(customer);
			builder.success(true);
			builder.customer(savedCustomer);
		}
		return builder.build();
	}

}
