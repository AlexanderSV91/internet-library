package com.faceit.example.service.postgre;

import com.faceit.example.dto.request.postgre.RoleRequest;
import com.faceit.example.tables.records.RolesRecord;

import java.util.List;

public interface RoleService {

    List<RolesRecord> getAllRole();

    List<RolesRecord> getAllRoleByUsername(long userId);

    RolesRecord getRoleById(long id);

    RolesRecord addRole(RoleRequest newRole);

    RolesRecord updateRoleById(RoleRequest roleRequest, long id);

    void deleteRoleById(long id);

    RolesRecord findByName(String role);
}
