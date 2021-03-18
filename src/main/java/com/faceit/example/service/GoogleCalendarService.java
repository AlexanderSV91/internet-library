package com.faceit.example.service;

import com.faceit.example.dto.request.googlecalendar.EventRequest;
import com.faceit.example.dto.response.googleevent.EventResponse;
import com.faceit.example.dto.response.googleevent.EventsResponse;
import com.google.api.client.auth.oauth2.Credential;
import com.google.api.services.calendar.model.Event;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;

public interface GoogleCalendarService {

    EventsResponse getEvents(String accessToken);

    Event getEvent(OAuth2AuthenticationToken authentication, String id);

    EventResponse addEvent(String accessToken, EventRequest eventRequest);

    EventResponse updateEvent(String accessToken, String id, EventRequest eventRequest);

    void deleteEvent(String accessToken, String id);
}
