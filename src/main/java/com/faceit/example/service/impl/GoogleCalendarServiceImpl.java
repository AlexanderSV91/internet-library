package com.faceit.example.service.impl;

import com.faceit.example.configuration.OffsetDateTimeCombinedSerializer;
import com.faceit.example.dto.request.googlecalendar.EventRequest;
import com.faceit.example.dto.response.googleevent.EventResponse;
import com.faceit.example.dto.response.googleevent.EventsResponse;
import com.faceit.example.mapper.googleevent.EventMapper;
import com.faceit.example.service.GoogleCalendarService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.auth.oauth2.TokenResponse;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.calendar.Calendar;
import com.google.api.services.calendar.CalendarScopes;
import com.google.api.services.calendar.model.Event;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.time.Duration;
import java.time.Instant;
import java.time.OffsetDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class GoogleCalendarServiceImpl implements GoogleCalendarService {

    private static final String REQUEST_URI_EVENTS = "https://www.googleapis.com/calendar/v3/calendars/primary/events";

    private final RestTemplate restTemplate;
    private final EventMapper eventMapper;

    private final OAuth2AuthorizedClientService authorizedClientService;
    private static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();
    private static final String CALENDAR_ID = "primary";
    private static final List<String> SCOPES = Collections.singletonList(CalendarScopes.CALENDAR_EVENTS);

    @Override
    public EventsResponse getEvents(String accessToken) {
        HttpHeaders headers = buildHttpHeaders(accessToken);
        ResponseEntity<EventsResponse> exchange = restTemplate
                .exchange(REQUEST_URI_EVENTS, HttpMethod.GET, new HttpEntity<>(headers), EventsResponse.class);
        return exchange.getBody();
    }

    @Override
    public Event getEvent(OAuth2AuthenticationToken authentication, String id) {
        Calendar service = this.prepareCalendarService(authentication);

        Event event = null;
        try {
            event = service.events().get("primary", id).execute();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return event;
    }

    @Override
    public EventResponse addEvent(String accessToken, EventRequest eventRequest) {
        String json = eventRequestToJson(eventRequest);

        HttpHeaders headers = buildHttpHeaders(accessToken);
        ResponseEntity<EventResponse> exchange = restTemplate
                .exchange(REQUEST_URI_EVENTS, HttpMethod.POST, new HttpEntity<>(json, headers), EventResponse.class);
        return exchange.getBody();
    }

    @Override
    public EventResponse updateEvent(String accessToken, String id, EventRequest eventRequest) {
/*        EventRequest event = eventMapper.eventToEventRequest(getEvent(accessToken, id));
        if (event == null) {
            throw new RuntimeException();
        } else {
            event = eventMapper.updateEventRequestFromEvent(eventRequest, event);
        }

        String json = eventRequestToJson(event);

        HttpHeaders headers = buildHttpHeaders(accessToken);
        ResponseEntity<EventResponse> exchange = restTemplate
                .exchange(REQUEST_URI_EVENTS + "/" + id, HttpMethod.PUT, new HttpEntity<>(json, headers), EventResponse.class);
        return exchange.getBody();*/
        return null;
    }

    @Override
    public void deleteEvent(String accessToken, String id) {
        HttpHeaders headers = buildHttpHeaders(accessToken);
        ResponseEntity<Void> exchange = restTemplate
                .exchange(REQUEST_URI_EVENTS + "/" + id, HttpMethod.DELETE, new HttpEntity<>(headers), Void.class);
        if (HttpStatus.NO_CONTENT != exchange.getStatusCode()) {
            throw new RuntimeException();
        }
    }

    private String eventRequestToJson(EventRequest eventRequest) {
        ObjectMapper objectMapper = buildObjectMapper();

        String json = null;
        try {
            json = objectMapper.writeValueAsString(eventRequest);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return json;
    }

    private ObjectMapper buildObjectMapper() {
        ObjectMapper objectMapper = new ObjectMapper();
        SimpleModule simpleModule = new SimpleModule();
        simpleModule.addSerializer(OffsetDateTime.class, new OffsetDateTimeCombinedSerializer.OffsetDateTimeSerializer());
        objectMapper.registerModule(simpleModule);
        return objectMapper;
    }

    private HttpHeaders buildHttpHeaders(String accessToken) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add("Authorization", "Bearer " + accessToken);
        return headers;
    }

    private Calendar prepareCalendarService(OAuth2AuthenticationToken authentication) {
        // Get authorized client through Oauth2 client service
        OAuth2AuthorizedClient client = this.authorizedClientService
                .loadAuthorizedClient(
                        authentication.getAuthorizedClientRegistrationId(),
                        authentication.getName());

        NetHttpTransport HTTP_TRANSPORT = null;
        try {
            HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
        } catch (GeneralSecurityException | IOException e) {
            e.printStackTrace();
        }

        // Build flow and trigger user authorization request.
        GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow
                .Builder(HTTP_TRANSPORT, JSON_FACTORY, this.mapToClientSecrets(client), SCOPES)
                .build();

        // Build calendar and set application name, token, refresh token response
        Credential credential = null;
        try {
            credential = flow.createAndStoreCredential(this.createTokenResponse(client), authentication.getName());
        } catch (IOException e) {
            e.printStackTrace();
        }


        return new Calendar
                .Builder(Objects.requireNonNull(HTTP_TRANSPORT), JSON_FACTORY, credential)
                .setApplicationName("internet-library")
                .build();
    }

    private TokenResponse createTokenResponse(OAuth2AuthorizedClient client) {
        TokenResponse response = new TokenResponse();
        response.setAccessToken(client.getAccessToken().getTokenValue());
        if (client.getRefreshToken() != null)
            response.setRefreshToken(client.getRefreshToken().getTokenValue());

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
