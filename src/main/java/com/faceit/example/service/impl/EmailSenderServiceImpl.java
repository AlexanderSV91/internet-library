package com.faceit.example.service.impl;

import com.faceit.example.service.EmailSenderService;
import com.faceit.example.tables.records.UsersRecord;
import com.faceit.example.util.Utils;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class EmailSenderServiceImpl implements EmailSenderService {

    private static final String CONFIRMATION_URL = "confirm/";
    private static final String NAME_TEMPLATE = "activeEmail.ftl";

    @Value(value = "${server.host}")
    private String serverHost;

    private final JavaMailSenderImpl mailSender;
    private final FreeMarkerConfigurer markerConfigurer;

    @Async("threadPoolTaskExecutorSendMail")
    @Override
    public void sendActiveEmail(UsersRecord user, String token)
            throws MessagingException, IOException, TemplateException {
        String subject = Utils.getMessageForLocale("activeEmail.mailConfirmationLink");

        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true, "UTF-8");
        mimeMessageHelper.setFrom("<MAIL>");
        mimeMessageHelper.setTo(user.getEmail());
        mimeMessageHelper.setSubject(subject);

        String s = getTemplate(user, token);
        mimeMessageHelper.setText(s, true);
        mailSender.send(mimeMessage);
    }

    private String getTemplate(UsersRecord user, String token) throws IOException, TemplateException {
        String activeLink = serverHost + CONFIRMATION_URL + token;
        String newUser = Utils.getMessageForLocale("activeEmail.newUser");
        String copyright = Utils.getMessageForLocale("activeEmail.copyright");
        String yourToken = Utils.getMessageForLocale("activeEmail.yourToken");
        String internetLibrary = Utils.getMessageForLocale("activeEmail.internetLibrary");
        String confirmRegistration = Utils.getMessageForLocale("activeEmail.confirmRegistration");

        Map<String, String> map = new HashMap<>();
        map.put("userToken", token);
        map.put("activeLink", activeLink);
        map.put("userName", user.getUsername());
        map.put("newUser", newUser);
        map.put("copyright", copyright);
        map.put("yourToken", yourToken);
        map.put("internetLibrary", internetLibrary);
        map.put("confirmRegistration", confirmRegistration);

        Configuration configuration = markerConfigurer.getConfiguration();
        Template template = configuration.getTemplate(NAME_TEMPLATE, "UTF-8");

        StringWriter stringWriter = new StringWriter();
        template.process(map, stringWriter);
        stringWriter.flush();
        return stringWriter.toString();
    }
}
