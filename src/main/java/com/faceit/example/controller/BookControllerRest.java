package com.faceit.example.controller;

import com.faceit.example.dto.LocalUser;
import com.faceit.example.dto.request.postgre.BookRequest;
import com.faceit.example.dto.response.postgre.BookResponse;
import com.faceit.example.mapper.postgre.BookMapper;
import com.faceit.example.service.postgre.BookService;
import com.faceit.example.tables.records.BooksRecord;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(value = {"/api/v1/book"})
@RequiredArgsConstructor
public class BookControllerRest {

    private final BookService bookService;
    private final BookMapper bookMapper;

    @GetMapping
    public ResponseEntity<Page<BookResponse>> getAllBooksWithPageable(Pageable pageable) {
        Page<BookResponse> bookResponses = bookService.getAllBooksWithPageable(pageable);
        return ResponseEntity.status(HttpStatus.OK).body(bookResponses);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BookResponse> getBookById(@PathVariable long id) {
        BookResponse bookResponse = bookMapper.bookRecordToBookResponse(bookService.getBookById(id));
        return ResponseEntity.status(HttpStatus.OK).body(bookResponse);
    }

    @PostMapping
    public ResponseEntity<BookResponse> saveBook(@AuthenticationPrincipal LocalUser userDetails,
                                                 @Valid @RequestBody BookRequest bookRequest) {
        BooksRecord bookRecord = bookMapper.bookRequestToBookRecord(bookRequest);
        BookResponse bookResponse = bookMapper.bookRecordToBookResponse(
                bookService.saveBook(userDetails, bookRecord));
        return ResponseEntity.status(HttpStatus.OK).body(bookResponse);
    }

    @PutMapping("/{id}")
    public ResponseEntity<BookResponse> updateBookById(@AuthenticationPrincipal LocalUser userDetails,
                                                       @RequestBody BookRequest updateBook,
                                                       @PathVariable long id) {
        BooksRecord bookRecord = bookMapper.bookRequestToBookRecord(updateBook);
        BookResponse bookResponse = bookMapper.bookRecordToBookResponse(
                bookService.updateBookById(userDetails, bookRecord, id));
        return ResponseEntity.status(HttpStatus.OK).body(bookResponse);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBookById(@AuthenticationPrincipal LocalUser userDetails,
                                               @PathVariable long id) {
        bookService.deleteBookById(userDetails, id);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
