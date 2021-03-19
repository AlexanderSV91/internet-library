package com.faceit.example.security.oauth2.user;

import com.faceit.example.exception.OAuth2AuthenticationProcessingException;

import java.util.Map;

import static com.faceit.example.model.SocialProvider.GITHUB;
import static com.faceit.example.model.SocialProvider.GOOGLE;

public class OAuth2UserInfoFactory {

    public static OAuth2UserInfo getOAuth2UserInfo(String registrationId,
                                                   Map<String, Object> attributes) {
        if (GOOGLE.getProviderType().equalsIgnoreCase(registrationId)) {
            return new GoogleOAuth2UserInfo(attributes);
        } else if (GITHUB.getProviderType().equalsIgnoreCase(registrationId)) {
            return new GithubOAuth2UserInfo(attributes);
        } else {
            throw new OAuth2AuthenticationProcessingException(
                    "Sorry! Login with " + registrationId + " is not supported yet.");
        }
    }
}