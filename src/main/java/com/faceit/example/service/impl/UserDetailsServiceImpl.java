package com.faceit.example.service.impl;


import com.faceit.example.model.MyUserDetails;
import com.faceit.example.service.postgre.RoleService;
import com.faceit.example.service.postgre.UserService;
import com.faceit.example.tables.records.RolesRecord;
import com.faceit.example.tables.records.UsersRecord;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserService userService;
    @Autowired
    private RoleService roleService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UsersRecord user = userService.findUser(username);
        if (user == null) {
            throw new RuntimeException("exception.couldNotFindUser");
        }
        List<RolesRecord> usersRoles = roleService.getAllRoleByUsername(user.getId());
        return new MyUserDetails(user, usersRoles);
    }
}
