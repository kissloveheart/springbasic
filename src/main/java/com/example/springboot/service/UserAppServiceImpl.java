package com.example.springboot.service;

import com.example.springboot.command.UserAppCommand;
import com.example.springboot.converter.UserAppCommandToUserApp;
import com.example.springboot.converter.UserAppToUserAppCommand;
import com.example.springboot.model.UserApp;
import com.example.springboot.model.VerificationToken;
import com.example.springboot.repository.TokenRepository;
import com.example.springboot.repository.UserAppRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.HashSet;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

@Service
@Slf4j
public class UserAppServiceImpl implements UserAppService{
    private final UserAppRepository userAppRepository;
    private final UserAppToUserAppCommand userAppToUserAppCommand;
    private final UserAppCommandToUserApp userAppCommandToUserApp;
    private final TokenRepository tokenRepository;

    public UserAppServiceImpl(UserAppRepository userAppRepository, UserAppToUserAppCommand userAppToUserAppCommand, UserAppCommandToUserApp userAppCommandToUserApp, TokenRepository tokenRepository) {
        this.userAppRepository = userAppRepository;
        this.userAppToUserAppCommand = userAppToUserAppCommand;
        this.userAppCommandToUserApp = userAppCommandToUserApp;
        this.tokenRepository = tokenRepository;
    }


    @Override
    public Set<UserApp> getUserApps() {
        Set<UserApp> userAppSet = new HashSet<>();
        userAppRepository.findAll().iterator().forEachRemaining(userAppSet::add);
        return userAppSet;
    }

    @Override
    public UserApp findById(Long Id) {
        Optional<UserApp> userAppOptional = userAppRepository.findById(Id);
        if(userAppOptional.isEmpty()){
            log.warn("UserApp is not found");
            return null;
        }
        return userAppOptional.get();
    }

    @Override
    public UserApp findByEmail(String email) {
        UserApp userApp = userAppRepository.findByEmail(email);
        if(userApp == null){
            log.warn("UserApp is not found");
            return null;
        }
        return userApp;
    }

    @Override
    public void save(UserApp userApp) {
        userAppRepository.save(userApp);
        log.info("Save successfully: "+userApp.getEmail());
    }

    @Override
    @Transactional
    public UserAppCommand findCommandById(Long id) {
        return userAppToUserAppCommand.convert(findById(id));
    }

    @Override
    @Transactional
    public UserAppCommand findCommandByEmail(String email) {
        UserApp userApp = findByEmail(email);
        if(userApp == null){
            return null;
        }
        userApp.getRoleSet().forEach(userApp::addRole);
        return userAppToUserAppCommand.convert(userApp);
    }

    @Override
    @Transactional
    public UserAppCommand saveUserAppCommand(UserAppCommand userAppCommand) {
        UserApp detachedUser = userAppCommandToUserApp.convert(userAppCommand);
        UserApp saveUser = userAppRepository.save(detachedUser);
        log.info("Save the user ID: "+saveUser.getId());
        return userAppToUserAppCommand.convert(saveUser);
    }

    @Override
    public Boolean deleteUser(Long id) {
        Optional<UserApp> userAppOptional = userAppRepository.findById(id);
        if (userAppOptional.isEmpty()){
            return false;
        }
        userAppRepository.deleteById(id);
        return true;
    }

    @Override
    public VerificationToken createVerificationToken(UserApp userApp, String token) {
        VerificationToken myToken = new VerificationToken(token, userApp);
        return tokenRepository.save(myToken);
    }

    @Override
    public VerificationToken getVerificationToken(String verificationToken) {
        return tokenRepository.findByToken(verificationToken);
    }

    @Override
    public void setEnableUserApp(VerificationToken verificationToken) {
        UserApp userApp = verificationToken.getUserApp();
        userApp.setEnabled(true);
        userApp.setVerificationToken(verificationToken);
        userAppRepository.save(userApp);
    }

    @Override
    public void depositCash(Double money) {
        UserApp userApp = getCurrentUserApp();
        double newCash = (userApp.getCash() + money) < 0? 0: userApp.getCash() + money;
        userApp.setCash(newCash);
        userAppRepository.save(userApp);
    }

    @CacheEvict(cacheNames = "users", key = "#email")
    public void clearCache(String email) {
        log.info("Clear cache successfully user " + email);
    }

    public UserApp getCurrentUserApp(){
        // Get user order
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String email;

        if(principal instanceof UserDetails) {
            email =((UserDetails) principal).getUsername();
        } else{
            log.info("User not login");
            return null;
        }
        return  userAppRepository.findByEmail(email);
    }

}
