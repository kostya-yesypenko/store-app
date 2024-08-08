package com.kostik.store.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.kostik.store.domain.Order;
import com.kostik.store.domain.User;
import com.kostik.store.dto.OrderRequest;
import com.kostik.store.service.CartService;
import com.kostik.store.service.HttpService;
import com.kostik.store.service.OrderService;
import com.kostik.store.service.OrderService.ProductNotFoundException;
import com.kostik.store.service.OrderService.UserNotFoundException;
import com.kostik.store.service.userData.UserDataProvider;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;

@Controller
@CrossOrigin(origins ="http://localhost:3000", allowedHeaders = "*", allowCredentials = "true")
@RequestMapping("/api/v1/cart")
@ResponseBody
@Slf4j
public class CartController {
	@Autowired
	private OrderService orderService;
	
	@Autowired
	private UserDataProvider udp;
	
	@Autowired
	private CartService cartService;
	
	@Autowired
	private HttpService httpService;
	
	

	@PostMapping("/checkout")
	public void checkoutCart() {
		cartService.checkout();
	}
	

}
