package com.kostik.store.service;


import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.SessionScope;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kostik.store.domain.Cart;
import com.kostik.store.domain.User;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;

@Service
@SessionScope
@Slf4j
public class HttpService {
	public enum Attr {
		CART, USER
	};

	@Autowired
	HttpServletRequest request;

	@Autowired
	HttpServletResponse response;
	
	@Autowired
	HttpSession httpSession;

	@Autowired
	private ObjectMapper om;

	public Object load(Long userId, Attr attr) {
		Cookie[] cookies = request.getCookies();
		String val = null;
		Class<?> clazz = null;

		if (cookies != null) {
			for (Cookie cookie : cookies) {
				if (getCookieName(userId, attr).equalsIgnoreCase(cookie.getName())) {
					val = cookie.getValue();
				}
			}
		}

		if (val != null) {
			try {
				switch (attr) {
				case CART:
					clazz = Cart.class;
					break;
				case USER:
					clazz = User.class;
					break;
				default:
					break;

				}

				return om.readValue(URLDecoder.decode(val, "UTF-8"), clazz);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		return null;
	}

	public void save(Long userId, Attr attr, Object value) {
		String val = null;
		try {
			val = URLEncoder.encode(om.writeValueAsString(value), "UTF-8");
		} catch (JsonProcessingException | UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		Cookie cookie = new Cookie(getCookieName(userId, attr), val);
		// Set cookie attributes
		cookie.setMaxAge(24 * 60 * 60); // 1 day
		cookie.setHttpOnly(true);
		cookie.setSecure(false);
		cookie.setDomain("localhost");
		cookie.setPath("/");

		// Add cookie to response
		response.addCookie(cookie);
	}

	private String getCookieName(Long userId, Attr attr) {
		return attr + "_" + userId;
	}
	
	public User getCurrentUserFromSession() {
		return (User) httpSession.getAttribute(Attr.USER.name());
	}
	
	public void saveCurrentUserInSession(User user) {
		httpSession.setAttribute(Attr.USER.name(), user);
	}
	
	public void deleteCurrentUserfromSession(User user) {
		httpSession.setAttribute(Attr.USER.name(), null);
	}
	
	public void printCurrentSession() {
		log.info("Current Session ID = {}", httpSession.getId());
	}
}
