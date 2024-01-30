package com.kostik.store.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kostik.store.domain.Category;
import com.kostik.store.domain.Product;
import com.kostik.store.repository.CategoryRepository;
import com.kostik.store.repository.ProductRepository;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class CategoryService {

	@Autowired
	private CategoryRepository categoryRepository;
	
	@Autowired
	private ProductRepository productRepository;

	public Category findByName(String name) {
		return categoryRepository.findByName(name);
	}

	public List<Category> getCategories() {
		return categoryRepository.findAll();
	}

	public List<Product> getProductsByCategory(Long categoryId) {
        // Retrieve products by category ID using the ProductRepository
        return productRepository.findByCategoryId(categoryId);
    }

}
