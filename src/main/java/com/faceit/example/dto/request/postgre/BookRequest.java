package com.faceit.example.dto.request.postgre;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class BookRequest {

    private String name;
    private String bookCondition;
    private String description;
}
