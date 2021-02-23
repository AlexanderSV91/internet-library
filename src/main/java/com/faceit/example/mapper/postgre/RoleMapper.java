package com.faceit.example.mapper.postgre;

import com.faceit.example.dto.request.postgre.RoleRequest;
import com.faceit.example.dto.response.postgre.RoleResponse;
import com.faceit.example.tables.records.RolesRecord;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public interface RoleMapper {

    RoleResponse roleRecordToRoleResponse(RolesRecord role);

    @Mapping(target = "id", ignore = true)
    RolesRecord roleRequestToRoleRecord(RoleRequest roleRequest);

    List<RoleResponse> rolesRecordToRolesResponse(List<RolesRecord> roles);

    @Mapping(target = "id", ignore = true)
    RolesRecord updateRoleRecordFromRoleRecord(RolesRecord updateRole, @MappingTarget RolesRecord role);
}
