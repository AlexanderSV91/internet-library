package com.faceit.example.dto.request;

import com.faceit.example.model.SocialProvider;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SignUpRequest {

    private Long userID;
    private String providerUserId;
    private String username;
    private String email;
    private String firstName;
    private String lastName;
    private SocialProvider socialProvider;
    private String password;

    public SignUpRequest(String providerUserId, String username, String email, String firstName,
                         String lastName, SocialProvider socialProvider, String password) {
        this.providerUserId = providerUserId;
        this.username = username;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.socialProvider = socialProvider;
        this.password = password;
    }
}
