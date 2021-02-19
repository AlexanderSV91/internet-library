package com.faceit.example.repository.postgre;

import com.faceit.example.tables.records.RolesRecord;
import lombok.RequiredArgsConstructor;
import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.faceit.example.Tables.ROLES;
import static com.faceit.example.Tables.USERS_ROLES;

@Repository
@RequiredArgsConstructor
public class RoleRepository {

    private final DSLContext dslContext;

    public List<RolesRecord> getAllRoleByUsername(long userId) {
        return dslContext
                .select()
                .from(USERS_ROLES)
                .join(ROLES)
                .on(ROLES.ID.eq(USERS_ROLES.ROLE_ID))
                .where(USERS_ROLES.USER_ID.eq(userId))
                .fetchInto(RolesRecord.class);
    }
}
