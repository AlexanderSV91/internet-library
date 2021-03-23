package com.faceit.example.dto.response.googleevent;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CalendarUser {
    private String email;
    private boolean self;
}
