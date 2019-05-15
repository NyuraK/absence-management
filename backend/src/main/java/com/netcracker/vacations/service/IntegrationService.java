package com.netcracker.vacations.service;

import com.google.api.client.auth.oauth2.AuthorizationCodeRequestUrl;
import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.auth.oauth2.TokenResponse;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.DateTime;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.calendar.Calendar;
import com.google.api.services.calendar.CalendarScopes;
import com.google.api.services.calendar.model.Event;
import com.google.api.services.calendar.model.EventDateTime;
import com.google.api.services.calendar.model.EventReminder;
import com.netcracker.vacations.domain.RequestEntity;
import com.netcracker.vacations.domain.UserEntity;
import com.netcracker.vacations.repository.RequestRepository;
import com.netcracker.vacations.repository.UserRepository;
import com.netcracker.vacations.security.MyUserPrincipal;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;


@Service
@Transactional
public class IntegrationService {

    private RequestRepository requestRepository;
    private UserRepository userRepository;

    public IntegrationService(RequestRepository requestRepository, UserRepository userRepository) {

        this.requestRepository = requestRepository;
        this.userRepository = userRepository;
    }

    @Value("${google.client.client-id}")
    private String clientId;
    @Value("${google.client.client-secret}")
    private String clientSecret;
    @Value("${google.client.redirectUri}")
    private String redirectURI;

    private static final String TOKENS_DIRECTORY_PATH = "tokens";
    private static final List<String> SCOPES = Collections.singletonList(CalendarScopes.CALENDAR);
    private static final String APPLICATION_NAME = "Absence Management-app";
    private static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();
    private static HttpTransport httpTransport;

    private GoogleAuthorizationCodeFlow flow;
    private String login;


    public void cancelIntegration() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserEntity currentUser = ((MyUserPrincipal) authentication.getPrincipal()).getUser();
        currentUser.setIntegrated(false);
        userRepository.save(currentUser);
    }

    public void insertEventsAfterApproval(List<Integer> requests) throws Exception {
        for (Integer id : requests) {
            RequestEntity requestEntity = requestRepository.findByRequestsId(id).get(0);
            Credential credential = getStoredCredential(requestEntity.getUser().getLogin());
            if (credential != null && requestEntity.getUser().getIntegrated()) {
                Calendar service = new Calendar.Builder(httpTransport, JSON_FACTORY, credential)
                        .setApplicationName(APPLICATION_NAME)
                        .build();
                String calendarId = "primary";
                Event event = service.events().insert(calendarId, makeEvent(requestEntity)).execute();
                requestEntity.setGoogleId(event.getId());
                requestRepository.save(requestEntity);
            }
        }
    }


    public void insertEvents(String code) throws Exception {
        Calendar service = getCalendarService(code);
        String calendarId = "primary";
        List<RequestEntity> requests = requestRepository.findAllByUserLogin(login);
        List<Event> eventsFromGoogle = service.events().list(calendarId).execute().getItems();
        boolean duplicate = false;
        for (RequestEntity entity : requests) {
            if (entity.getStatus().getName().equals("Accepted")) {
                if (entity.getGoogleId() == null) {
                    Event event = service.events().insert(calendarId, makeEvent(entity)).execute();
                    entity.setGoogleId(event.getId());
                    requestRepository.save(entity);
                } else {
                    for (Event googleEvent : eventsFromGoogle) {
                        if (googleEvent.getId().equals(entity.getGoogleId())) {
                            duplicate = true;
                            break;
                        }
                    }
                    if (!duplicate) {
                        Event event = service.events().insert(calendarId, makeEvent(entity)).execute();
                        entity.setGoogleId(event.getId());
                        requestRepository.save(entity);
                    }
                    duplicate = false;
                }
            }
        }
    }

    public Event makeEvent(RequestEntity requestEntity) {
        Event event = new Event()
                .setSummary(requestEntity.getTypeOfRequest().getName())
                .setDescription(requestEntity.getDescription());

        DateTime startDateTime = new DateTime(requestEntity.getBeginning().toString());
        EventDateTime start = new EventDateTime()
                .setDate(startDateTime);
        event.setStart(start);

        DateTime endDateTime = new DateTime(requestEntity.getEnding().toString());
        EventDateTime end = new EventDateTime()
                .setDate(endDateTime);
        event.setEnd(end);

        EventReminder[] reminderOverrides = new EventReminder[]{
                new EventReminder().setMethod("email").setMinutes(24 * 60),
                new EventReminder().setMethod("popup").setMinutes(10),
        };
        Event.Reminders reminders = new Event.Reminders()
                .setUseDefault(false)
                .setOverrides(Arrays.asList(reminderOverrides));
        event.setReminders(reminders);
        event.setColorId("3");
        return event;
    }

    public Calendar getCalendarService(String code) throws Exception {
        return new Calendar.Builder(httpTransport, JSON_FACTORY, getCredential(code))
                .setApplicationName(APPLICATION_NAME)
                .build();
    }

    public Credential getCredential(String code) throws Exception {
        UserEntity currentUser = userRepository.findByLogin(login).get(0);
        if (!currentUser.getIntegrated()) {
            currentUser.setIntegrated(true);
            userRepository.save(currentUser);
        }
        if (flow.loadCredential(login) != null) {
            System.out.println("!!! not null !!!");
            return flow.loadCredential(login);
        }
        TokenResponse response = flow.newTokenRequest(code).setRedirectUri(redirectURI).execute();
        System.out.println("!!! NULL !!!");
        return flow.createAndStoreCredential(response, login);
    }

    public Credential getStoredCredential(String login) throws Exception {
        String URL = getRedirectURL(login);
        return flow.loadCredential(login);
    }

    public String getRedirectURL(String login) throws Exception {
        this.login = login;
        if (flow == null) {
            GoogleClientSecrets.Details web = new GoogleClientSecrets.Details();
            web.setClientId(clientId);
            web.setClientSecret(clientSecret);
            GoogleClientSecrets clientSecrets = new GoogleClientSecrets().setWeb(web);
            httpTransport = GoogleNetHttpTransport.newTrustedTransport();
            // Build flow and trigger user authorization request.
            flow = new GoogleAuthorizationCodeFlow.Builder(
                    httpTransport, JSON_FACTORY, clientSecrets, SCOPES)
                    .setDataStoreFactory(new FileDataStoreFactory(new java.io.File(TOKENS_DIRECTORY_PATH)))
                    .setAccessType("offline")
                    .build();
            System.out.println("!!! flow is null !!!");
        }
        AuthorizationCodeRequestUrl authorizationUrl = flow.newAuthorizationUrl().setRedirectUri(redirectURI);
        return authorizationUrl.build();
    }

}
