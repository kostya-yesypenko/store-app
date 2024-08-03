package com.kostik.store.service;

import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.kostik.store.domain.Order;
import com.kostik.store.domain.Product;
import com.kostik.store.domain.User;
import com.kostik.store.repository.OrderRepository;
import com.kostik.store.repository.ProductRepository;
import com.kostik.store.repository.UserRepository;

import jakarta.annotation.PostConstruct;
import jakarta.transaction.Transactional;
import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
public class OrderService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private ProductRepository productRepository;

	@Autowired
	private OrderRepository orderRepository;
	
	@Autowired
	@Qualifier("ds1")
	private DataSource ds;
	
	@Autowired
	private JdbcTemplate jdbcTemplate;	
		
	@PostConstruct
	void init() {
		log.debug("Order service bean has been constructed");
	}

	@Transactional
	public Order createOrder(String email, Long productId, Long quantity, Double price) throws UserNotFoundException, ProductNotFoundException {
		// Find the employee by email
		User employee = userRepository.findByEmail(email);

		if (employee == null) {
			log.error("Employee with email = {} not found", email);
			throw new UserNotFoundException("Employee not found with email: " + email);
		}		

		// Find the product by productId
		Product product = productRepository.findById(productId).orElse(null);

		if (product == null) {
			throw new ProductNotFoundException("Product not found with ID: " + productId);
		}

		// Create a new OrderEntity
		Order order = new Order();
		order.setEmployee(employee);
		order.setProduct(product);
		order.setQty(quantity);
		order.setPrice(price);
		Order savedOrder = orderRepository.save(order);

		//Update product quantity by subtracting order quantity
		product.setQty(product.getQty()-quantity);
		productRepository.save(product);
		
		return savedOrder;
	}
	
	public List<Order> getOrders() {
		return (orderRepository.findAll());
	}

	public class UserNotFoundException extends Exception {
		public UserNotFoundException(String message) {
			super(message);
		}
	}

	public class ProductNotFoundException extends Exception {
		public ProductNotFoundException(String message) {
			super(message);
		}
	}



}
