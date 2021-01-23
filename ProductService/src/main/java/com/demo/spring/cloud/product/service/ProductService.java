package com.demo.spring.cloud.product.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.demo.spring.cloud.product.entity.Product;
import com.demo.spring.cloud.product.repo.ProductRepo;

@Service
public class ProductService {

	@Autowired
	private ProductRepo repo;

	public List<Product> fetchAllProducts() {
		return repo.findAll();
	}

	public Product findProductById(long id) {
		return repo.findById(id).orElse(null);
	}

	public List<Product> findProductByName(String name) {
		return repo.findByName(name);
	}

	public boolean incrementQuantity(long id, long incrementBy) {
		Product product = repo.findById(id).orElse(null);
		if (product == null || incrementBy < 1) {
			return false;
		}
		product.setAvailableQuantity(product.getAvailableQuantity() + incrementBy);
		repo.save(product);
		return true;
	}

	public boolean decrementQuantity(long id, long decrementBy) {
		Product product = repo.findById(id).orElse(null);
		if (product == null || decrementBy < 1 || product.getAvailableQuantity() < decrementBy) {
			return false;
		}
		product.setAvailableQuantity(product.getAvailableQuantity() - decrementBy);
		repo.save(product);
		return true;
	}

	public Product addProduct(Product product) {
		return repo.save(product);
	}

	public boolean deleteProduct(long id) {
		// TODO Auto-generated method stub
		boolean productExists = repo.existsById(id);
		if (productExists) {
			repo.deleteById(id);
			;
		}
		return productExists && !repo.existsById(id);
	}
}
