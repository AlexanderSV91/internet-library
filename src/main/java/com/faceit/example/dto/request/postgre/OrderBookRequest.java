package com.faceit.example.dto.request.postgre;

import com.faceit.example.dto.response.postgre.BookResponse;
import com.faceit.example.dto.response.postgre.UserResponse;
import com.faceit.example.model.enumeration.OrderBookStatus;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderBookRequest {


    private OrderBookStatus status;
    @NotNull(message = "exception.userDoesNotExist")
    private UserResponse user;
    @NotNull(message = "exception.bookDoesNotExist")
    private BookResponse book;
    private String note;
//    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm", iso = DateTimeFormat.ISO.DATE_TIME)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime startDate;
//    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm", iso = DateTimeFormat.ISO.DATE_TIME)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime endDate;
}
