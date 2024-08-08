package com.kostik.store.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kostik.store.domain.Cart;
import com.kostik.store.domain.Order;
import com.kostik.store.domain.User;
import com.kostik.store.domain.UserData;
import com.kostik.store.service.userData.UserDataProvider;

import jakarta.transaction.Transactional;

@Service
public class CartService {

	@Autowired
	OrderService orderService;

	@Autowired
	private UserDataProvider udp;

	@Autowired
	private ObjectMapper mapper;	
	
	@Autowired
	private HttpService httpService;

	@Transactional
	public void checkout() {
		// make orders for al
		Cart cart = getCurrentCart();
		for (var order : cart.getOrders()) {
			try {
				orderService.saveOrder(order);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		resetCart();
	}

	public void addOrderToCart(Order order) {		
		Cart cart = getCurrentCart();		
		
		cart.addOrder(order);		
		
		saveCurrentCart(cart);			
	}
	
	private void updateCartDetailsInDB(Long userId, Cart cart) {
		String cartDetails = null;
		try {
			cartDetails = mapper.writeValueAsString(cart);
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Map<String, String> data = new HashMap<>();
		data.put(UserData.USER_DETAILS, cartDetails);
		udp.save(userId, data);		
	}

	public void removeOrderFromCart(Order order) {
		Cart cart = getCurrentCart();
		
		cart.removeOrder(order);
		
		saveCurrentCart(cart);
	}

	public void resetCart() {
		Cart cart = getCurrentCart();
		
		cart = null;
		
		saveCurrentCart(cart);
	}

	public void loadCart(User user) {
		Cart cart = getCurrentCart();
		
		Map<String, String> data = udp.load(user.getId());
		String cartDetails = data.get(UserData.USER_DETAILS);

		if (cartDetails != null) {
			try {
				cart = mapper.readValue(cartDetails, Cart.class);
			} catch (JsonMappingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (JsonProcessingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		saveCurrentCart(cart);
	}
	
	public Cart getCurrentCart() {
		User user = httpService.getCurrentUserFromSession();

		Cart cart = (Cart) httpService.load(user.getId(), HttpService.Attr.CART);
		if (cart == null)
			cart = new Cart();
		
		cart.setUserId(user.getId());
		
		return cart;
	}

	public void saveCurrentCart(Cart cart) {
		Long userId = cart.getUserId();
		httpService.save(userId, HttpService.Attr.CART, cart);
		//updateCartDetailsInDB(userId, cart);
	}
}
