package com.faceit.example.dto.response.googleevent;

import com.faceit.example.configuration.LocalDateTimeCombinedSerializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
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
    @JsonSerialize(using = LocalDateTimeCombinedSerializer.LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeCombinedSerializer.LocalDateTimeDeserializer.class)
    private LocalDateTime updated;
    private String timeZone;
    private String accessRole;
    private String nextSyncToken;
    private List<EventResponse> items;
}
