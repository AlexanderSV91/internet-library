package com.faceit.example.service.postgre;

import com.faceit.example.tables.records.RolesRecord;

import java.util.List;

public interface RoleService {

    List<RolesRecord> getAllRole();

    List<RolesRecord> getAllRoleByUsername(long userId);

    RolesRecord getRoleById(long id);

    RolesRecord addRole(RolesRecord newRole);

    RolesRecord updateRoleById(RolesRecord updateRole, long id);

    void deleteRoleById(long id);

    RolesRecord findByName(String role);
}
