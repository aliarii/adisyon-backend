package com.adisyon.adisyon_backend.Dto.Request.Auth;

import lombok.Data;

@Data
public class LoginRequest {
    private String email;
    private String password;
}
