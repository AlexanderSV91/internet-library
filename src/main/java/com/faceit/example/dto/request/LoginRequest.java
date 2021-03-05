package com.faceit.example.dto.request;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class LoginRequest {
    private String email;
    private String password;
}