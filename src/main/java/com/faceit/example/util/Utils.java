package com.faceit.example.util;

import com.faceit.example.exception.ApiException;
import com.faceit.example.tables.records.RolesRecord;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.*;

public class Utils {

    public static final String ROLE_EMPLOYEE = "ROLE_EMPLOYEE";

    public static boolean isEmployee(List<RolesRecord> roles) {
        return roles.stream().anyMatch(role -> role.getName().equals(ROLE_EMPLOYEE));
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
