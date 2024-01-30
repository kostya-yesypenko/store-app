package com.kostik.store.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.kostik.store.domain.Employee;
@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {
	
	public Employee findByLogin(String login);
	
	public Employee findByEmail(String email);
	
}
