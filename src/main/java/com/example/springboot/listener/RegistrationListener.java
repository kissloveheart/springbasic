package com.example.springboot.listener;

import com.example.springboot.model.UserApp;
import com.example.springboot.service.MailService;
import com.example.springboot.service.UserAppService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;
import java.util.UUID;
@Slf4j
@Component
public class RegistrationListener implements ApplicationListener<OnRegistrationCompleteEvent> {
    @Autowired
    private UserAppService userAppService;
    @Autowired
    MailService mailService;

    @Override
    @Async
    public void onApplicationEvent(OnRegistrationCompleteEvent event) {
        UserApp userApp = userAppService.findByEmail(event.getUserAppCommand().getEmail());
        String token = UUID.randomUUID().toString();
        userAppService.createVerificationToken(userApp,token);
        String name = userApp.getEmail().substring(0,userApp.getEmail().indexOf("@"));
        String confirmationUrl = event.getAppUrl() + "/confirm/" + token;

        try {
            mailService.sendRegisMail(name,confirmationUrl,userApp.getEmail(),event.getLocale());
        } catch (MessagingException e) {
            log.info("Email sent unsuccessfully");
        }
    }
}
