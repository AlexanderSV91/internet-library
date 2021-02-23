package com.faceit.example.dto.request.postgre;

import com.faceit.example.dto.response.postgre.BookResponse;
import com.faceit.example.dto.response.postgre.UserResponse;
import com.faceit.example.model.enumeration.OrderBookStatus;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Data
@Builder
public class OrderBookRequest {

    private OrderBookStatus status;
    @NotNull(message = "exception.userDoesNotExist")
    private UserResponse user;
    @NotNull(message = "exception.bookDoesNotExist")
    private BookResponse book;
    private String note;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime startDate;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime endDate;
}
