package com.kostik.store.service.userData;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import com.kostik.store.domain.UserData;
import com.kostik.store.service.UserDataService;

@Component
@Primary
public class DbUserDataProvider implements UserDataProvider {
	
	@Autowired
	private UserDataService userDataService;

	@Override
	public void save(Long userId, Map<String, String> data) {
		UserData userData = new UserData();
		
		UserData dbUserData = userDataService.findByUserId(userId);
		
		if (dbUserData !=null) {
			BeanUtils.copyProperties(dbUserData, userData);
		} else {
			userData.setId(userId);
		}
		
		userData.setOrderDetails(data.get(UserData.USER_DETAILS));
		userData.setDateModified(new Date());
		userDataService.save(userData);
	}

	@Override
	public Map<String, String> load(Long userId) {
		UserData userData = userDataService.findByUserId(userId);
		Map<String, String> data = new HashMap<>();
		data.put(UserData.USER_DETAILS, userData!=null ? userData.getOrderDetails() : null);
		return data;
	}
	

}
