package com.kostik.store.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.kostik.store.domain.User;
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
	
    Optional<User> findByLogin(String login);
	
	public User findByEmail(String email);
	
}
