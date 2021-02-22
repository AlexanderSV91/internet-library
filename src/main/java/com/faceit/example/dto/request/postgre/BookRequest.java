package com.faceit.example.dto.request.postgre;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
@Builder
public class BookRequest {

    @NotBlank(message = "exception.pleaseProvideABookName")
    private String name;
    @NotBlank(message = "exception.pleaseProvideABookCondition")
    private String bookCondition;
    private String description;
}
