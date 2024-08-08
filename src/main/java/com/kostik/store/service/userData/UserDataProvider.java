package com.kostik.store.service.userData;

import java.util.Map;

public interface UserDataProvider {
	public static final String SETTINGS_ID = "UserSettingsProvider";
	
	public void save(Long userId, Map<String, String> data);
	
	public Map<String, String> load(Long userId);
	
}
