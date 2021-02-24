package com.faceit.example.dto.response.postgre;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BookResponse {

    private long id;
    private String name;
    private String bookCondition;
    private String description;
}
