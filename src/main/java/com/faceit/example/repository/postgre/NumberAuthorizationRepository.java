package com.faceit.example.repository.postgre;

import com.faceit.example.tables.records.NumberAuthorizationsRecord;
import lombok.RequiredArgsConstructor;
import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;

import static com.faceit.example.Tables.NUMBER_AUTHORIZATIONS;

@Repository
@RequiredArgsConstructor
public class NumberAuthorizationRepository {

    private final DSLContext dslContext;

    public NumberAuthorizationsRecord getById(long id) {
        return dslContext
                .selectFrom(NUMBER_AUTHORIZATIONS)
                .where(NUMBER_AUTHORIZATIONS.ID.eq(id))
                .fetchAny();
    }

    public NumberAuthorizationsRecord save(NumberAuthorizationsRecord numberAuthorization) {
        return dslContext
                .insertInto(NUMBER_AUTHORIZATIONS)
                .set(numberAuthorization)
                .returning()
                .fetchOne();
    }

    public NumberAuthorizationsRecord updateById(NumberAuthorizationsRecord numberAuthorization) {
        return dslContext
                .update(NUMBER_AUTHORIZATIONS)
                .set(numberAuthorization)
                .where(NUMBER_AUTHORIZATIONS.ID.eq(numberAuthorization.getId()))
                .returning()
                .fetchOne();
    }
}
