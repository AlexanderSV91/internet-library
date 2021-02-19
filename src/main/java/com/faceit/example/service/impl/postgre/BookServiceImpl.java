package com.faceit.example.service.impl.postgre;

import com.faceit.example.dto.request.postgre.BookRequest;
import com.faceit.example.dto.response.postgre.BookResponse;
import com.faceit.example.mapper.postgre.BookMapper;
import com.faceit.example.repository.postgre.BookRepository;
import com.faceit.example.service.postgre.BookService;
import com.faceit.example.tables.records.BooksRecord;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;
    private final BookMapper bookMapper;

    @Override
    public Page<BookResponse> getAllBooksWithPageable(Pageable pageable) {
        List<BooksRecord> booksRecordList = bookRepository.getAllBooksWithPageable(pageable);
        long totalElements = bookRepository.findCountAllBooks();

        List<BookResponse> bookResponseList = bookMapper.booksRecordToBooksResponse(booksRecordList);
        return new PageImpl<>(bookResponseList, pageable, totalElements);
    }

    @Override
    public BookResponse getBookById(long id) {
        BooksRecord booksRecord = bookRepository.getBookById(id);
        return bookMapper.bookRecordToBookResponse(booksRecord);
    }

    @Override
    public BookResponse saveBook(BookRequest newBook) {
        boolean exists = bookRepository.existsBookByName(newBook.getName());
        if (exists) {
            throw new RuntimeException("exists name book");
        }

        BooksRecord booksRecord = bookRepository.saveBook(bookMapper.bookRequestToBookRecord(newBook));
        return bookMapper.bookRecordToBookResponse(booksRecord);
    }

    @Override
    public BookResponse updateBookById(BookRequest updateBook, long id) {
        BooksRecord bookRecord = bookRepository.getBookById(id);
        bookMapper.updateBookRecordFromBookRequest(updateBook, bookRecord);
        BooksRecord booksRecord = bookRepository.updateBookById(bookRecord);
        return bookMapper.bookRecordToBookResponse(booksRecord);
    }

    @Override
    public void deleteBookById(long id) {
        boolean isDeleted = bookRepository.deleteBookById(id);
        if (!isDeleted) {
            throw new RuntimeException("book is not deleted");
        }
    }
}
