package com.demo.spring.cloud.product.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.demo.spring.cloud.product.entity.Product;

public interface ProductRepo extends JpaRepository<Product, Long>{
	
	List<Product> findByName(String name);

}
