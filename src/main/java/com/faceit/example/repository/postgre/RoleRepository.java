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

    public List<RolesRecord> getAllRole() {
        return dslContext
                .selectFrom(ROLES)
                .fetchInto(RolesRecord.class);
    }

    public List<RolesRecord> getAllRoleByUsername(long userId) {
        return dslContext
                .select()
                .from(USERS_ROLES)
                .join(ROLES)
                .on(ROLES.ID.eq(USERS_ROLES.ROLE_ID))
                .where(USERS_ROLES.USER_ID.eq(userId))
                .fetchInto(RolesRecord.class);
    }

    public RolesRecord getRoleById(long id) {
        return dslContext
                .selectFrom(ROLES)
                .where(ROLES.ID.eq(id))
                .fetchAny();
    }

    public RolesRecord addRole(RolesRecord newRole) {
        return dslContext
                .insertInto(ROLES)
                .set(newRole)
                .returning()
                .fetchOne();
    }

    public RolesRecord updateRoleById(RolesRecord roleRequest) {
        return dslContext
                .update(ROLES)
                .set(roleRequest)
                .where(ROLES.ID.eq(roleRequest.getId()))
                .returning()
                .fetchOne();
    }

    public boolean deleteRoleById(long id) {
        return dslContext
                .deleteFrom(ROLES)
                .where(ROLES.ID.eq(id))
                .execute() == 1;
    }

    public RolesRecord findByName(String name) {
        return dslContext.
                selectFrom(ROLES)
                .where(ROLES.NAME.eq(name))
                .fetchAny();
    }
}
