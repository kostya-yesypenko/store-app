package com.kostik.store.service.userData;

import java.util.Map;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public interface UserDataProvider {
	public static final String SETTINGS_ID = "UserSettingsProvider";
	
	public void save(String id, Map<String, String> data);
	
	public Map<String, String> load(String email);
	
}
