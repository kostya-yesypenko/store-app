package com.kostik.store.api.controller;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.kostik.store.domain.User;
import com.kostik.store.dto.AuthorizationRequest;
import com.kostik.store.service.AuthorizationService;
import com.kostik.store.service.CartService;
import com.kostik.store.service.HttpService;
import com.kostik.store.service.UserService;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
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
        User user = userService.findByLogin(login);

        if (user != null && authorizationService.validatePasswords(password, user.getPassword())) {
        	httpService.printCurrentSession();
        	
        	httpService.saveCurrentUserInSession(user);            
            
            cartService.loadCart(user);
            System.out.println("User set in session: " + user);
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
