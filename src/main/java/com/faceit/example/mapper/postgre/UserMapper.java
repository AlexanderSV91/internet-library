package com.faceit.example.mapper.postgre;

import com.faceit.example.dto.request.postgre.UserRequest;
import com.faceit.example.dto.response.postgre.UserResponse;
import com.faceit.example.tables.records.UsersRecord;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface UserMapper {

    UserResponse userRecordToUserResponse(UsersRecord user);

    UsersRecord userResponseToUserRecord(UserResponse user);

    @Mappings({
            @Mapping(target = "id", ignore = true),
            @Mapping(target = "enabled", ignore = true)})
    UsersRecord userRequestToUserRecord(UserRequest userRequest);

    List<UserResponse> usersRecordToUsersResponse(List<UsersRecord> users);

    @Mappings({
            @Mapping(target = "enabled", ignore = true),
            @Mapping(target = "id", ignore = true)})
    UsersRecord updateUserRecordFromUserRecord(UsersRecord updateUser, @MappingTarget UsersRecord user);
}
