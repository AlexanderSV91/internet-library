package com.faceit.example.controller;

import com.faceit.example.dto.request.postgre.UserRequest;
import com.faceit.example.mapper.postgre.UserMapper;
import com.faceit.example.model.enumeration.TokenStatus;
import com.faceit.example.service.ConfirmationTokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = {"/api-public/v1"})
public class RegistrationControllerRest {

    private final ConfirmationTokenService confirmationTokenService;
    private final UserMapper userMapper;

    @PostMapping("/registration")
    public ResponseEntity<Void> registration(@Valid @RequestBody UserRequest userRequest) {
        confirmationTokenService.addConfirmationToken(userMapper.userRequestToUserRecord(userRequest));
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @GetMapping("/confirm/{token}")
    public ResponseEntity<TokenStatus> confirmMail(@PathVariable String token) {
        TokenStatus tokenStatus = confirmationTokenService.findByToken(token);
        return ResponseEntity.status(HttpStatus.OK).body(tokenStatus);
    }
}
