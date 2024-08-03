package com.kostik.store.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kostik.store.domain.User;
import com.kostik.store.service.UserService;

import jakarta.servlet.http.HttpSession;


@RestController
@CrossOrigin("http://localhost:3000")
@RequestMapping("/api/v1")
public class EmployeeController {
	@Autowired
	private UserService userService;


	@GetMapping("/employees")
	List<User> getUsers(){
		return userService.getUsers();
	}

	@GetMapping("/employee")
    ResponseEntity<User> getSessionEmployee(HttpSession session) {
        User employee = (User) session.getAttribute("employee");
        if (employee != null) {
            return new ResponseEntity<>(employee, HttpStatus.OK);
        }
        return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }
}
