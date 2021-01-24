package com.demo.spring.cloud.order.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.demo.spring.cloud.order.dto.ProductDTO;
import com.demo.spring.cloud.order.dto.RequestStatusDTO;
import com.demo.spring.cloud.order.dto.RequestStatusDTO.RequestStatusDTOBuilder;
import com.demo.spring.cloud.order.entity.Order;
import com.demo.spring.cloud.order.entity.OrderLine;
import com.demo.spring.cloud.order.exceptions.InvalidOrderException;
import com.demo.spring.cloud.order.exceptions.OrderPlacementException;
import com.demo.spring.cloud.order.exceptions.ProductUnavailableException;
import com.demo.spring.cloud.order.repo.OrderRepo;

@Service
public class OrderService {

	@Autowired
	private OrderRepo repo;

	@Autowired
	private CustomerService customerService;
	
	@Autowired
	private ProductService productService;

	public List<Order> findAllOrders() {
		return repo.findAll();
	}

	public Order findById(long id) {
		return repo.findById(id).orElse(null);
	}

	public List<Order> findByCustomerId(long id) {
		return repo.findByCustomerId(id);
	}

	public RequestStatusDTO updateOrderStatus(long id, String status) {

		RequestStatusDTOBuilder builder = RequestStatusDTO.builder();
		Order order = repo.findById(id).orElse(null);
		if (order == null) {
			builder.success(false);
			builder.errorMsg("Order not found!");
		} else {
			order.setStatus(status);
			Order savedOrder = repo.save(order);
			builder.success(true);
			builder.order(savedOrder);
		}

		return builder.build();
	}

	public RequestStatusDTO placeOrder(Order order) {
		
		RequestStatusDTOBuilder status = RequestStatusDTO.builder();
		
		try {
			blockProductAndUpdateOrderTotal(order);
		} catch (OrderPlacementException | InvalidOrderException | ProductUnavailableException e) {
			status.success(false);
			status.errorMsg(e.getMessage());
			return status.build();
		}

		RequestStatusDTO chargeCustomer = customerService.chargeCustomer(order.getCustomerId(), order.getOrderTotal());

		if (!chargeCustomer.isSuccess()) {
			reverseProductBlock(order.getItems());
			status.success(false);
			status.errorMsg(chargeCustomer.getErrorMsg());
		} else {
			
			order.setStatus("PLACED");
			order.setPlacedAt(LocalDateTime.now());
			Order placedOrder = repo.save(order);
			if (placedOrder == null) {
				status.success(false);
				status.errorMsg("Error placing order");
			} else {
				status.success(true);
				status.order(placedOrder);
			}
		}
		return status.build();
	}
	
	private void blockProductAndUpdateOrderTotal(Order order) throws OrderPlacementException, InvalidOrderException, ProductUnavailableException {
		
		double orderTotal = 0.0;
		ProductDTO productInfo = null;
		
		List<OrderLine> items = order.getItems();
		OrderLine item = null;
		for(int i = 0; i < items.size(); i++) {
			item = items.get(i);
			productInfo = productService.fetchProductInfo(item.getProductId());
			
			if(productInfo == null) {
				throw new InvalidOrderException(String.format("ProductId %s is not recognized", item.getProductId()));
			}
			item.setPricePerItem(productInfo.getPrice());
			
			RequestStatusDTO statusDTO = productService.decrementAvailableQuantity(item.getProductId(), item.getQuantity());
			
			if(!statusDTO.isSuccess()) {
				//Revert all product blocks made so far
				if(i>0) {
					reverseProductBlock(items.subList(0, i));
				}
				
				if(item.getQuantity() > productInfo.getAvailableQuantity()) {
				throw new ProductUnavailableException(
						String.format("Insufficient Availability: Available Quantity for product: %s is %s",
								productInfo.getName(), productInfo.getAvailableQuantity()));
				} else {
					throw new OrderPlacementException("Error placing Order");
				}
			}
			
			orderTotal += productInfo.getPrice() * item.getQuantity();
		}
		
		order.setOrderTotal(orderTotal);
	}

	private void reverseProductBlock(List<OrderLine> items) {
		for(OrderLine item: items) {
			productService.incrementAvailableQuantity(item.getProductId(), item.getQuantity());
		}
	}
	
	public boolean deleteOrder(long id) {
		// TODO Auto-generated method stub
		boolean orderExists = repo.existsById(id);
		if (orderExists) {
			repo.deleteById(id);
		}
		return orderExists && !repo.existsById(id);
	}
	
}
