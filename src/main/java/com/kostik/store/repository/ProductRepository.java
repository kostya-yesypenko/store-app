package com.kostik.store.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.kostik.store.domain.Product;
@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
	
	public Product findByName(String name);

	List<Product> findByCategoryId(Long categoryId);

}
