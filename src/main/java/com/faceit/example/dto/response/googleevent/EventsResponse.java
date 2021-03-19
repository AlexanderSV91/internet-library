package com.faceit.example.dto.response.googleevent;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EventsResponse {
    private String kind;
    private String etag;
    private String summary;
    private LocalDateTime updated;
    private String timeZone;
    private String accessRole;
    private String nextSyncToken;
    private List<EventResponse> items;
}
