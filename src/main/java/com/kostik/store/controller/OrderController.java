package com.kostik.store.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kostik.store.domain.Order;
import com.kostik.store.dto.OrderRequest;
import com.kostik.store.service.OrderService;
import com.kostik.store.service.OrderService.EmployeeNotFoundException;
import com.kostik.store.service.OrderService.ProductNotFoundException;

@RestController
@CrossOrigin("http://localhost:3000")
@RequestMapping("/api/v1/")
public class OrderController {
	@Autowired
	private OrderService orderService;

	@PostMapping("/order")
	public ResponseEntity<?> createOrder(@RequestBody OrderRequest request) {
		try {
			// Call the createOrder method from the service
			Order createdOrder = orderService.createOrder(request.getEmail(), request.getProductId(), request.getQty(), request.getPrice());

			
			return new ResponseEntity<>(createdOrder, HttpStatus.CREATED);
		} catch (EmployeeNotFoundException e) {
			// Handle the case where the employee is not found
			String errorMessage = "User is not registered with the provided email.";
			return new ResponseEntity<>(errorMessage, HttpStatus.NOT_FOUND);
		} catch (ProductNotFoundException e) {
			// Handle the case where the product is not found
			String errorMessage = "Product not found with the provided ID.";
			return new ResponseEntity<>(errorMessage, HttpStatus.NOT_FOUND);
		} catch (Exception e) {
			// Handle other exceptions
			e.printStackTrace();
			return new ResponseEntity<>("Error creating the order. Please try again.", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}
