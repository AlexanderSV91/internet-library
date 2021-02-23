package com.faceit.example.controller;

import com.faceit.example.dto.request.postgre.OrderBookRequest;
import com.faceit.example.dto.response.postgre.OrderBookResponse;
import com.faceit.example.exception.ResourceNotFoundException;
import com.faceit.example.mapper.postgre.OrderBookMapper;
import com.faceit.example.model.MyUserDetails;
import com.faceit.example.model.enumeration.OrderBookStatus;
import com.faceit.example.service.postgre.OrderBookService;
import com.faceit.example.tables.records.OrderBooksRecord;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = {"/api/v1/orderbook"})
public class OrderBookControllerRest {

    private final OrderBookService orderBookService;
    private final OrderBookMapper orderBookMapper;

    @GetMapping
    public Page<OrderBookResponse> getOrderBooksByUserUserName(@AuthenticationPrincipal MyUserDetails userDetails,
                                                               Pageable pageable) {
        return orderBookService.findOrderBooksByUserUserName(userDetails, pageable);
    }

    @GetMapping("/status")
    public OrderBookStatus[] getAllStatus() {
        return orderBookService.getAllStatus();
    }

    @GetMapping("/{id}")
    public OrderBookResponse getAllOrderBookById(@PathVariable long id) {
        return orderBookMapper.orderBookToOrderBookResponse(orderBookService.getOrderBookById(id));
    }

    @GetMapping("/user/{id}")
    public Page<OrderBookResponse> getOrderBookByReader(Pageable pageable, @PathVariable long id) {
        return orderBookService.getOrderBookByReaderId(pageable, id);
    }

    @PostMapping
    public OrderBookResponse addOrderBook(@RequestBody OrderBookRequest orderBookRequest) {
        OrderBooksRecord orderBook = orderBookMapper.orderBookRequestToOrderBook(orderBookRequest);
        return orderBookMapper.orderBookToOrderBookResponse(orderBookService.addOrderBook(orderBook));
    }

    @DeleteMapping("/{id}")
    public void deleteOrderBookById(@PathVariable long id) {
        orderBookService.deleteOrderBookById(id);
    }

    @PutMapping("/orderbook/{id}")
    public OrderBookResponse updateReaderById(@RequestBody OrderBookRequest orderBookRequest,
                                              @PathVariable Long id) {
        OrderBooksRecord orderBookRecord = orderBookMapper.orderBookRequestToOrderBook(orderBookRequest);
        OrderBooksRecord orderBook = orderBookService.updateOrderBookById(orderBookRecord, id);
        return orderBookMapper.orderBookToOrderBookResponse(orderBook);
    }
}
