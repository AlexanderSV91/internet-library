package com.faceit.example.controller;

import com.faceit.example.dto.request.postgre.BookRequest;
import com.faceit.example.dto.response.postgre.BookResponse;
import com.faceit.example.mapper.postgre.BookMapper;
import com.faceit.example.model.postgre.BookModel;
import com.faceit.example.service.postgre.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(value = {"/api/v1/book"})
@RequiredArgsConstructor
public class BookControllerRest {

    private final BookService bookService;
    private final BookMapper bookMapper;

    @GetMapping
    public ResponseEntity<Page<BookResponse>> getAllBooksWithPageable(final Pageable pageable) {
        Page<BookResponse> bookResponses = bookService.getAllBooksWithPageable(pageable);
        return ResponseEntity.status(HttpStatus.OK).body(bookResponses);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BookResponse> getBookById(@PathVariable final long id) {
        BookModel bookModel = bookService.getBookById(id);
        BookResponse bookResponse = bookMapper.bookModelToBookResponse(bookModel);
        return ResponseEntity.status(HttpStatus.OK).body(bookResponse);
    }

    @PostMapping
    public ResponseEntity<BookResponse> saveBook(@Valid @RequestBody final BookRequest bookRequest) {
        BookModel bookModel = bookMapper.bookRequestToBookModel(bookRequest);
        BookResponse bookResponse = bookMapper.bookModelToBookResponse(bookService.saveBook(bookModel));
        return ResponseEntity.status(HttpStatus.OK).body(bookResponse);
    }

    @PutMapping("/{id}")
    public ResponseEntity<BookResponse> updateBookById(@RequestBody final BookRequest updateBook,
                                                @PathVariable final long id) {
        BookModel bookModel = bookService.updateBookById(updateBook, id);
        BookResponse bookResponse = bookMapper.bookModelToBookResponse(bookModel);
        return ResponseEntity.status(HttpStatus.OK).body(bookResponse);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBookById(@PathVariable final long id) {
        bookService.deleteBookById(id);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
