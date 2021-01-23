package com.demo.spring.cloud.customer;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

	public boolean incrementBalance(long id, double incrementBy) {
		Customer customer = repo.findById(id).orElse(null);
		if (customer == null || incrementBy < 1) {
			return false;
		}
		customer.setAccountBalance(customer.getAccountBalance() + incrementBy);
		repo.save(customer);
		return true;
	}

	public boolean decrementBalance(long id, long decrementBy) {
		Customer customer = repo.findById(id).orElse(null);
		if (customer == null || decrementBy < 1 || customer.getAccountBalance() < decrementBy) {
			return false;
		}
		customer.setAccountBalance(customer.getAccountBalance() - decrementBy);
		repo.save(customer);
		return true;
	}

}
