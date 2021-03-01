package com.faceit.example.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping(value = {"/api/v1/oauth2"})
@RequiredArgsConstructor
public class OAuth2ControllerRest {

    @GetMapping("/user")
    public Map<String, Object> user(@AuthenticationPrincipal OAuth2User principal) {
        Map<String, Object> stringObjectMap = Collections.singletonMap("name", principal.getAttribute("name"));
        stringObjectMap.forEach((s, o) -> log.error(s + " " + o));
        principal.getAttributes().forEach((s, o) -> log.error(s + " " + o));
        return stringObjectMap;
    }
}
