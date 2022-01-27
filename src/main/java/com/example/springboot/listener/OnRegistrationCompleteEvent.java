package com.example.springboot.listener;

import com.example.springboot.command.UserAppCommand;
import com.example.springboot.model.UserApp;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.context.ApplicationEvent;

import java.util.Locale;


@Getter
@Setter
public class OnRegistrationCompleteEvent extends ApplicationEvent {
    private String appUrl;
    private Locale locale;
    private UserAppCommand userAppCommand;

    public OnRegistrationCompleteEvent(
            UserAppCommand userAppCommand, Locale locale, String appUrl) {
        super(userAppCommand);

        this.userAppCommand = userAppCommand;
        this.locale = locale;
        this.appUrl = appUrl;
    }

}
