package com.faceit.example.service.impl.postgre;

import com.faceit.example.dto.response.postgre.OrderBookResponse;
import com.faceit.example.exception.ApiRequestException;
import com.faceit.example.mapper.postgre.BookMapper;
import com.faceit.example.mapper.postgre.OrderBookMapper;
import com.faceit.example.mapper.postgre.UserMapper;
import com.faceit.example.model.MyUserDetails;
import com.faceit.example.model.enumeration.OrderBookStatus;
import com.faceit.example.repository.postgre.BookRepository;
import com.faceit.example.repository.postgre.OrderBookRepository;
import com.faceit.example.repository.postgre.UserRepository;
import com.faceit.example.service.postgre.OrderBookService;
import com.faceit.example.tables.records.OrderBooksRecord;
import com.faceit.example.util.Utils;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderBookServiceImpl implements OrderBookService {

    private final OrderBookRepository orderBookRepository;
    private final UserRepository userRepository;
    private final BookRepository bookRepository;
    private final OrderBookMapper orderBookMapper;
    private final UserMapper userMapper;
    private final BookMapper bookMapper;

    @Override
    public List<OrderBookResponse> getPagingOrderBook(Pageable pageable) {
        List<OrderBooksRecord> recordResult = orderBookRepository.getPagingOrderBook(pageable);
        return getOrderBookResponse(recordResult);
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
        OrderBooksRecord orderBookById = orderBookRepository.getOrderBookById(id);
        OrderBooksRecord orderBooksRecord = orderBookMapper
                .updateOrderBookFromOrderBook(update, orderBookById);
        return orderBookRepository.updateOrderBookById(orderBooksRecord);
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
        List<OrderBooksRecord> recordResult = orderBookRepository.getOrderBookByReaderId(pageable, idReader);
        long totalElements = orderBookRepository.findCountAllBooksByUserId(idReader);

        List<OrderBookResponse> orderBookResponses = getOrderBookResponse(recordResult);
        return new PageImpl<>(orderBookResponses, pageable, totalElements);
    }

    @Override
    public Page<OrderBookResponse> findOrderBooksByUsername(MyUserDetails user, Pageable pageable) {
        boolean isEmployee = Utils.isEmployee(user.getRolesRecords());

        List<OrderBookResponse> orderBookResponses;
        long totalElements;
        if (isEmployee) {
            orderBookResponses = getPagingOrderBook(pageable);
            totalElements = orderBookRepository.findCountAllBooks();
        } else {
            List<OrderBooksRecord> orderBooksByUsername =
                    orderBookRepository.findOrderBooksByUsername(pageable, user.getUsername());
            orderBookResponses = getOrderBookResponse(orderBooksByUsername);
            totalElements = orderBookRepository.findCountAllBooksByUserId(user.getUser().getId());
        }
        return new PageImpl<>(orderBookResponses, pageable, totalElements);
    }

    @Override
    public OrderBookStatus[] getAllStatus() {
        return OrderBookStatus.values();
    }

    private List<OrderBookResponse> getOrderBookResponse(List<OrderBooksRecord> orderBooksRecords) {
        return orderBooksRecords.stream().map(orderBooksRecord -> new OrderBookResponse(
                orderBooksRecord.getId(),
                OrderBookStatus.valueOf(orderBooksRecord.getStatus()),
                userMapper.userRecordToUserResponse(userRepository.getUserById(orderBooksRecord.getUserId())),
                bookMapper.bookRecordToBookResponse(bookRepository.getBookById(orderBooksRecord.getBookId())),
                orderBooksRecord.getNote(),
                orderBooksRecord.getStartDate(),
                orderBooksRecord.getEndDate()
        )).collect(Collectors.toList());
    }
}
