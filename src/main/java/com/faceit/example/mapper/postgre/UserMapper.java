package com.faceit.example.mapper.postgre;

import com.faceit.example.dto.request.postgre.UserRequest;
import com.faceit.example.dto.response.postgre.UserResponse;
import com.faceit.example.tables.records.UsersRecord;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserResponse userRecordToUserResponse(UsersRecord user);

    UsersRecord userResponseToUserRecord(UserResponse user);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "enabled", ignore = true)
    UsersRecord userRequestToUserRecord(UserRequest userRequest);

    List<UserResponse> usersRecordToUsersResponse(List<UsersRecord> users);

    @Mapping(target = "enabled", ignore = true)
    @Mapping(target = "id", ignore = true)
    UsersRecord updateUserRecordFromUserRecord(UsersRecord updateUser, @MappingTarget UsersRecord user);
}
