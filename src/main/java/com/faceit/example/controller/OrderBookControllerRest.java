package com.faceit.example.controller;

import com.faceit.example.dto.LocalUser;
import com.faceit.example.dto.request.postgre.OrderBookRequest;
import com.faceit.example.dto.response.postgre.OrderBookResponse;
import com.faceit.example.mapper.postgre.OrderBookMapper;
import com.faceit.example.model.enumeration.OrderBookStatus;
import com.faceit.example.service.postgre.OrderBookService;
import com.faceit.example.tables.records.OrderBooksRecord;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = {"/api/v1/orderbook"})
public class OrderBookControllerRest {

    private final OrderBookService orderBookService;
    private final OrderBookMapper orderBookMapper;

    @GetMapping
    public ResponseEntity<Page<OrderBookResponse>> getOrderBooksByUserUserName(@AuthenticationPrincipal LocalUser userDetails,
                                                                               Pageable pageable) {
        Page<OrderBookResponse> orderBookResponse = orderBookService.findOrderBooksByUsername(userDetails, pageable);
        return ResponseEntity.status(HttpStatus.OK).body(orderBookResponse);
    }

    @GetMapping("/status")
    public ResponseEntity<OrderBookStatus[]> getAllStatus() {
        OrderBookStatus[] status = orderBookService.getAllStatus();
        return ResponseEntity.status(HttpStatus.OK).body(status);
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderBookResponse> getAllOrderBookById(@PathVariable long id) {
        OrderBookResponse orderBookResponse = orderBookMapper
                .orderBookToOrderBookResponse(orderBookService.getOrderBookById(id));
        return ResponseEntity.status(HttpStatus.OK).body(orderBookResponse);
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<Page<OrderBookResponse>> getOrderBookByReader(Pageable pageable, @PathVariable long id) {
        Page<OrderBookResponse> orderBookResponse = orderBookService.getOrderBookByReaderId(pageable, id);
        return ResponseEntity.status(HttpStatus.OK).body(orderBookResponse);
    }

    @PostMapping
    public ResponseEntity<OrderBookResponse> addOrderBook(@RequestBody OrderBookRequest orderBookRequest) {
        OrderBooksRecord orderBook = orderBookMapper.orderBookRequestToOrderBook(orderBookRequest);
        OrderBookResponse orderBookResponse = orderBookMapper
                .orderBookToOrderBookResponse(orderBookService.addOrderBook(orderBook));
        return ResponseEntity.status(HttpStatus.OK).body(orderBookResponse);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOrderBookById(@PathVariable long id) {
        orderBookService.deleteOrderBookById(id);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<OrderBookResponse> updateReaderById(@RequestBody OrderBookRequest orderBookRequest,
                                                              @PathVariable Long id) {
        OrderBooksRecord orderBookRecord = orderBookMapper.orderBookRequestToOrderBook(orderBookRequest);
        OrderBooksRecord orderBook = orderBookService.updateOrderBookById(orderBookRecord, id);
        OrderBookResponse orderBookResponse = orderBookMapper.orderBookToOrderBookResponse(orderBook);
        return ResponseEntity.status(HttpStatus.OK).body(orderBookResponse);
    }
}
