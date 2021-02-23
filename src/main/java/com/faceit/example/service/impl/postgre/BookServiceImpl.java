package com.faceit.example.service.impl.postgre;

import com.faceit.example.dto.response.postgre.BookResponse;
import com.faceit.example.exception.ApiRequestException;
import com.faceit.example.exception.ResourceAlreadyExists;
import com.faceit.example.exception.ResourceNotFoundException;
import com.faceit.example.mapper.postgre.BookMapper;
import com.faceit.example.model.MyUserDetails;
import com.faceit.example.repository.postgre.BookRepository;
import com.faceit.example.service.postgre.BookService;
import com.faceit.example.tables.records.BooksRecord;
import com.faceit.example.util.Utils;
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
    public BooksRecord getBookById(long id) {
        return bookRepository.getBookById(id);
    }

    @Override
    public BooksRecord saveBook(MyUserDetails userDetails, BooksRecord newBook) {
        boolean isEmployee = Utils.isEmployee(userDetails.getRolesRecords());
        if (isEmployee) {
            boolean exists = bookRepository.existsBookByName(newBook.getName());
            if (exists) {
                throw new ResourceAlreadyExists("name","exception.bookNameExist");
            }
            return bookRepository.saveBook(newBook);
        } else {
            throw new ApiRequestException("exception.bookNotAdd");
        }
    }

    @Override
    public BooksRecord updateBookById(MyUserDetails userDetails, BooksRecord updateBook, long id) {
        boolean isEmployee = Utils.isEmployee(userDetails.getRolesRecords());
        if (isEmployee) {
            BooksRecord bookRecord = bookRepository.getBookById(id);
            bookMapper.updateBookRecordFromBookRecord(updateBook, bookRecord);
            return bookRepository.updateBookById(bookRecord);
        } else {
            throw new ResourceNotFoundException("exception.notFound");
        }
    }

    @Override
    public void deleteBookById(MyUserDetails userDetails, long id) {
        boolean isEmployee = Utils.isEmployee(userDetails.getRolesRecords());
        if (isEmployee) {
            boolean isDeleted = bookRepository.deleteBookById(id);
            if (!isDeleted) {
                throw new ApiRequestException("exception.bookNotDelete");
            }
        } else {
            throw new ApiRequestException("exception.noPermission");
        }
    }
}
