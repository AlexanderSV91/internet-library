package com.faceit.example.dto.response.postgre;

import com.faceit.example.model.enumeration.OrderBookStatus;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class OrderBookResponse {

    private long id;
    private OrderBookStatus status;
    private UserResponse user;
    private BookResponse book;
    private String note;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime startDate;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime endDate;
}
