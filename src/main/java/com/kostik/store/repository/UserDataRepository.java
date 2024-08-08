package com.kostik.store.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.kostik.store.domain.UserData;
@Repository
public interface UserDataRepository extends JpaRepository<UserData, Long> {
	
	public UserData findByUserId(Long id);
}
