package com.faceit.example.service.impl.postgre;

import com.faceit.example.dto.response.postgre.UserResponse;
import com.faceit.example.exception.ApiRequestException;
import com.faceit.example.exception.ResourceAlreadyExists;
import com.faceit.example.mapper.postgre.UserMapper;
import com.faceit.example.model.MyUserDetails;
import com.faceit.example.repository.postgre.UserRepository;
import com.faceit.example.service.postgre.RoleService;
import com.faceit.example.service.postgre.UserService;
import com.faceit.example.tables.records.NumberAuthorizationsRecord;
import com.faceit.example.tables.records.RolesRecord;
import com.faceit.example.tables.records.UsersRecord;
import com.faceit.example.util.Utils;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final RoleService roleService;

    @Override
    public Page<UserResponse> getAllUserByUsername(MyUserDetails user, Pageable pageable) {
        boolean isEmployee = Utils.isEmployee(user.getRolesRecords());
        List<UsersRecord> users;
        long totalElements;
        if (isEmployee) {
            users = userRepository.findAll(pageable);
            totalElements = userRepository.findCountAllUsers();
        } else {
            users = Collections.singletonList(userRepository.getUserById(user.getUser().getId()));
            totalElements = 1L;
        }
        return new PageImpl<>(userMapper.usersRecordToUsersResponse(users), pageable, totalElements);
    }

    @Override
    public UsersRecord getUserById(long id) {
        return userRepository.getUserById(id);
    }

    @Override
    public UsersRecord addUser(UsersRecord newUser) {
        preparingUser(newUser);
        checkUsername(newUser.getUsername());
        checkEmail(newUser.getEmail());

        RolesRecord userRole = roleService.findByName("ROLE_USER");
        NumberAuthorizationsRecord numberAuthorizationsRecord = preparingNumberAuthorization();

        return userRepository.save(newUser, numberAuthorizationsRecord, userRole.getId());
    }

    @Override
    public UsersRecord addUser(MyUserDetails myUserDetails, UsersRecord newUser) {
        boolean isEmployee = Utils.isEmployee(myUserDetails.getRolesRecords());
        if (isEmployee) {
            return addUser(newUser);
        } else {
            throw new ApiRequestException("exception.userNotAdd");
        }
    }

    @Override
    public UsersRecord updateUserById(UsersRecord updateUser, long id) {
        UsersRecord userRecord = userRepository.getUserById(id);
        if (updateUser.getPassword() != null && updateUser.getPassword().length() < 30) {
            updateUser.setPassword(bCryptPasswordEncoder.encode(updateUser.getPassword()));
        }
        if (!updateUser.getUsername().equals(userRecord.getUsername())) {
            checkUsername(updateUser.getUsername());
        }
        return userRepository
                .updateUser(userMapper.updateUserRecordFromUserRecord(updateUser, userRecord));
    }

    @Override
    public void deleteUserById(MyUserDetails myUserDetails, long id) {
        boolean isEmployee = Utils.isEmployee(myUserDetails.getRolesRecords());
        if (isEmployee) {
            userRepository.deleteById(id);
        } else {
            throw new ApiRequestException("exception.userNotDelete");
        }
    }

    @Override
    public UsersRecord findUserByUserName(String username) {
        return userRepository.findUser(username);
    }

    @Override
    public boolean existsUserByUserName(String username) {
        return userRepository.existsUserByUserName(username);
    }

    @Override
    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    private void checkUsername(String username) {
        boolean checkUser = existsUserByUserName(username);
        if (checkUser) {
            throw new ResourceAlreadyExists("username", "exception.usernameExists");
        }
    }

    private void checkEmail(String email) {
        boolean checkEmail = existsByEmail(email);
        if (checkEmail) {
            throw new ResourceAlreadyExists("email", "exception.emailExists");
        }
    }

    private NumberAuthorizationsRecord preparingNumberAuthorization() {
        NumberAuthorizationsRecord numberAuthorization = new NumberAuthorizationsRecord();
        numberAuthorization.setQuantity(3);
        numberAuthorization.setLastAuthorizationDate(LocalDateTime.now());
        return numberAuthorization;
    }

    private void preparingUser(UsersRecord newUser) {
        newUser.setPassword(bCryptPasswordEncoder.encode(newUser.getPassword()));
        newUser.setEnabled(false);
    }
}
