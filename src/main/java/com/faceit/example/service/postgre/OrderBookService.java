package com.faceit.example.service.postgre;

import com.faceit.example.dto.response.postgre.OrderBookResponse;
import com.faceit.example.model.MyUserDetails;
import com.faceit.example.model.enumeration.OrderBookStatus;
import com.faceit.example.tables.records.OrderBooksRecord;
import com.faceit.example.tables.records.UsersRecord;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface OrderBookService {

    List<OrderBookResponse> getPagingOrderBook(Pageable pageable);

    OrderBooksRecord getOrderBookById(long id);

    OrderBooksRecord addOrderBook(OrderBooksRecord newOrderBook);

    OrderBooksRecord updateOrderBookById(OrderBooksRecord orderBookRequest, long id);

    void deleteOrderBookById(long id);

    Page<OrderBookResponse> getOrderBookByReaderId(Pageable pageable, long idReader);

    Page<OrderBookResponse> findOrderBooksByUsername(MyUserDetails user, Pageable pageable);

    OrderBookStatus[] getAllStatus();
}
