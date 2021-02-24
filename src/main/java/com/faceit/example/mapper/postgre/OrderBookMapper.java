package com.faceit.example.mapper.postgre;

import com.faceit.example.dto.request.postgre.OrderBookRequest;
import com.faceit.example.dto.response.postgre.OrderBookResponse;
import com.faceit.example.tables.records.OrderBooksRecord;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring")
public interface OrderBookMapper {

    @Mappings({
            @Mapping(source = "startDate", dateFormat = "dd/MM/yyyy hh:mm", target = "startDate"),
            @Mapping(source = "endDate", dateFormat = "dd/MM/yyyy hh:mm", target = "endDate")})
    OrderBookResponse orderBookToOrderBookResponse(OrderBooksRecord orderBook);

    @Mappings({
            @Mapping(source = "startDate", dateFormat = "dd/MM/yyyy hh:mm", target = "startDate"),
            @Mapping(source = "endDate", dateFormat = "dd/MM/yyyy hh:mm", target = "endDate"),
            @Mapping(target = "id", ignore = true),
            @Mapping(source = "user.id", target = "userId"),
            @Mapping(source = "book.id", target = "bookId")
    })
    OrderBooksRecord orderBookRequestToOrderBook(OrderBookRequest orderBookRequest);

    List<OrderBookResponse> orderBooksToOrderBookResponse(List<OrderBooksRecord> orderBooks);

    @Mappings({
            @Mapping(target = "id", ignore = true)
    })
    OrderBooksRecord updateOrderBookFromOrderBook(OrderBooksRecord orderBookRequest,
                                                  @MappingTarget OrderBooksRecord orderBook);
}
