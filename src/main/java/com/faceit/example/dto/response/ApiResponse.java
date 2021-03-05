package com.faceit.example.dto.response;

import lombok.Value;

@Value
public class ApiResponse {
    Boolean success;
    String message;
}