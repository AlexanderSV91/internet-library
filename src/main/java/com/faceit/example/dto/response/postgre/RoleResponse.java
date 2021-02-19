package com.faceit.example.dto.response.postgre;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RoleResponse {

    private long id;
    private String name;
}
