package com.faceit.example.service;

import com.faceit.example.dto.request.googlecalendar.EventRequest;
import com.google.api.services.calendar.model.Event;
import com.google.api.services.calendar.model.Events;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;

public interface GoogleCalendarService {

    Events getEvents(OAuth2AuthenticationToken authentication);

    Event getEvent(OAuth2AuthenticationToken authentication, String id);

    Event addEvent(OAuth2AuthenticationToken authentication, EventRequest eventRequest);

    Event updateEvent(OAuth2AuthenticationToken authentication, String id, EventRequest eventRequest);

    void deleteEvent(OAuth2AuthenticationToken authentication, String id);
}
