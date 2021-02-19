package com.faceit.example.service.impl;

import com.faceit.example.service.EmailSenderService;
import com.faceit.example.tables.records.UsersRecord;
import freemarker.template.TemplateException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import java.io.IOException;

@Service
@RequiredArgsConstructor
public class EmailSenderServiceImpl implements EmailSenderService {

    @Override
    public void sendActiveEmail(UsersRecord user, String token)
            throws MessagingException, IOException, TemplateException {

    }
}
