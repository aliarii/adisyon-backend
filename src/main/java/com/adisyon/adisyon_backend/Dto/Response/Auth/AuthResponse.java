package com.adisyon.adisyon_backend.Dto.Response.Auth;

import com.adisyon.adisyon_backend.Entities.USER_ROLE;

import lombok.Data;

@Data
public class AuthResponse {

    private String jwt;
    private String message;
    private USER_ROLE role;
}
