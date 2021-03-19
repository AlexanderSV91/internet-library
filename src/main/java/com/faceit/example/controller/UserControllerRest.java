package com.faceit.example.controller;

import com.faceit.example.dto.LocalUser;
import com.faceit.example.dto.request.postgre.UserRequest;
import com.faceit.example.dto.response.postgre.UserResponse;
import com.faceit.example.mapper.postgre.UserMapper;
import com.faceit.example.service.postgre.UserService;
import com.faceit.example.tables.records.UsersRecord;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = {"/api/v1/user"})
public class UserControllerRest {

    private final UserService userService;
    private final UserMapper userMapper;

    @GetMapping
    public ResponseEntity<Page<UserResponse>> getAllUserByUsername(@AuthenticationPrincipal LocalUser userDetails,
                                                                   Pageable pageable) {
        Page<UserResponse> users = userService.getAllUserByUsername(userDetails, pageable);
        return ResponseEntity.status(HttpStatus.OK).body(users);
    }

    @GetMapping("/current-user")
    public ResponseEntity<UserResponse> findUserByUserName(@AuthenticationPrincipal LocalUser userDetails) {
        UserResponse userResponse = userMapper
                .userRecordToUserResponse(userService.findUserByUserName(userDetails.getUsername()));
        return ResponseEntity.status(HttpStatus.OK).body(userResponse);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> getUserById(@PathVariable long id) {
        UserResponse userResponse = userMapper.userRecordToUserResponse(userService.getUserById(id));
        return ResponseEntity.status(HttpStatus.OK).body(userResponse);
    }

    @PostMapping
    public ResponseEntity<UserResponse> addUser(@AuthenticationPrincipal LocalUser userDetails,
                                                @Valid @RequestBody UserRequest userRequest) {
        UsersRecord userRecord = userMapper.userRequestToUserRecord(userRequest);
        UserResponse userResponse = userMapper
                .userRecordToUserResponse(userService.addUser(userDetails, userRecord));
        return ResponseEntity.status(HttpStatus.OK).body(userResponse);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUserById(@AuthenticationPrincipal LocalUser userDetails,
                                               @PathVariable long id) {
        userService.deleteUserById(userDetails, id);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserResponse> updateUserById(@Valid @RequestBody UserRequest userRequest,
                                                       @PathVariable Long id) {
        UsersRecord userRecord = userMapper.userRequestToUserRecord(userRequest);
        UserResponse userResponse = userMapper
                .userRecordToUserResponse(userService.updateUserById(userRecord, id));
        return ResponseEntity.status(HttpStatus.OK).body(userResponse);
    }
}
