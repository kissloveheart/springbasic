package com.example.springboot.service;


import com.example.springboot.command.UserAppCommand;
import com.example.springboot.model.UserApp;
import com.example.springboot.model.VerificationToken;
import org.springframework.cache.annotation.Cacheable;

import java.util.Set;

public interface UserAppService {
    Set<UserApp> getUserApps();
    UserApp findById(Long Id);

    UserApp findByEmail(String  email);
    void save(UserApp userApp);

    UserAppCommand findCommandById(Long id);
    UserAppCommand findCommandByEmail(String email);
    UserAppCommand saveUserAppCommand(UserAppCommand userAppCommand);

    Boolean deleteUser(Long id);

    VerificationToken createVerificationToken(UserApp userApp, String token);

    VerificationToken getVerificationToken(String VerificationToken);
    void setEnableUserApp(VerificationToken verificationToken);
    void depositCash(Double money);

     UserApp getCurrentUserApp();

     void clearCache(String email);

}
