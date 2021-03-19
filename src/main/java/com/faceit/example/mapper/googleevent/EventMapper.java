package com.faceit.example.mapper.googleevent;

import com.faceit.example.dto.request.googlecalendar.EventRequest;
import com.faceit.example.dto.response.googleevent.EventResponse;
import com.faceit.example.dto.response.googleevent.EventsResponse;
import com.google.api.client.util.DateTime;
import com.google.api.services.calendar.model.Event;
import com.google.api.services.calendar.model.Events;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface EventMapper {

    Event eventRequestToEvent(EventRequest eventRequest);
    
    EventResponse eventToEventResponse(Event event);

    EventsResponse eventsToEventsResponse(Events events);

    Event updateEventRequestFromEvent(EventRequest updateEvent, @MappingTarget Event event);

    default LocalDateTime dateTimeToLocalDateTime(DateTime dateTime) {
        ZoneOffset zoneOffSet = ZoneOffset.ofTotalSeconds(dateTime.getTimeZoneShift() * 60);
        return LocalDateTime.ofInstant(Instant.ofEpochMilli(dateTime.getValue()), zoneOffSet);
    }

    default OffsetDateTime dateTimeToOffsetDateTime(DateTime dateTime) {
        ZoneOffset zoneOffSet = ZoneOffset.ofTotalSeconds(dateTime.getTimeZoneShift() * 60);
        return OffsetDateTime.ofInstant(Instant.ofEpochMilli(dateTime.getValue()), zoneOffSet);
    }
}
