package com.faceit.example.service.impl.postgre;

import com.faceit.example.exception.ApiRequestException;
import com.faceit.example.mapper.postgre.RoleMapper;
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
    private final RoleMapper roleMapper;

    @Override
    public List<RolesRecord> getAllRole() {
        return roleRepository.getAllRole();
    }

    @Override
    public List<RolesRecord> getAllRoleByUserId(long userId) {
        return roleRepository.getAllRoleByUsername(userId);
    }

    @Override
    public RolesRecord getRoleById(long id) {
        return roleRepository.getRoleById(id);
    }

    @Override
    public RolesRecord addRole(RolesRecord newRole) {
        return roleRepository.addRole(newRole);
    }

    @Override
    public RolesRecord updateRoleById(RolesRecord updateRole, long id) {
        RolesRecord roleRecord = roleMapper.updateRoleRecordFromRoleRecord(updateRole, getRoleById(id));
        return roleRepository.updateRoleById(roleRecord);
    }

    @Override
    public void deleteRoleById(long id) {
        boolean isDeleted = roleRepository.deleteRoleById(id);
        if (!isDeleted) {
            throw new ApiRequestException("role is not deleted");
        }
    }

    @Override
    public RolesRecord findByName(String role) {
        return roleRepository.findByName(role);
    }
}
