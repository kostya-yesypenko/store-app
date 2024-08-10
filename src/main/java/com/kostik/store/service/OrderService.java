package com.kostik.store.service;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.kostik.store.domain.Order;
import com.kostik.store.domain.Product;
import com.kostik.store.domain.User;
import com.kostik.store.repository.OrderRepository;

import jakarta.annotation.PostConstruct;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class OrderService {

	@Autowired
	private OrderRepository orderRepository;

	@Autowired
	private DataSource ds;

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Autowired
	private ProductService productService;

	@PostConstruct
	void init() {
		log.debug("Order service bean has been constructed");
	}

	public Order createOrder(User user, Long productId, Long quantity, Double price) throws UserNotFoundException, ProductNotFoundException, Exception {
		Product product = productService.getProductById(productId);

		if (product == null) {
			throw new ProductNotFoundException("Product not found with ID: " + productId);
		}

		if(quantity>product.getQty())
			throw new Exception("Not enough products on market");
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
		order.setDateModified(LocalDateTime.now());
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
		productService.save(product);
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
