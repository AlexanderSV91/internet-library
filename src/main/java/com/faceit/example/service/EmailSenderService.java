package com.faceit.example.service;

import com.faceit.example.tables.records.UsersRecord;
import freemarker.template.TemplateException;

import javax.mail.MessagingException;
import java.io.IOException;

public interface EmailSenderService {

    void sendActiveEmail(UsersRecord user, String token)
            throws MessagingException, IOException, TemplateException;
}
