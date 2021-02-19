package com.faceit.example.dto.response.postgre;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserResponse {

    private long id;
    private String userName;
    private String firstName;
    private String lastName;
    private String email;
    private int age;
}
