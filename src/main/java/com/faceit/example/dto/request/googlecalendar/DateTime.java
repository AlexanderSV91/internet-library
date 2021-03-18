package com.faceit.example.dto.request.googlecalendar;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DateTime {

    @JsonProperty("dateTime")
    private OffsetDateTime dateTime;
}
