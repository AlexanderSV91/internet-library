package com.faceit.example.service.impl;

import com.faceit.example.dto.LocalUser;
import com.faceit.example.model.MyUserDetails;
import com.faceit.example.service.postgre.RoleService;
import com.faceit.example.service.postgre.UserService;
import com.faceit.example.tables.records.RolesRecord;
import com.faceit.example.tables.records.UsersRecord;
import com.faceit.example.util.Utils;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserService userService;
    private final RoleService roleService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UsersRecord user = userService.findUserByUserName(username);
        if (user == null) {
            throw new RuntimeException("exception.couldNotFindUser");
        }
        List<RolesRecord> usersRoles = roleService.getAllRoleByUsername(user.getId());
        return new MyUserDetails(user, usersRoles);
    }

    public LocalUser loadUserById(Long id) {
        UsersRecord user = userService.getUserById(id);
        return createLocalUser(user);
    }

    private LocalUser createLocalUser(UsersRecord user) {
        List<RolesRecord> rolesRecords = roleService.getAllRoleByUsername(user.getId());
        return new LocalUser(
                user.getEmail(),
                user.getPassword(),
                user.getEnabled(),
                true,
                true,
                true,
                Utils.buildSimpleGrantedAuthorities(rolesRecords),
                user,
                rolesRecords);
    }
}
