package com.faceit.example.mapper.postgre;

import com.faceit.example.dto.request.postgre.OrderBookRequest;
import com.faceit.example.dto.response.postgre.OrderBookResponse;
import com.faceit.example.tables.records.OrderBooksRecord;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Mappings;

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
            @Mapping(target = "id", ignore = true)
    })
    OrderBooksRecord orderBookRequestToOrderBook(OrderBookRequest orderBookRequest);

    List<OrderBookResponse> orderBooksToOrderBookResponse(List<OrderBooksRecord> orderBooks);

    @Mapping(target = "id", ignore = true)
    OrderBooksRecord updateOrderBookFromOrderBook(OrderBooksRecord orderBookRequest,
                                                  @MappingTarget OrderBooksRecord orderBook);
}
