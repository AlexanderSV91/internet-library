package com.faceit.example.repository.postgre;

import com.faceit.example.tables.records.UsersRecord;
import lombok.RequiredArgsConstructor;
import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;

import static com.faceit.example.Tables.USERS;

@Repository
@RequiredArgsConstructor
public class UserRepository {

    private final DSLContext dslContext;

    public UsersRecord findUser(String username) {
        return dslContext
                .selectFrom(USERS)
                .where(USERS.USERNAME.eq(username))
                .fetchOne();
    }
}
