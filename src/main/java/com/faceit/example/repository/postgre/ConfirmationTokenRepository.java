package com.faceit.example.repository.postgre;

import com.faceit.example.tables.records.ConfirmationTokensRecord;
import lombok.RequiredArgsConstructor;
import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.faceit.example.Tables.CONFIRMATION_TOKENS;

@Repository
@RequiredArgsConstructor
public class ConfirmationTokenRepository {

    private final DSLContext dslContext;

    public ConfirmationTokensRecord findByToken(String token) {
        return dslContext
                .selectFrom(CONFIRMATION_TOKENS)
                .where(CONFIRMATION_TOKENS.TOKEN.eq(token))
                .fetchAny();
    }

    public ConfirmationTokensRecord save(ConfirmationTokensRecord confirmationToken) {
        return dslContext
                .insertInto(CONFIRMATION_TOKENS)
                .set(confirmationToken)
                .returning()
                .fetchOne();
    }

    public ConfirmationTokensRecord update(ConfirmationTokensRecord confirmationToken) {
        return dslContext
                .update(CONFIRMATION_TOKENS)
                .set(confirmationToken)
                .where(CONFIRMATION_TOKENS.ID.eq(confirmationToken.getId()))
                .returning()
                .fetchOne();
    }

    public boolean existsByToken(String token) {
        return dslContext
                .fetchExists(
                        dslContext
                                .selectFrom(CONFIRMATION_TOKENS)
                                .where(CONFIRMATION_TOKENS.TOKEN.eq(token)));
    }

    public ConfirmationTokensRecord findById(long id) {
        return dslContext
                .selectFrom(CONFIRMATION_TOKENS)
                .where(CONFIRMATION_TOKENS.ID.eq(id))
                .fetchAny();
    }

    public List<ConfirmationTokensRecord> findAll() {
        return dslContext
                .selectFrom(CONFIRMATION_TOKENS)
                .fetchInto(ConfirmationTokensRecord.class);
    }
}
