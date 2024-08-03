package com.kostik.store.dto;

import lombok.Data;

@Data
public class AuthorizationRequest {
    
    private String login;
    private String password;
}
