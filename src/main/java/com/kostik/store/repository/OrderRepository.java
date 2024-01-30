package com.kostik.store.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.kostik.store.domain.Order;
@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
		
}
