package com.faceit.example.dto.response;

import com.faceit.example.dto.UserInfo;
import lombok.Value;

@Value
public class JwtAuthenticationResponse {
    String accessToken;
    UserInfo user;
}