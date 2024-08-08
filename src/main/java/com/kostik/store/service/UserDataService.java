package com.kostik.store.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kostik.store.domain.UserData;
import com.kostik.store.repository.UserDataRepository;

@Service
public class UserDataService {
	
	@Autowired
	UserDataRepository userDataRepository;
	

	public Optional<UserData> findById(Long id) {
		return userDataRepository.findById(id);
	}


	public UserData findByUserId(Long id) {
		return userDataRepository.findByUserId(id);
	}


	public void save(UserData userData) {
		userDataRepository.save(userData);
	}
}
