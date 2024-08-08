package com.kostik.store.service;

import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.kostik.store.domain.Cart;
import com.kostik.store.domain.Order;
import com.kostik.store.domain.Product;
import com.kostik.store.domain.User;
import com.kostik.store.repository.OrderRepository;
import com.kostik.store.repository.ProductRepository;
import com.kostik.store.repository.UserRepository;

import jakarta.annotation.PostConstruct;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class OrderService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private ProductRepository productRepository;

	@Autowired
	private OrderRepository orderRepository;
	
	@Autowired
	private DataSource ds;
	
	@Autowired
	private JdbcTemplate jdbcTemplate;	
		
	@PostConstruct
	void init() {
		log.debug("Order service bean has been constructed");
	}
	
	public Order createOrder(User user, Long productId, Long quantity, Double price) throws UserNotFoundException, ProductNotFoundException {		
		// Find the product by productId
		Product product = productRepository.findById(productId).orElse(null);

		if (product == null) {
			throw new ProductNotFoundException("Product not found with ID: " + productId);
		}

		// Create a new OrderEntity
		Order order = new Order();
		order.setUser(user);
		order.setProduct(product);
		order.setQty(quantity);
		order.setPrice(price);
				
		return order;
	}
	


	@Transactional
	public Order saveOrder(Order order) throws Exception {
		Order dbOrder = orderRepository.save(order);
		
		if (dbOrder == null) {
			throw new Exception("Can not create order");
		}

		Product product = order.getProduct();

		if (product == null) {
			throw new ProductNotFoundException("Product not found");
		}
		// Update product quantity by subtracting order quantity
		product.setQty(product.getQty() - order.getQty());
		productRepository.save(product);
		return dbOrder;
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
