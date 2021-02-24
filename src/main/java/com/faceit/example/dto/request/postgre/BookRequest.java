package com.faceit.example.dto.request.postgre;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BookRequest {

    @NotBlank(message = "exception.pleaseProvideABookName")
    private String name;
    @NotBlank(message = "exception.pleaseProvideABookCondition")
    private String bookCondition;
    private String description;
}
