package com.demo.spring.cloud.product.controller;

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

import com.demo.spring.cloud.product.entity.Product;
import com.demo.spring.cloud.product.restdto.PostResponse;
import com.demo.spring.cloud.product.restdto.ProductEditRequest;
import com.demo.spring.cloud.product.service.ProductService;

@RestController
@RequestMapping("/products")
public class ProductController {

	@Autowired
	ProductService service;

	@GetMapping
	public List<Product> fetchAll(@RequestParam(name = "name", required = false) String productName) {

		List<Product> products = null;

		if (StringUtils.hasLength(productName)) {
			products = service.findProductByName(productName);
		} else {
			products = service.fetchAllProducts();
		}

		return products;
	}

	@GetMapping("{id}")
	public Product getCustomerById(@PathVariable("id") long id, HttpServletResponse response) {
		Product product = service.findProductById(id);
		if (product == null) {
			response.setStatus(HttpStatus.NOT_FOUND.value());
		}
		return product;
	}

	@PostMapping
	public Product addProduct(@RequestBody Product product) {
		return service.addProduct(product);
	}

	@DeleteMapping("/{id}")
	public void deleteProduct(@PathVariable("id") long id, HttpServletResponse response) {
		boolean deleted = service.deleteProduct(id);
		if (!deleted) {
			response.setStatus(HttpStatus.NOT_FOUND.value());
		}
	}

	@PostMapping("/{id}/editQuantity")
	public PostResponse editQuantity(@PathVariable("id") long id, @RequestBody ProductEditRequest request, HttpServletResponse response) {
		PostResponse responseBody = null;
		if(request.getIncrementBy() != null && request.getIncrementBy() > 0) {
			boolean incStatus = service.incrementQuantity(id, request.getIncrementBy());
			responseBody = incStatus ?
					new PostResponse(incStatus, "Increment successful.")
					: new PostResponse(incStatus, "Increment Failed.");
		} else if(request.getDecrementBy() != null && request.getDecrementBy() > 0) {
			boolean decStatus = service.decrementQuantity(id, request.getDecrementBy());
			responseBody = decStatus ?
					new PostResponse(decStatus, "Decrement successful.")
					: new PostResponse(decStatus, "Decrement Failed.");
		} else {
			response.setStatus(HttpStatus.BAD_REQUEST.value());
			responseBody = new PostResponse(false, "Bad request! Either incrementBy or DecrementBy expected");
		}
		
		return responseBody;
	}
	
}
