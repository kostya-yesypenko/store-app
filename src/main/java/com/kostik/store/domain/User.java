package com.kostik.store.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "user")
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false, length = 50)
	private String name;
	
	@Column(nullable = false, length = 50)
	private String email;
	
	@Column(nullable = false, length = 50, unique=true)
	private String login;
	
	@Column(nullable = false, length = 50)
	private String password;
	
	@ManyToOne
    @JoinColumn(name = "role_id", nullable = false)
    private Role role;
}