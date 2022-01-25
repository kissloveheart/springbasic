package com.example.springboot.Service;

import com.example.springboot.command.UserAppCommand;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class UserAppServiceTest {
    @Autowired
    private UserAppService userAppService;

    @Test
    void saveUserAppCommand() {
        UserAppCommand command = new UserAppCommand();
        command.setEmail("test@test.com");
        command.setPassword("12345678");
        UserAppCommand saveCommand = userAppService.saveUserAppCommand(command);
        Assertions.assertEquals("test@test.com",saveCommand.getEmail());
    }
}