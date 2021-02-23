package com.faceit.example.dto.request.postgre;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
@Builder
public class RoleRequest {

    @NotBlank(message = "exception.pleaseProvideARoleName")
    private String name;
}
