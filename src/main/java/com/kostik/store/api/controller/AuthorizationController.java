package com.kostik.store.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kostik.store.domain.User;
import com.kostik.store.dto.AuthorizationRequest;
import com.kostik.store.service.AuthorizationService;
import com.kostik.store.service.UserService;

import jakarta.servlet.http.HttpSession;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/auth")
public class AuthorizationController {

    @Autowired
    private UserService userService;

    @Autowired
    private AuthorizationService authorizationService;

    @PostMapping("/login")
    public ResponseEntity<User> login(@RequestBody AuthorizationRequest request, HttpSession session) {
        String login = request.getLogin();
        String password = request.getPassword();
        User user = userService.findByLogin(login);
        if (user != null && authorizationService.validatePasswords(password, user.getPassword())) {
            session.setAttribute("user", user);
            System.out.println("Employee set in session: " + user);
            return new ResponseEntity<>(user, HttpStatus.OK);
        }
        System.out.println("Invalid login attempt for login: " + login);
        return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);        
    }


//	@PostMapping("/logout")
//	public void logout(@RequestParam @NotEmpty String refreshToken) {
//		tokenService.delete(tokenService.findByRefreshToken(refreshToken));
//	}
	
}
