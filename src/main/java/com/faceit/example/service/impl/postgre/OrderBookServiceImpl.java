package com.faceit.example.service.impl.postgre;

import com.faceit.example.dto.response.postgre.OrderBookResponse;
import com.faceit.example.exception.ApiRequestException;
import com.faceit.example.mapper.postgre.BookMapper;
import com.faceit.example.mapper.postgre.OrderBookMapper;
import com.faceit.example.mapper.postgre.UserMapper;
import com.faceit.example.model.MyUserDetails;
import com.faceit.example.model.enumeration.OrderBookStatus;
import com.faceit.example.repository.postgre.OrderBookRepository;
import com.faceit.example.service.postgre.OrderBookService;
import com.faceit.example.tables.records.BooksRecord;
import com.faceit.example.tables.records.OrderBooksRecord;
import com.faceit.example.tables.records.UsersRecord;
import com.faceit.example.util.Utils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jooq.Record;
import org.jooq.Result;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import static com.faceit.example.Tables.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderBookServiceImpl implements OrderBookService {

    private final OrderBookRepository orderBookRepository;
    private final OrderBookMapper orderBookMapper;
    private final UserMapper userMapper;
    private final BookMapper bookMapper;

    @Override
    public List<OrderBookResponse> getPagingOrderBook(Pageable pageable) {
        Result<Record> recordResult = orderBookRepository.getPagingOrderBook(pageable);
        return mapToOrderBookResponse(recordResult);
    }

    @Override
    public OrderBooksRecord getOrderBookById(long id) {
        return orderBookRepository.getOrderBookById(id);
    }

    @Override
    public OrderBooksRecord addOrderBook(OrderBooksRecord newOrderBook) {
        LocalDateTime now = LocalDateTime.now().withNano(0);
        if (newOrderBook.getStartDate() == null) {
            newOrderBook.setStartDate(now);
        }
        if (newOrderBook.getEndDate() == null) {
            newOrderBook.setEndDate(now);
        }
        if (newOrderBook.getStartDate().isAfter(newOrderBook.getEndDate()) ||
                newOrderBook.getStartDate().isEqual(newOrderBook.getEndDate())) {
            newOrderBook.setEndDate(now.plusMonths(2L));
            newOrderBook.setStartDate(now);
        }
        return orderBookRepository.addOrderBook(newOrderBook);
    }

    @Override
    public OrderBooksRecord updateOrderBookById(OrderBooksRecord update, long id) {
        OrderBooksRecord orderBooksRecord = orderBookMapper
                .updateOrderBookFromOrderBook(update, orderBookRepository.getOrderBookById(id));
        log.error(orderBooksRecord.toString());
        //return orderBookRepository.updateOrderBookById(orderBooksRecord);
        return null;
    }

    @Override
    public void deleteOrderBookById(long id) {
        boolean isDeleted = orderBookRepository.deleteOrderBookById(id);
        if (!isDeleted) {
            throw new ApiRequestException("exception.orderBookNotDelete");
        }
    }

    @Override
    public Page<OrderBookResponse> getOrderBookByReaderId(Pageable pageable, long idReader) {
        Result<Record> recordResult = orderBookRepository.getOrderBookByReaderId(pageable, idReader);
        long totalElements = orderBookRepository.findCountAllBooksByUserId(idReader);

        List<OrderBookResponse> orderBookResponses = mapToOrderBookResponse(recordResult);
        return new PageImpl<>(orderBookResponses, pageable, totalElements);
    }

    @Override
    public Page<OrderBookResponse> findOrderBooksByUserUserName(MyUserDetails user, Pageable pageable) {
        boolean isEmployee = Utils.isEmployee(user.getRolesRecords());

        List<OrderBookResponse> orderBookResponses;
        long totalElements;
        if (isEmployee) {
            orderBookResponses = getPagingOrderBook(pageable);
            totalElements = orderBookRepository.findCountAllBooks();
        } else {
            Result<Record> recordResult = orderBookRepository.findOrderBooksByUserUserName(pageable, user.getUsername());
            totalElements = orderBookRepository.findCountAllBooksByUserId(user.getUser().getId());
            orderBookResponses = mapToOrderBookResponse(recordResult);
        }
        return new PageImpl<>(orderBookResponses, pageable, totalElements);
    }

    @Override
    public OrderBookStatus[] getAllStatus() {
        return OrderBookStatus.values();
    }

    private List<OrderBookResponse> mapToOrderBookResponse(Result<Record> recordResult) {
        return recordResult.stream().map(record -> {
            OrderBooksRecord orderBooksRecord = record.into(ORDER_BOOKS);
            UsersRecord usersRecord = record.into(USERS);
            BooksRecord books = record.into(BOOKS);
            return OrderBookResponse
                    .builder()
                    .id(orderBooksRecord.getId())
                    .status(OrderBookStatus.valueOf(orderBooksRecord.getStatus()))
                    .user(userMapper.userRecordToUserResponse(usersRecord))
                    .book(bookMapper.bookRecordToBookResponse(books))
                    .note(orderBooksRecord.getNote())
                    .startDate(orderBooksRecord.getStartDate())
                    .endDate(orderBooksRecord.getEndDate())
                    .build();
        }).collect(Collectors.toList());
    }
}
