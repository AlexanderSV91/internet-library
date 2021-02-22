package com.faceit.example.service.postgre;

import com.faceit.example.dto.response.postgre.BookResponse;
import com.faceit.example.model.MyUserDetails;
import com.faceit.example.tables.records.BooksRecord;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface BookService {

    Page<BookResponse> getAllBooksWithPageable(Pageable pageable);

    BooksRecord getBookById(long id);

    BooksRecord saveBook(MyUserDetails userDetails, BooksRecord newBook);

    BooksRecord updateBookById(MyUserDetails userDetails, BooksRecord updateBook, long id);

    void deleteBookById(MyUserDetails userDetails, long id);
}
