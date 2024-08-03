package com.kostik.store.service.userData;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@Component
@Scope("session") 
public class SessionUserDataProvider implements UserDataProvider {

	@Autowired
	HttpSession session;

    @Override
    public void save(String id, Map<String, String> data) {
        session.setAttribute(UserDataProvider.SETTINGS_ID, data);
    }

    @Override
    public Map<String, String> load(String id) {
        Map<String, String> settings = (Map<String, String>) session.getAttribute(UserDataProvider.SETTINGS_ID);
        if (settings == null) {
            settings = new HashMap<>();
        }
        return settings;
    }


}
