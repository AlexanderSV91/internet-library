package com.faceit.example.model.postgre;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BookModel {

    private long id;
    private String name;
    private String bookCondition;
    private String description;
}
