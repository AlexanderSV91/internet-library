package com.faceit.example.dto.request.postgre;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserRequest {

    private String userName;
    private String password;
    private String firstName;
    private String lastName;
    private String email;
    private int age;
}
