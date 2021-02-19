package com.faceit.example.model;

import com.faceit.example.tables.records.RolesRecord;
import com.faceit.example.tables.records.UsersRecord;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class MyUserDetails implements UserDetails {

    private final UsersRecord user;
    private final List<RolesRecord> rolesRecords;

    public MyUserDetails(UsersRecord user, List<RolesRecord> rolesRecords) {
        this.user = user;
        this.rolesRecords = rolesRecords;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return rolesRecords
                .stream()
                .map(rolesRecord -> new SimpleGrantedAuthority(rolesRecord.getName()))
                .distinct()
                .collect(Collectors.toList());
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return user.getEnabled();
    }

    public UsersRecord getUser() {
        return user;
    }

    public List<RolesRecord> getRolesRecords() {
        return rolesRecords;
    }
}
