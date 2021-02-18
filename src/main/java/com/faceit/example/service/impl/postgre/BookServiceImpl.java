package com.faceit.example.service.impl.postgre;

import com.faceit.example.dto.request.postgre.BookRequest;
import com.faceit.example.dto.response.postgre.BookResponse;
import com.faceit.example.model.postgre.BookModel;
import com.faceit.example.repository.postgre.BookRepository;
import com.faceit.example.service.postgre.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;

    @Override
    public Page<BookResponse> getAllBooksWithPageable(Pageable pageable) {
        return bookRepository.getAllBooksWithPageable(pageable);
    }

    @Override
    public BookModel getBookById(long id) {
        return bookRepository.getBookById(id);
    }

    @Override
    public BookModel saveBook(BookModel newBook) {
        return bookRepository.saveBook(newBook);
    }

    @Override
    public BookModel updateBookById(BookRequest updateBook, long id) {
        return bookRepository.updateBookById(updateBook,id);
    }

    @Override
    public void deleteBookById(long id) {
        bookRepository.deleteBookById(id);
    }
}
