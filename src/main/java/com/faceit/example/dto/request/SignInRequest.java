package com.faceit.example.dto.request;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class SignInRequest {
    private String username;
    private String password;
}