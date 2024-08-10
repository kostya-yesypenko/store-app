package com.kostik.store.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kostik.store.domain.Cart;
import com.kostik.store.domain.Order;
import com.kostik.store.domain.Product;
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

	@Autowired
	private ProductService productService;

	@Transactional
	private boolean doCheckout() throws Exception {
		// make orders for al
		Cart cart = getCurrentCart();
		for (var order : cart.getOrders()) {
			orderService.saveOrder(order);
		}
		resetCart();
		return true;
	}

	public boolean checkout() {
		boolean result = false;
		try {
			result = doCheckout();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}

	public void addOrderToCart(Order order) {
		Cart cart = getCurrentCart();

		cart.addOrder(order);

		saveCurrentCart(cart);
	}

	public void updateOrderFromCart(Long productId, Long qty) {
		Cart cart = getCurrentCart();
		for (Order o : cart.getOrders()) {
			if (o.getProduct().getId().equals(productId)) {
				// Adjust the quantity only if it is positive and would not reduce below 1
				if (o.getQty() + qty > 0) {
					o.addQuantity(qty);
					saveCurrentCart(cart);
					Product product = o.getProduct();
					product.setQty(product.getQty() - o.getQty());
					productService.save(product);
					break;
				}
			}
		}
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

		Product product = productService.getProductById(order.getProduct().getId());
		product.setQty(product.getQty() + order.getQty());
		productService.save(product);

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

		if (user == null) {
			throw new RuntimeException("User not found in session");
		}

		Cart cart = (Cart) httpService.load(user.getId(), HttpService.Attr.CART);
		if (cart == null) {
			cart = new Cart();
		}

		cart.setUserId(user.getId());

		return cart;
	}

	public void saveCurrentCart(Cart cart) {

		Long userId = httpService.getCurrentUserFromSession().getId();
		httpService.save(userId, HttpService.Attr.CART, cart);
		// updateCartDetailsInDB(userId, cart);
	}
}
