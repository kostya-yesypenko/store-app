package com.kostik.store.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kostik.store.domain.Employee;
import com.kostik.store.service.EmployeeService;


@RestController
@CrossOrigin("http://localhost:3000")
@RequestMapping("/api/v1/")
public class EmployeeController {
	@Autowired
	private EmployeeService employeeService;


	@GetMapping("/employees")
	List<Employee> getEmployees(){
		return employeeService.getEmployees();
	}

}
