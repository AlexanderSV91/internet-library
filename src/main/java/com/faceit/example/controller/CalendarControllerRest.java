package com.faceit.example.controller;

import com.faceit.example.dto.request.googlecalendar.EventRequest;
import com.faceit.example.dto.response.googleevent.EventResponse;
import com.faceit.example.dto.response.googleevent.EventsResponse;
import com.faceit.example.mapper.googleevent.EventMapper;
import com.faceit.example.service.GoogleCalendarService;
import com.google.api.services.calendar.model.Event;
import com.google.api.services.calendar.model.Events;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.client.annotation.RegisteredOAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = {"/api/v1/calendar"})
@RequiredArgsConstructor
public class CalendarControllerRest {

    private final GoogleCalendarService googleCalendarService;
    private final EventMapper eventMapper;

    @GetMapping
    public ResponseEntity<EventsResponse> getEvents(@RegisteredOAuth2AuthorizedClient OAuth2AuthenticationToken authentication) {
        Events events = googleCalendarService.getEvents(authentication);
        return ResponseEntity.status(HttpStatus.OK).body(eventMapper.eventsToEventsResponse(events));
    }

    @GetMapping("/{id}")
    public ResponseEntity<EventResponse> getEventGoogle(@RegisteredOAuth2AuthorizedClient OAuth2AuthenticationToken authentication,
                                                        @PathVariable String id) {
        Event events = googleCalendarService.getEvent(authentication, id);
        return ResponseEntity.status(HttpStatus.OK).body(eventMapper.eventToEventResponse(events));
    }

    @PostMapping
    public ResponseEntity<EventResponse> addEvent(@RegisteredOAuth2AuthorizedClient OAuth2AuthenticationToken authentication,
                                                  @RequestBody EventRequest eventRequest) {
        Event event = googleCalendarService.addEvent(authentication, eventRequest);
        return ResponseEntity.status(HttpStatus.OK).body(eventMapper.eventToEventResponse(event));
    }

    @PutMapping("/{id}")
    public ResponseEntity<EventResponse> updateEvent(@RegisteredOAuth2AuthorizedClient OAuth2AuthenticationToken authentication,
                                                     @PathVariable String id,
                                                     @RequestBody EventRequest eventRequest) {
        Event event = googleCalendarService.updateEvent(authentication, id, eventRequest);
        return ResponseEntity.status(HttpStatus.OK).body(eventMapper.eventToEventResponse(event));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEvent(@RegisteredOAuth2AuthorizedClient OAuth2AuthenticationToken authentication,
                                            @PathVariable String id) {
        googleCalendarService.deleteEvent(authentication, id);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
