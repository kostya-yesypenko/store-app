package com.kostik.store.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kostik.store.domain.Employee;
import com.kostik.store.repository.EmployeeRepository;

@Service
public class EmployeeService {
	
	@Autowired
	private EmployeeRepository employeeRepository;
	
	public EmployeeService(EmployeeRepository employeeRepository) {
		this.employeeRepository = employeeRepository;
	}

	public Employee findByLogin(String login) {
		return employeeRepository.findByLogin(login);
	}

	public List<Employee> getEmployees() {
		return employeeRepository.findAll();
	}
	
	

}
