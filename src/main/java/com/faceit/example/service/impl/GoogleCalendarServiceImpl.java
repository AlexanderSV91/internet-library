package com.faceit.example.service.impl;

import com.faceit.example.dto.request.googlecalendar.EventRequest;
import com.faceit.example.mapper.googleevent.EventMapper;
import com.faceit.example.service.GoogleCalendarService;
import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.auth.oauth2.TokenResponse;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.services.calendar.Calendar;
import com.google.api.services.calendar.CalendarScopes;
import com.google.api.services.calendar.model.Event;
import com.google.api.services.calendar.model.Events;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.time.Duration;
import java.time.Instant;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class GoogleCalendarServiceImpl implements GoogleCalendarService {

    private static final String CALENDAR_ID = "primary";
    private static final String APPLICATION_NAME = "internet-library";
    private static final List<String> SCOPES = Collections.singletonList(CalendarScopes.CALENDAR_EVENTS);

    private final EventMapper eventMapper;
    private final JsonFactory jsonFactory;
    private final OAuth2AuthorizedClientService authorizedClientService;

    @Override
    public Events getEvents(OAuth2AuthenticationToken authentication) {
        Calendar service = prepareCalendarService(authentication);
        Events events = null;
        try {
            events = service.events().list(CALENDAR_ID).execute();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return events;
    }

    @Override
    public Event getEvent(OAuth2AuthenticationToken authentication, String id) {
        Calendar service = prepareCalendarService(authentication);
        Event event = null;
        try {
            event = service.events().get(CALENDAR_ID, id).execute();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return event;
    }

    @Override
    public Event addEvent(OAuth2AuthenticationToken authentication, EventRequest eventRequest) {
        Calendar service = prepareCalendarService(authentication);

        Event event = eventMapper.eventRequestToEvent(eventRequest);
        try {
            event = service.events().insert(CALENDAR_ID, event).execute();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return event;
    }

    @Override
    public Event updateEvent(OAuth2AuthenticationToken authentication, String id, EventRequest eventRequest) {
        Calendar service = prepareCalendarService(authentication);

        Event event = getEvent(authentication, id);
        event = eventMapper.updateEventRequestFromEvent(eventRequest, event);

        Event updatedEvent = null;
        try {
            updatedEvent = service.events().update(CALENDAR_ID, event.getId(), event).execute();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return updatedEvent;
    }

    @Override
    public void deleteEvent(OAuth2AuthenticationToken authentication, String id) {
        Calendar service = prepareCalendarService(authentication);
        try {
            service.events().delete(CALENDAR_ID, id).execute();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private Calendar prepareCalendarService(OAuth2AuthenticationToken authentication) {
        OAuth2AuthorizedClient client = authorizedClientService.loadAuthorizedClient(
                authentication.getAuthorizedClientRegistrationId(), authentication.getName());

        NetHttpTransport httpTransport = null;
        try {
            httpTransport = GoogleNetHttpTransport.newTrustedTransport();
        } catch (GeneralSecurityException | IOException e) {
            e.printStackTrace();
        }

        GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow
                .Builder(httpTransport, jsonFactory, mapToClientSecrets(client), SCOPES)
                .build();

        Credential credential = null;
        try {
            credential = flow.createAndStoreCredential(
                    createTokenResponse(client), authentication.getName());
        } catch (IOException e) {
            e.printStackTrace();
        }

        return new Calendar
                .Builder(Objects.requireNonNull(httpTransport), jsonFactory, credential)
                .setApplicationName(APPLICATION_NAME)
                .build();
    }

    private TokenResponse createTokenResponse(OAuth2AuthorizedClient client) {
        TokenResponse response = new TokenResponse();
        response.setAccessToken(client.getAccessToken().getTokenValue());
        if (client.getRefreshToken() != null) {
            response.setRefreshToken(client.getRefreshToken().getTokenValue());
        }

        Duration duration = Duration.between(Instant.now(), client.getAccessToken().getExpiresAt());
        response.setExpiresInSeconds(duration.getSeconds());

        response.setTokenType(client.getAccessToken().getTokenType().getValue());
        response.setScope(client.getAccessToken().getScopes().toString());
        return response;
    }

    private GoogleClientSecrets mapToClientSecrets(OAuth2AuthorizedClient client) {
        GoogleClientSecrets.Details web = new GoogleClientSecrets.Details();
        web.setClientId(client.getClientRegistration().getClientId());
        web.setClientSecret(client.getClientRegistration().getClientSecret());
        web.setAuthUri(client.getClientRegistration().getProviderDetails().getAuthorizationUri());
        web.setTokenUri(client.getClientRegistration().getProviderDetails().getTokenUri());
        return new GoogleClientSecrets().setWeb(web);
    }
}
