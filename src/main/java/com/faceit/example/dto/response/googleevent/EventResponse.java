package com.faceit.example.dto.response.googleevent;

import com.faceit.example.configuration.LocalDateTimeCombinedSerializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EventResponse {
    private String kind;
    private String etag;
    private String id;
    private String status;
    private String htmlLink;
    @JsonSerialize(using = LocalDateTimeCombinedSerializer.LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeCombinedSerializer.LocalDateTimeDeserializer.class)
    private LocalDateTime created;
    @JsonSerialize(using = LocalDateTimeCombinedSerializer.LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeCombinedSerializer.LocalDateTimeDeserializer.class)
    private LocalDateTime updated;
    private String summary;
    private String description;
    private CalendarUser creator;
    private CalendarUser organizer;
    private CalendarSchedule start;
    private CalendarSchedule end;
}
