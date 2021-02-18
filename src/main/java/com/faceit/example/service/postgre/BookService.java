package com.faceit.example.service.postgre;

import com.faceit.example.dto.request.postgre.BookRequest;
import com.faceit.example.dto.response.postgre.BookResponse;
import com.faceit.example.model.postgre.BookModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface BookService {

    Page<BookResponse> getAllBooksWithPageable(Pageable pageable);

    BookModel getBookById(long id);

    BookModel saveBook(BookModel newBook);

    BookModel updateBookById(BookRequest updateBook, long id);

    void deleteBookById(long id);
}
