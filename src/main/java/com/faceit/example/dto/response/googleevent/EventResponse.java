package com.faceit.example.dto.response.googleevent;

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
    private LocalDateTime created;
    private LocalDateTime updated;
    private String summary;
    private String description;
    private CalendarUser creator;
    private CalendarUser organizer;
    private CalendarSchedule start;
    private CalendarSchedule end;
}
