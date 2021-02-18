package com.faceit.example.util;

import com.faceit.example.model.postgre.RoleModel;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

public class Utils {

    public static final String ROLE_EMPLOYEE = "ROLE_EMPLOYEE";

    public static boolean isEmployee(Set<RoleModel> roles) {
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
}
