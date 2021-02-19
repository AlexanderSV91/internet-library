package com.faceit.example.service.impl.postgre;

import com.faceit.example.dto.request.postgre.RoleRequest;
import com.faceit.example.repository.postgre.RoleRepository;
import com.faceit.example.service.postgre.RoleService;
import com.faceit.example.tables.records.RolesRecord;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;

    @Override
    public List<RolesRecord> getAllRole() {
        return null;
    }

    @Override
    public List<RolesRecord> getAllRoleByUsername(long userId) {
        return roleRepository.getAllRoleByUsername(userId);
    }

    @Override
    public RolesRecord getRoleById(long id) {
        return null;
    }

    @Override
    public RolesRecord addRole(RoleRequest newRole) {
        return null;
    }

    @Override
    public RolesRecord updateRoleById(RoleRequest roleRequest, long id) {
        return null;
    }

    @Override
    public void deleteRoleById(long id) {

    }

    @Override
    public RolesRecord findByName(String role) {
        return null;
    }
}
