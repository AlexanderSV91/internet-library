package com.faceit.example.repository.postgre;

import com.faceit.example.tables.records.NumberAuthorizationsRecord;
import com.faceit.example.tables.records.UsersRecord;
import com.faceit.example.tables.records.UsersRolesRecord;
import lombok.RequiredArgsConstructor;
import org.jooq.DSLContext;
import org.jooq.SortField;
import org.jooq.TableField;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

import static com.faceit.example.Tables.*;

@Repository
@RequiredArgsConstructor
public class UserRepository {

    private final DSLContext dslContext;

    public List<UsersRecord> findAll(Pageable pageable) {
        return dslContext
                .selectFrom(USERS)
                .orderBy(getSortFields(pageable.getSort()))
                .limit(pageable.getPageSize())
                .offset(pageable.getOffset())
                .fetch();
    }

    public UsersRecord findUser(String username) {
        return dslContext
                .selectFrom(USERS)
                .where(USERS.USERNAME.eq(username))
                .fetchOne();
    }

    public UsersRecord getUserById(long id) {
        return dslContext
                .selectFrom(USERS)
                .where(USERS.ID.eq(id))
                .fetchAny();
    }

    public UsersRecord save(UsersRecord newUser,
                            NumberAuthorizationsRecord numberAuthorization,
                            long roleId) {
        return dslContext.transactionResult(configuration -> {
            NumberAuthorizationsRecord numberAuthorizationsRecord = dslContext
                    .insertInto(NUMBER_AUTHORIZATIONS)
                    .set(numberAuthorization)
                    .returning()
                    .fetchOne();
            long numberAuthorizationId = Objects.requireNonNull(numberAuthorizationsRecord).getId();

            newUser.setNumberAuthorizationId(numberAuthorizationId);
            UsersRecord usersRecord = dslContext
                    .insertInto(USERS)
                    .set(newUser)
                    .returning()
                    .fetchOne();
            long userId = Objects.requireNonNull(usersRecord).getId();

            UsersRolesRecord usersRolesRecord = dslContext
                    .insertInto(USERS_ROLES)
                    .set(USERS_ROLES.USER_ID, userId)
                    .set(USERS_ROLES.ROLE_ID, roleId)
                    .returning()
                    .fetchOne();
            Objects.requireNonNull(usersRolesRecord);

            return usersRecord;
        });
    }

    public UsersRecord updateUser(UsersRecord userRecord) {
        return dslContext
                .update(USERS)
                .set(userRecord)
                .where(USERS.ID.eq(userRecord.getId()))
                .returning()
                .fetchOne();
    }

    public boolean existsUserByUserName(String username) {
        return dslContext
                .fetchExists(
                        dslContext
                                .selectFrom(USERS)
                                .where(USERS.USERNAME.eq(username)));
    }

    public boolean existsByEmail(String email) {
        return dslContext
                .fetchExists(
                        dslContext
                                .selectFrom(USERS)
                                .where(USERS.EMAIL.eq(email)));
    }

    public void deleteById(long id) {
        dslContext
                .deleteFrom(USERS)
                .where(USERS.ID.eq(id))
                .execute();
    }

    public long findCountAllUsers() {
        return dslContext.fetchCount(dslContext.selectFrom(USERS));
    }

    private Collection<SortField<?>> getSortFields(Sort sortSpecification) {
        Collection<SortField<?>> querySortFields = new ArrayList<>();
        if (sortSpecification == null) {
            return querySortFields;
        }

        for (Sort.Order specifiedField : sortSpecification) {
            String sortFieldName = specifiedField.getProperty().toUpperCase();
            Sort.Direction sortDirection = specifiedField.getDirection();

            TableField tableField = getTableField(sortFieldName);
            SortField<?> querySortField = convertTableFieldToSortField(tableField, sortDirection);
            querySortFields.add(querySortField);
        }

        return querySortFields;
    }

    private TableField getTableField(String sortFieldName) {
        TableField sortField;
        try {
            Field tableField = USERS.getClass().getDeclaredField(sortFieldName);
            sortField = (TableField) tableField.get(USERS);
        } catch (NoSuchFieldException | IllegalAccessException ex) {
            String errorMessage = "Could not find table field: {}" + sortFieldName;
            throw new InvalidDataAccessApiUsageException(errorMessage, ex);
        }

        return sortField;
    }

    private SortField<?> convertTableFieldToSortField(TableField tableField,
                                                      Sort.Direction sortDirection) {
        if (Sort.Direction.ASC == sortDirection) {
            return tableField.asc();
        } else {
            return tableField.desc();
        }
    }
}
