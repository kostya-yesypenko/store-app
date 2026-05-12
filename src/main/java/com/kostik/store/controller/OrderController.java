package com.kostik.store.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.kostik.store.domain.Order;
import com.kostik.store.domain.Product;
import com.kostik.store.domain.User;
import com.kostik.store.dto.OrderRequest;
import com.kostik.store.service.CartService;
import com.kostik.store.service.HttpService;
import com.kostik.store.service.OrderService;
import com.kostik.store.service.OrderService.ProductNotFoundException;
import com.kostik.store.service.OrderService.UserNotFoundException;
import com.kostik.store.service.ProductService;
import com.kostik.store.service.userData.UserDataProvider;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;

@Controller
@CrossOrigin(origins ="http://localhost:3000", allowedHeaders = "*", allowCredentials = "true")
@RequestMapping("/api/v1/")
@ResponseBody
@Slf4j
public class OrderController {
	@Autowired
	private OrderService orderService;
	
	@Autowired
	private UserDataProvider udp;
	
	@Autowired
	private CartService cartService;
	
	@Autowired
	private HttpService httpService;
	
	@Autowired
	private ProductService productService;
	
	@GetMapping("/orders")
	List<Order> getOrders(){
		return orderService.getOrders();
	}

	@PostMapping("/order")
	public ResponseEntity<?> createOrder(HttpServletRequest request, @RequestBody OrderRequest orderRequest) {
		try {
			User user = httpService.getCurrentUserFromSession();
        	httpService.printCurrentSession();

			Order order = orderService.createOrder(user, orderRequest.getProductId(), orderRequest.getQty(), orderRequest.getPrice());
			cartService.addOrderToCart(order);
			Product product = productService.getProductById(order.getProduct().getId());
			product.setQty(product.getQty()-order.getQty());
			productService.save(product);
			return new ResponseEntity<>(order, HttpStatus.CREATED);
		} catch (UserNotFoundException e) {
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
