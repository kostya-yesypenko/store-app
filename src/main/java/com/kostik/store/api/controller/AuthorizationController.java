package com.kostik.store.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.kostik.store.domain.Employee;
import com.kostik.store.dto.AuthorizationRequest;
import com.kostik.store.service.AuthorizationService;
import com.kostik.store.service.EmployeeService;

import jakarta.servlet.http.HttpSession;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/auth")
public class AuthorizationController {

	@Autowired
    private EmployeeService employeeService;

    @Autowired
    private AuthorizationService authorizationService;

	@PostMapping("/login")
	public ResponseEntity<Employee> login(@RequestBody AuthorizationRequest request, HttpSession session) {
		String login = request.getLoginParam();
		String password = request.getPasswordParam();
		Employee employee = employeeService.findByLogin(login);
		if (employee != null && authorizationService.validatePasswords(password, employee.getPassword())) {
			session.setAttribute("employee", employee);
			return new ResponseEntity<>(employee, HttpStatus.OK);

		}
		return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);		
	}

//	@PostMapping("/logout")
//	public void logout(@RequestParam @NotEmpty String refreshToken) {
//		tokenService.delete(tokenService.findByRefreshToken(refreshToken));
//	}
	
}
