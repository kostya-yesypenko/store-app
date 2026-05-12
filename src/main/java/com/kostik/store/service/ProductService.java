package com.kostik.store.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kostik.store.domain.Product;
import com.kostik.store.repository.ProductRepository;
import com.kostik.store.service.OrderService.ProductNotFoundException;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class ProductService {

	@Autowired
	private ProductRepository productRepository;

	public Product findByName(String name) {
		return productRepository.findByName(name);
	}

	public List<Product> getProducts() {
		return productRepository.findAll();
	}

	public Optional<Product> getProduct(Long id) {
		return productRepository.findById(id);
	}

	// Find the product by productId
	public Product getProductById(Long productId) {
		return productRepository.findById(productId).orElse(null);
	}

	public void save(Product product) {
		productRepository.save(product);
	}
}
