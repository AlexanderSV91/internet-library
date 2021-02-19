package com.faceit.example.service.impl.postgre;

import com.faceit.example.dto.request.postgre.UserRequest;
import com.faceit.example.dto.response.postgre.UserResponse;
import com.faceit.example.repository.postgre.UserRepository;
import com.faceit.example.service.postgre.UserService;
import com.faceit.example.tables.records.RolesRecord;
import com.faceit.example.tables.records.UsersRecord;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    //private final UserMapper userMapper;

    @Override
    public Page<UserResponse> getAllUserByUsername(UsersRecord user, Pageable pageable) {
        return null;
    }

    @Override
    public UserResponse getUserById(long id) {
        return null;
    }

    @Override
    public UserResponse addUser(UserRequest newUser) {
        return null;
    }

    @Override
    public UserResponse addUser(UserRequest newUser, Set<RolesRecord> roles) {
        return null;
    }

    @Override
    public UserResponse updateUserById(UserRequest userRequest, long id) {
        return null;
    }

    @Override
    public void deleteUserById(long id, Set<RolesRecord> roles) {

    }

    @Override
    public UserResponse findUserByUserName(String username) {
        return null;
    }

    @Override
    public UsersRecord findUser(String username) {
        return userRepository.findUser(username);
    }

    @Override
    public boolean existsUserByUserName(String username) {
        return false;
    }

    @Override
    public boolean existsByEmail(String email) {
        return false;
    }
}
