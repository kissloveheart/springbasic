package com.example.springboot.Service;

import com.example.springboot.command.UserAppCommand;
import com.example.springboot.converter.UserAppCommandToUserApp;
import com.example.springboot.converter.UserAppToUserAppCommand;
import com.example.springboot.model.UserApp;
import com.example.springboot.repository.UserAppRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
@Slf4j
public class UserAppServiceImpl implements UserAppService{
    private final UserAppRepository userAppRepository;
    private final UserAppToUserAppCommand userAppToUserAppCommand;
    private final UserAppCommandToUserApp userAppCommandToUserApp;

    public UserAppServiceImpl(UserAppRepository userAppRepository, UserAppToUserAppCommand userAppToUserAppCommand, UserAppCommandToUserApp userAppCommandToUserApp) {
        this.userAppRepository = userAppRepository;
        this.userAppToUserAppCommand = userAppToUserAppCommand;
        this.userAppCommandToUserApp = userAppCommandToUserApp;
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
        if(!userAppOptional.isPresent()){
            //throw new RuntimeException("UserApp is not found");
            return null;
        }
        return userAppOptional.get();
    }

    @Override
    public UserApp findByEmail(String email) {
        Optional<UserApp> userAppOptional = userAppRepository.findByEmail(email);
        if(!userAppOptional.isPresent()){
            throw new RuntimeException("UserApp is not found");
        }
        return userAppOptional.get();
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
        if (!userAppOptional.isPresent()){
            return false;
        }
        userAppRepository.deleteById(id);
        return true;
    }
}
