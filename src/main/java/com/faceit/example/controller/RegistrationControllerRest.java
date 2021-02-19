package com.faceit.example.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = {"/api-public/v1"})
public class RegistrationControllerRest {

/*    @PostMapping("/registration")
    public void registration(@Valid @RequestBody UserRequest userRequest) {
        User user = userMapper.userRequestToUser(userRequest);
        confirmationTokenService.addConfirmationToken(user);
    }

    @GetMapping("/confirm/{token}")
    public TokenStatus confirmMail(@PathVariable String token) {
        return confirmationTokenService.findByToken(token);
    }*/
}
