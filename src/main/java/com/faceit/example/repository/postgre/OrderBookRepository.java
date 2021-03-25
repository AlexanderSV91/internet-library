package com.faceit.example.repository.postgre;

import com.faceit.example.tables.records.OrderBooksRecord;
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

import static com.faceit.example.Tables.*;

@Repository
@RequiredArgsConstructor
public class OrderBookRepository {

    private final DSLContext dslContext;

    public List<OrderBooksRecord> getPagingOrderBook(Pageable pageable) {
        return dslContext
                .selectFrom(ORDER_BOOKS)
                .orderBy(getSortFields(pageable.getSort()))
                .limit(pageable.getPageSize())
                .offset(pageable.getOffset())
                .fetchInto(OrderBooksRecord.class);
    }

    public OrderBooksRecord getOrderBookById(long id) {
        return dslContext
                .selectFrom(ORDER_BOOKS)
                .where(ORDER_BOOKS.ID.eq(id))
                .fetchAny();
    }

    public OrderBooksRecord addOrderBook(OrderBooksRecord newOrderBook) {
        return dslContext
                .insertInto(ORDER_BOOKS)
                .set(newOrderBook)
                .returning()
                .fetchOne();
    }

    public OrderBooksRecord updateOrderBookById(OrderBooksRecord orderBookRequest) {
        return dslContext
                .update(ORDER_BOOKS)
                .set(orderBookRequest)
                .where(ORDER_BOOKS.ID.eq(orderBookRequest.getId()))
                .returning()
                .fetchOne();
    }

    public boolean deleteOrderBookById(long id) {
        return dslContext
                .deleteFrom(ORDER_BOOKS)
                .where(ORDER_BOOKS.BOOK_ID.eq(id))
                .execute() == 1;
    }

    public List<OrderBooksRecord> getOrderBookByReaderId(Pageable pageable, long idReader) {
        return dslContext
                .select()
                .from(ORDER_BOOKS)
                .join(USERS)
                .on(USERS.ID.eq(ORDER_BOOKS.USER_ID))
                .where(USERS.ID.eq(idReader))
                .orderBy(getSortFields(pageable.getSort()))
                .limit(pageable.getPageSize())
                .offset(pageable.getOffset())
                .fetchInto(OrderBooksRecord.class);
    }

    public List<OrderBooksRecord> findOrderBooksByUsername(Pageable pageable, String username) {
        return dslContext
                .select()
                .from(ORDER_BOOKS)
                .join(USERS)
                .on(USERS.ID.eq(ORDER_BOOKS.USER_ID))
                .where(USERS.USERNAME.eq(username))
                .orderBy(getSortFields(pageable.getSort()))
                .limit(pageable.getPageSize())
                .offset(pageable.getOffset())
                .fetchInto(OrderBooksRecord.class);
    }

    public long findCountAllBooks() {
        return dslContext.fetchCount(dslContext.selectFrom(ORDER_BOOKS));
    }

    public long findCountAllBooksByUserId(long userId) {
        return dslContext
                .fetchCount(
                        dslContext
                                .select()
                                .from(ORDER_BOOKS)
                                .join(USERS)
                                .on(USERS.ID.eq(ORDER_BOOKS.USER_ID))
                                .where(USERS.ID.eq(userId)));
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
            Field tableField = ORDER_BOOKS.getClass().getDeclaredField(sortFieldName);
            sortField = (TableField) tableField.get(ORDER_BOOKS);
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
