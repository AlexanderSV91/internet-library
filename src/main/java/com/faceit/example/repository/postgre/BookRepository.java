package com.faceit.example.repository.postgre;

import com.faceit.example.dto.request.postgre.BookRequest;
import com.faceit.example.dto.response.postgre.BookResponse;
import com.faceit.example.model.postgre.BookModel;
import lombok.RequiredArgsConstructor;
import org.jooq.DSLContext;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class BookRepository {

    private final DSLContext dslContext;

    public Page<BookResponse> getAllBooksWithPageable(Pageable pageable) {
        return null;
    }

    public BookModel getBookById(long id) {
        return null;
    }

    public BookModel saveBook(BookModel newBook) {
        return null;
    }

    public BookModel updateBookById(BookRequest updateBook, long id) {
        return null;
    }

    public void deleteBookById(long id) {

    }
}
