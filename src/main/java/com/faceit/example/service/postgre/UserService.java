package com.faceit.example.service.postgre;

import com.faceit.example.dto.request.postgre.UserRequest;
import com.faceit.example.dto.response.postgre.UserResponse;
import com.faceit.example.tables.records.RolesRecord;
import com.faceit.example.tables.records.UsersRecord;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Set;

public interface UserService {

    Page<UserResponse> getAllUserByUsername(UsersRecord user, Pageable pageable);

    UserResponse getUserById(long id);

    UserResponse addUser(UserRequest newUser);

    UserResponse addUser(UserRequest newUser, Set<RolesRecord> roles);

    UserResponse updateUserById(UserRequest userRequest, long id);

    void deleteUserById(long id, Set<RolesRecord> roles);

    UserResponse findUserByUserName(String username);

    UsersRecord findUser(String username);

    boolean existsUserByUserName(String username);

    boolean existsByEmail(String email);
}
