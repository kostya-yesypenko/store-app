package com.kostik.store.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kostik.store.domain.User;
import com.kostik.store.repository.UserRepository;

@Service
public class UserService {
	
	@Autowired
	private UserRepository userRepository;
	
	public UserService(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

    public Optional<User> findByLogin(String login){
		return userRepository.findByLogin(login);
	}

	public List<User> getUsers() {
		return userRepository.findAll();
	}
	
	

}
