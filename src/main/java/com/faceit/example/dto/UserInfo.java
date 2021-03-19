package com.faceit.example.dto;

import lombok.Value;

import java.util.List;

@Value
public class UserInfo {
    String id;
    String username;
    String email;
    List<String> roles;
}