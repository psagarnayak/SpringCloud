package com.demo.spring.cloud;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.demo.spring.cloud.entity.Customer;
import com.demo.spring.cloud.repo.CustomerRepo;

@Service
public class CustomerService {
	
	@Autowired
	CustomerRepo customerRepo;
	
	public List<Customer> fetchAllCustomers() {
		return customerRepo.findAll();
	}
	
	public Customer findCustomerById(long id) {
		return customerRepo.findById(id).orElse(null);
	}

	public Customer registerCustomer(Customer customer) {
		// TODO Auto-generated method stub
		return customerRepo.save(customer);
	}

	public boolean deleteCustomer(long customerId) {
		// TODO Auto-generated method stub
		boolean customerExists = customerRepo.existsById(customerId);
		customerRepo.deleteById(customerId);;
		return customerExists && !customerRepo.existsById(customerId);
	}

}
