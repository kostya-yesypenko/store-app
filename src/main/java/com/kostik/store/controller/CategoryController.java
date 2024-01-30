package com.kostik.store.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kostik.store.domain.Category;
import com.kostik.store.domain.Product;
import com.kostik.store.service.CategoryService;


@RestController
@CrossOrigin("http://localhost:3000")
@RequestMapping("/api/v1/")
public class CategoryController {
	
	@Autowired
	private CategoryService categoryService;

	@GetMapping("/categories")
	List<Category> getCategories(){
		return (categoryService.getCategories());
	}
	
    @GetMapping("/category/{id}")
    List<Product> getProductsByCategory(@PathVariable Long id) {
        return categoryService.getProductsByCategory(id);
    }
}
