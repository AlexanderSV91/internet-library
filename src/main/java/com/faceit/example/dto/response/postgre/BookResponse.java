package com.faceit.example.dto.response.postgre;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class BookResponse {

    private long id;
    private String name;
    private String bookCondition;
    private String description;
}
