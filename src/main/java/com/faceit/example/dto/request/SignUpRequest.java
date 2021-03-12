package com.faceit.example.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SignUpRequest {

    private String username;
    private String email;
    private String firstName;
    private String lastName;
    private String password;
    private int age;
}
