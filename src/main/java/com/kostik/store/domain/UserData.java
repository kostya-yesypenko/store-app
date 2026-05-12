package com.kostik.store.domain;


import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "userData")
public class UserData {
	public static final String USER_DETAILS = "userDetails";

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(nullable = false, length = 50, name = "userId")
	private Long userId;

	@Column(name="orderDetails")
	private String orderDetails;
	
	@Column(name="dateModified")
	private Date dateModified;
}