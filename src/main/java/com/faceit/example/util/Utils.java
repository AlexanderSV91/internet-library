package com.faceit.example.util;

import com.faceit.example.dto.LocalUser;
import com.faceit.example.dto.UserInfo;
import com.faceit.example.exception.ApiException;
import com.faceit.example.model.SocialProvider;
import com.faceit.example.tables.records.RolesRecord;
import com.faceit.example.tables.records.UsersRecord;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

public class Utils {

    public static final String ROLE_EMPLOYEE = "ROLE_EMPLOYEE";

    public static boolean isEmployee(List<RolesRecord> roles) {
        return roles.stream().anyMatch(role -> role.getName().equals(ROLE_EMPLOYEE));
    }

    public static List<SimpleGrantedAuthority> buildSimpleGrantedAuthorities(List<RolesRecord> roles) {
        return roles.stream()
                .map(role -> new SimpleGrantedAuthority(role.getName()))
                .collect(Collectors.toList());
    }

    public static SocialProvider toSocialProvider(String providerId) {
        for (SocialProvider socialProvider : SocialProvider.values()) {
            if (socialProvider.getProviderType().equals(providerId)) {
                return socialProvider;
            }
        }
        return SocialProvider.LOCAL;
    }

    public static UserInfo buildUserInfo(LocalUser localUser) {
        List<String> roles = localUser.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());
        UsersRecord user = localUser.getUser();
        return new UserInfo(user.getId().toString(), user.getUsername(), user.getEmail(), roles);
    }

    public static Map<String, String> buildMap(String key, String value) {
        return new HashMap<>() {{
            put(key, value);
        }};
    }

    public static ApiException buildApiException(String simpleName,
                                                 Map<String, String> message,
                                                 HttpStatus badRequest,
                                                 LocalDateTime now) {
        return new ApiException(simpleName, message, badRequest, now);
    }

    public static String getMessageForLocale(String messageKey) {
        Locale locale = new Locale(LocaleContextHolder.getLocale().toString());
        return ResourceBundle.getBundle("messages", locale).getString(messageKey);
    }
}
