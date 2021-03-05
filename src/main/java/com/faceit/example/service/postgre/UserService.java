package com.faceit.example.service.postgre;

import com.faceit.example.dto.response.postgre.UserResponse;
import com.faceit.example.dto.LocalUser;
import com.faceit.example.model.MyUserDetails;
import com.faceit.example.tables.records.UsersRecord;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.oauth2.core.oidc.OidcIdToken;
import org.springframework.security.oauth2.core.oidc.OidcUserInfo;

import java.util.Map;

public interface UserService {

    Page<UserResponse> getAllUserByUsername(MyUserDetails user, Pageable pageable);

    UsersRecord getUserById(long id);

    UsersRecord addUser(UsersRecord newUser);

    UsersRecord addUser(MyUserDetails myUserDetails, UsersRecord newUser);

    UsersRecord updateUserById(UsersRecord updateUser, long id);

    void deleteUserById(MyUserDetails myUserDetails, long id);

    UsersRecord findUserByUserName(String username);

    boolean existsUserByUserName(String username);

    boolean existsByEmail(String email);

    LocalUser processUserRegistration(String registrationId, Map<String, Object> attributes,
                                      OidcIdToken idToken, OidcUserInfo userInfo);
}
