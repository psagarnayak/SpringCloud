package com.demo.spring.cloud.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.demo.spring.cloud.entity.Customer;

public interface CustomerRepo extends JpaRepository<Customer, Long>{

}
