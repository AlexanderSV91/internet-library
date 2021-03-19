package com.faceit.example.service.postgre;

import com.faceit.example.dto.LocalUser;
import com.faceit.example.dto.response.postgre.BookResponse;
import com.faceit.example.tables.records.BooksRecord;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface BookService {

    Page<BookResponse> getAllBooksWithPageable(Pageable pageable);

    BooksRecord getBookById(long id);

    BooksRecord saveBook(LocalUser userDetails, BooksRecord newBook);

    BooksRecord updateBookById(LocalUser userDetails, BooksRecord updateBook, long id);

    void deleteBookById(LocalUser userDetails, long id);
}
