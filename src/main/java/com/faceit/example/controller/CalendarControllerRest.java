package com.faceit.example.controller;

import com.faceit.example.dto.request.googlecalendar.EventRequest;
import com.faceit.example.dto.response.googleevent.EventResponse;
import com.faceit.example.dto.response.googleevent.EventsResponse;
import com.faceit.example.service.GoogleCalendarService;
import com.google.api.services.calendar.model.Event;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.annotation.RegisteredOAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = {"/api/v1/calendar"})
@RequiredArgsConstructor
public class CalendarControllerRest {

    private final GoogleCalendarService googleCalendarService;

    @GetMapping
    public ResponseEntity<EventsResponse> getEvents(@RegisteredOAuth2AuthorizedClient OAuth2AuthorizedClient authorizedClient) {
        EventsResponse events = googleCalendarService.getEvents(authorizedClient.getAccessToken().getTokenValue());
        return ResponseEntity.status(HttpStatus.OK).body(events);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Event> getEventGoogle(@RegisteredOAuth2AuthorizedClient OAuth2AuthenticationToken authentication,
                                                @PathVariable String id) {
        Event events = googleCalendarService.getEvent(authentication, id);
        return ResponseEntity.status(HttpStatus.OK).body(events);
    }

    @PostMapping
    public ResponseEntity<EventResponse> addEvent(@RegisteredOAuth2AuthorizedClient OAuth2AuthorizedClient authorizedClient,
                                                  @RequestBody EventRequest eventRequest) {
/*        EventRequest eventRequest = new EventRequest(
                new DateTime(OffsetDateTime.now().plusDays(1).plusHours(1)),
                new DateTime(OffsetDateTime.now().plusDays(1)),
                "summary",
                "description");*/
        EventResponse event = googleCalendarService.addEvent(authorizedClient.getAccessToken().getTokenValue(), eventRequest);
        return ResponseEntity.status(HttpStatus.OK).body(event);
    }

    @GetMapping("/u/{id}")
    public ResponseEntity<EventResponse> updateEvent(@RegisteredOAuth2AuthorizedClient OAuth2AuthorizedClient authorizedClient,
                                                     @PathVariable String id,
                                                     @RequestBody EventRequest eventRequest) {
/*        EventRequest eventRequest = new EventRequest();
        eventRequest.setSummary("summary1");
        eventRequest.setDescription("description1");*/
        EventResponse event = googleCalendarService.updateEvent(authorizedClient.getAccessToken().getTokenValue(), id, eventRequest);
        return ResponseEntity.status(HttpStatus.OK).body(event);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEvent(@RegisteredOAuth2AuthorizedClient OAuth2AuthorizedClient authorizedClient,
                                            @PathVariable String id) {
        googleCalendarService.deleteEvent(authorizedClient.getAccessToken().getTokenValue(), id);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
