package com.faceit.example.dto.response.googleevent;

import com.faceit.example.configuration.OffsetDateTimeCombinedSerializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.OffsetDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CalendarSchedule {
    @JsonSerialize(using = OffsetDateTimeCombinedSerializer.OffsetDateTimeSerializer.class)
    @JsonDeserialize(using = OffsetDateTimeCombinedSerializer.OffsetDateTimeDeserializer.class)
    private OffsetDateTime dateTime;
}
