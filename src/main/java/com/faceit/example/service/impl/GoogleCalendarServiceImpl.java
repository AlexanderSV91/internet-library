package com.faceit.example.service.impl;

import com.faceit.example.dto.request.googlecalendar.EventRequest;
import com.faceit.example.mapper.googleevent.EventMapper;
import com.faceit.example.service.GoogleCalendarService;
import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.auth.oauth2.TokenResponse;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.DateTime;
import com.google.api.client.util.Preconditions;
import com.google.api.client.util.Strings;
import com.google.api.services.calendar.Calendar;
import com.google.api.services.calendar.CalendarScopes;
import com.google.api.services.calendar.model.Event;
import com.google.api.services.calendar.model.EventDateTime;
import com.google.api.services.calendar.model.EventReminder;
import com.google.api.services.calendar.model.Events;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.security.GeneralSecurityException;
import java.time.Duration;
import java.time.Instant;
import java.util.*;

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

/*    private static final String APPLICATION_NAME = "serviceCal";
    private static HttpTransport httpTransport;
    private static final JsonFactory JSON_FACTORY = new JacksonFactory();
    private static com.google.api.services.calendar.Calendar client;

    URL url = getClass().getResource("auth.p12");
    File keyFile = new File(url.getPath());
    GoogleClientSecrets clientSecrets;
    GoogleAuthorizationCodeFlow flow;
    Credential credential;

    HttpTransport TRANSPORT;
    private String SERVICE_ACCOUNT = "test-825@openforum-307505.iam.gserviceaccount.com";

    private Set<Event> events = new HashSet<>();

    public void setEvents(Set<Event> events) {
        this.events = events;
    }

    public ResponseEntity<String> oauth2Callback(HttpServletRequest request) throws IOException, GeneralSecurityException {
        com.google.api.services.calendar.model.Events eventList;
        String message;

// 			TokenResponse response = flow.newTokenRequest(code).setRedirectUri(redirectURI).execute();
// 			credential = flow.createAndStoreCredential(response, "userID"); //for Oauth2
        Preconditions.checkArgument(!Strings.isNullOrEmpty(APPLICATION_NAME), "applicationName cannot be null or empty!");
        try {
            TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();

            credential = new GoogleCredential.Builder()
                    .setTransport(TRANSPORT)
                    .setJsonFactory(JSON_FACTORY)
                    .setServiceAccountId(SERVICE_ACCOUNT)
                    .setServiceAccountScopes(Collections.singleton(CalendarScopes.CALENDAR))
                    .setServiceAccountPrivateKeyFromP12File(keyFile)
                    .build();

            credential.refreshToken();
            client = new com.google.api.services.calendar.Calendar.Builder(TRANSPORT, JSON_FACTORY, credential).setApplicationName(APPLICATION_NAME).build();
            System.out.println(client);

            Calendar.Events events = client.events();
            eventList = events.list("primary").setTimeMin(date1).setTimeMax(date2).execute();
            message = eventList.getItems().toString();

            Event event = new Event()
                    .setSummary("Google I/O 2015")
                    .setLocation("800 Howard St., San Francisco, CA 94103")
                    .setDescription("A chance to hear more about Google's developer products.");

            DateTime startDateTime = new DateTime("2021-07-31T09:00:00-07:00");
            EventDateTime start = new EventDateTime()
                    .setDateTime(startDateTime)
                    .setTimeZone("America/Los_Angeles");
            event.setStart(start);

            DateTime endDateTime = new DateTime("2021-08-01T17:00:00-07:00");
            EventDateTime end = new EventDateTime()
                    .setDateTime(endDateTime)
                    .setTimeZone("America/Los_Angeles");
            event.setEnd(end);

            String[] recurrence = new String[]{"RRULE:FREQ=DAILY;COUNT=2"};
            event.setRecurrence(Arrays.asList(recurrence));
//			EventAttendee[] attendees = new EventAttendee[] {
// 				    new EventAttendee().setEmail("greatgatsbylala18@gmail.com"),
//				    new EventAttendee().setEmail("himanshuranjan3030@gmail.com"),
//				};
//		event.setAttendees(Arrays.asList(attendees));

            EventReminder[] reminderOverrides = new EventReminder[]{
                    new EventReminder().setMethod("email").setMinutes(24 * 60),
                    new EventReminder().setMethod("popup").setMinutes(10)};

            Event.Reminders reminders = new Event.Reminders()
                    .setUseDefault(false)
                    .setOverrides(Arrays.asList(reminderOverrides));
            event.setReminders(reminders);

            String calendarId = "primary";
            event = client.events().insert("putyouremailhere@gmail.com", event).execute();
            System.out.printf("Event created: %s\n", event.getHtmlLink());

            System.out.println("cal message:" + message);
            return new ResponseEntity<>(message, HttpStatus.OK);
        } catch (IOException e) {
            System.out.println(e);
        }
        return new ResponseEntity<>("Nothing", HttpStatus.OK);
    }*/
}
