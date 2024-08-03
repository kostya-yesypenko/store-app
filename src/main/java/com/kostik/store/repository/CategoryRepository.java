package com.kostik.store.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.kostik.store.domain.Category;
@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
	
	public Category findByName(String name);

	public Optional<Category> findById(Long id);

}
