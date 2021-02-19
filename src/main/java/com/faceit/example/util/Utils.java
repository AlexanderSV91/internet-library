package com.faceit.example.util;

import com.faceit.example.tables.records.RolesRecord;
import org.springframework.context.i18n.LocaleContextHolder;

import java.util.*;

public class Utils {

    public static final String ROLE_EMPLOYEE = "ROLE_EMPLOYEE";

    public static boolean isEmployee(Set<RolesRecord> roles) {
        return roles.stream().anyMatch(role -> role.getName().equals(ROLE_EMPLOYEE));
    }

    public static Map<String, String> buildMap(String key, String value) {
        return new HashMap<>() {{
            put(key, value);
        }};
    }

    public static <T> T getDataFromTypeOptional(Optional<T> optional) {
        return optional.orElseThrow(() -> new RuntimeException("exception.notFound"));
    }

    public static String getMessageForLocale(String messageKey) {
        Locale locale = new Locale(LocaleContextHolder.getLocale().toString());
        return ResourceBundle.getBundle("messages", locale).getString(messageKey);
    }
}
