package com.faceit.example.repository.postgre;

import com.faceit.example.tables.records.BooksRecord;
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

import static com.faceit.example.Tables.BOOKS;

@Repository
@RequiredArgsConstructor
public class BookRepository {

    private final DSLContext dslContext;

    public List<BooksRecord> getAllBooksWithPageable(Pageable pageable) {
        return dslContext
                .selectFrom(BOOKS)
                .orderBy(getSortFields(pageable.getSort()))
                .limit(pageable.getPageSize())
                .offset(pageable.getOffset())
                .fetchInto(BooksRecord.class);
    }

    public BooksRecord getBookById(long id) {
        return dslContext
                .selectFrom(BOOKS)
                .where(BOOKS.ID.eq(id))
                .fetchAny();
    }

    public BooksRecord saveBook(BooksRecord newBook) {
        return dslContext
                .insertInto(BOOKS)
                .set(newBook)
                .returning()
                .fetchOne();
    }

    public BooksRecord updateBookById(BooksRecord updateBook) {
        return dslContext
                .update(BOOKS)
                .set(BOOKS.NAME, updateBook.getName())
                .set(BOOKS.BOOK_CONDITION, updateBook.getBookCondition())
                .set(BOOKS.DESCRIPTION, updateBook.getDescription())
                .where(BOOKS.ID.eq(updateBook.getId()))
                .returning()
                .fetchOne();
    }

    public boolean deleteBookById(long id) {
        return dslContext.deleteFrom(BOOKS)
                .where(BOOKS.ID.eq(id))
                .execute() == 1;
    }

    public long findCountAllBooks() {
        return dslContext.fetchCount(dslContext.select().from(BOOKS));
    }

    public boolean existsBookByName(String name) {
        return dslContext
                .fetchExists(
                        dslContext
                                .selectFrom(BOOKS)
                                .where(BOOKS.NAME.eq(name)));
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
            Field tableField = BOOKS.getClass().getDeclaredField(sortFieldName);
            sortField = (TableField) tableField.get(BOOKS);
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
