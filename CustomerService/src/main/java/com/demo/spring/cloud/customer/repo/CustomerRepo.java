package com.demo.spring.cloud.customer.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.demo.spring.cloud.customer.entity.Customer;

public interface CustomerRepo extends JpaRepository<Customer, Long>{

}
