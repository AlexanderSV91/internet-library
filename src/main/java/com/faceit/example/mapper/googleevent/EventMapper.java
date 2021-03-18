package com.faceit.example.mapper.googleevent;

import com.faceit.example.dto.request.googlecalendar.EventRequest;
import com.faceit.example.dto.response.googleevent.EventResponse;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface EventMapper {

    EventRequest eventToEventRequest(EventResponse event);

    EventRequest updateEventRequestFromEvent(EventRequest updateEvent, @MappingTarget EventRequest event);
}
