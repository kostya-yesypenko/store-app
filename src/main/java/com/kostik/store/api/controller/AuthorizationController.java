package com.kostik.store.api.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.kostik.store.domain.User;
import com.kostik.store.dto.AuthorizationRequest;
import com.kostik.store.service.AuthorizationService;
import com.kostik.store.service.CartService;
import com.kostik.store.service.HttpService;
import com.kostik.store.service.UserService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

@Controller
@CrossOrigin(origins ="http://localhost:3000", allowedHeaders = "*", allowCredentials = "true")
@RequestMapping("/auth")
@ResponseBody
@Slf4j
public class AuthorizationController {

    @Autowired
    private UserService userService;
    
    @Autowired
    private CartService cartService;

    @Autowired
    private AuthorizationService authorizationService;
    
    @Autowired
    private HttpService httpService;

    @PostMapping("/login")
    public ResponseEntity<User> login(HttpServletRequest httpRequest, HttpServletResponse httpResponse, @RequestBody AuthorizationRequest request) {
        String login = request.getLogin();
        String password = request.getPassword();
        Optional<User> user = userService.findByLogin(login);

        // Check if user is present and the passwords match
        if (user.isPresent() && authorizationService.validatePasswords(password, user.get().getPassword())) {
            // Print current session for debugging
            httpService.printCurrentSession();

            // Save the current user in session
            httpService.saveCurrentUserInSession(user.get());            

            // Load the user's cart
            cartService.loadCart(user.get());

            // Log and return the user
            System.out.println("User set in session: " + user);
            return new ResponseEntity<>(user.get(), HttpStatus.OK);
        }

        // Log and return a not found response if login is invalid
        System.out.println("Invalid login attempt for login: " + login);
        return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);        
    }



	@PostMapping("/logout")
	public void logout(User user) {
		
	}
	
}
