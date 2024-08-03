package com.kostik.store.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kostik.store.domain.User;
import com.kostik.store.repository.UserRepository;

@Service
public class UserService {
	
	@Autowired
	private UserRepository UserRepository;
	
	public UserService(UserRepository employeeRepository) {
		this.UserRepository = employeeRepository;
	}

	public User findByLogin(String login) {
		return UserRepository.findByLogin(login);
	}

	public List<User> getUsers() {
		return UserRepository.findAll();
	}
	
	

}
