package com.faceit.example.controller;

import com.faceit.example.dto.LocalUser;
import com.faceit.example.dto.request.LoginRequest;
import com.faceit.example.dto.request.SignUpRequest;
import com.faceit.example.dto.response.ApiResponse;
import com.faceit.example.dto.response.JwtAuthenticationResponse;
import com.faceit.example.model.SocialProvider;
import com.faceit.example.security.jwt.TokenProvider;
import com.faceit.example.service.postgre.UserService;
import com.faceit.example.tables.records.UsersRecord;
import com.faceit.example.util.Utils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@Slf4j
@RestController
@RequestMapping("/api-public/v1/auth")
@RequiredArgsConstructor
public class AuthControllerRest {

    private final AuthenticationManager authenticationManager;
    private final UserService userService;
    private final TokenProvider tokenProvider;

    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = tokenProvider.createToken(authentication);
        LocalUser localUser = (LocalUser) authentication.getPrincipal();
        return ResponseEntity.ok(new JwtAuthenticationResponse(jwt, Utils.buildUserInfo(localUser)));
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignUpRequest signUpRequest) {
        try {
            UsersRecord usersRecord = new UsersRecord();
            usersRecord.setUsername(signUpRequest.getUsername());
            usersRecord.setEmail(signUpRequest.getEmail());
            usersRecord.setFirstName(signUpRequest.getFirstName());
            usersRecord.setLastName(signUpRequest.getLastName());
            usersRecord.setPassword(signUpRequest.getPassword());
            userService.addUser(usersRecord);
        } catch (RuntimeException e) {
            log.error("Exception Ocurred", e);
            return new ResponseEntity<>(new ApiResponse(false, "Email Address already in use!"), HttpStatus.BAD_REQUEST);
        }
        return ResponseEntity.ok().body(new ApiResponse(true, "User registered successfully"));
    }
}