package com.kostik.store.service.userData;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
@Scope("session") 
public class CookieUserDataProvider implements UserDataProvider {
	
	@Autowired
	HttpServletRequest request;

	@Autowired
	HttpServletResponse response;
	
	@Override
	public void save(String id, Map<String, String> data) {
		String value = UserDataUtils.mapToString(data);
		Cookie cookie = new Cookie(UserDataProvider.SETTINGS_ID, value);
        // Set cookie attributes
        cookie.setMaxAge(24 * 60 * 60); // 1 day
        cookie.setHttpOnly(true);
        cookie.setSecure(true);
        cookie.setPath("/");
        
        // Add cookie to response
        response.addCookie(cookie);
        
	}

	@Override
    public Map<String, String> load(String id) {
        Cookie[] cookies = request.getCookies();
        Map<String, String> responseMap = new HashMap<>();
        
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                // Convert each cookie's value to a map
                Map<String, String> cookieMap = UserDataUtils.stringToMap(cookie.getValue());
                // Merge the cookie map into the response map
                responseMap.putAll(cookieMap);
            }
        }
        
        return responseMap;
    }
	
}
