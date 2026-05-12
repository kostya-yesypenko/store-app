package com.kostik.store.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.kostik.store.domain.Cart;
import com.kostik.store.domain.Order;
import com.kostik.store.domain.Product;
import com.kostik.store.service.CartService;
import com.kostik.store.service.HttpService;
import com.kostik.store.service.OrderService;
import com.kostik.store.service.userData.UserDataProvider;

import lombok.extern.slf4j.Slf4j;

@Controller
@CrossOrigin(origins = "http://localhost:3000", allowedHeaders = "*", allowCredentials = "true")
@RequestMapping("/api/v1")
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

	@GetMapping("/cart")
	public Cart getCart() {
		return cartService.getCurrentCart();
	}

	@PostMapping("/checkout")
	public ResponseEntity<?> checkoutCart() {
		boolean ok = cartService.checkout();
		return ok? new ResponseEntity<>("", HttpStatus.CREATED):new ResponseEntity<>("Failed to checkout cart", HttpStatus.SERVICE_UNAVAILABLE);
	}
	
	@PostMapping("/updateCartOrderQty")
	public ResponseEntity<?> updateCartOrderQty(@RequestParam Long productId, @RequestParam Long qty) {
		cartService.updateOrderFromCart(productId, qty);
	    return new ResponseEntity<>("Product qty updated", HttpStatus.OK);
	}

	@DeleteMapping("/deleteOrder")
	public Cart deleteOrderFromCart(@RequestParam Long productId) {
		Cart cart = cartService.getCurrentCart();

		Order orderToRemove = null;
		for (Order order : cart.getOrders()) {
			if (order.getProduct().getId().equals(productId)) {
				orderToRemove = order;
				break;
			}
		}

		if (orderToRemove != null) {
			cartService.removeOrderFromCart(orderToRemove);
		}

		return cartService.getCurrentCart();
	}

}
