package com.faceit.example.dto.request.googlecalendar;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EventRequest {

    @JsonProperty("end")
    private DateTime end;

    @JsonProperty("start")
    private DateTime start;

    @JsonProperty("summary")
    private String summary;

    @JsonProperty("description")
    private String description;
}