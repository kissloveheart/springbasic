package com.example.springboot.Service;


import com.example.springboot.command.UserAppCommand;
import com.example.springboot.model.UserApp;

import java.util.Set;

public interface UserAppService {
    Set<UserApp> getUserApps();
    UserApp findById(Long Id);
    UserApp findByEmail(String  email);

    UserAppCommand findCommandById(Long id);
    UserAppCommand findCommandByEmail(String email);
    UserAppCommand saveUserAppCommand(UserAppCommand userAppCommand);

}
