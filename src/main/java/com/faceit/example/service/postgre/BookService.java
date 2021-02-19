package com.faceit.example.service.postgre;

import com.faceit.example.dto.request.postgre.BookRequest;
import com.faceit.example.dto.response.postgre.BookResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface BookService {

    Page<BookResponse> getAllBooksWithPageable(Pageable pageable);

    BookResponse getBookById(long id);

    BookResponse saveBook(BookRequest newBook);

    BookResponse updateBookById(BookRequest updateBook, long id);

    void deleteBookById(long id);
}
