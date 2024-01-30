package com.kostik.store.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kostik.store.domain.Product;
import com.kostik.store.service.ProductService;


@RestController
@CrossOrigin("http://localhost:3000")
@RequestMapping("/api/v1/")
public class ProductController {
	
	@Autowired
	private ProductService productService;

	@GetMapping("/products")
	List<Product> getProducts(){
		return (productService.getProducts());
	}
	
	@GetMapping("/products/{id}")
	Optional<Product> getProduct(@PathVariable("id") Long id){
		return (productService.getProduct(id));
	}
	

}
