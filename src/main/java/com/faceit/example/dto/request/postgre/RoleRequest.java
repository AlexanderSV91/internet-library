package com.faceit.example.dto.request.postgre;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RoleRequest {

    private String name;
}
