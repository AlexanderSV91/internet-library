package com.faceit.example.dto.request.googlecalendar;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.api.client.util.DateTime;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DateTimeRequest {

    @JsonProperty("dateTime")
    private DateTime dateTime;
}
