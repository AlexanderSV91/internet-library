package com.faceit.example.dto;

import lombok.Value;

import java.util.List;

@Value
public class UserInfo {
    String id, username, email;
    List<String> roles;
}