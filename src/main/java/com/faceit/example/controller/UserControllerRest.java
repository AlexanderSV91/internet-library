package com.faceit.example.controller;

import com.faceit.example.service.postgre.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = {"/api/v1/user"})
public class UserControllerRest {

    private final UserService userService;
}
