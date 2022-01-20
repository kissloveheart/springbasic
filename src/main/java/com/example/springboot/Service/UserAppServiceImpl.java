package com.example.springboot.Service;

import com.example.springboot.command.UserAppCommand;
import com.example.springboot.converter.UserAppToUserAppCommand;
import com.example.springboot.model.UserApp;
import com.example.springboot.repository.UserAppRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
public class UserAppServiceImpl implements UserAppService{
    private final UserAppRepository userAppRepository;
    private final UserAppToUserAppCommand userAppToUserAppCommand;

    public UserAppServiceImpl(UserAppRepository userAppRepository, UserAppToUserAppCommand userAppToUserAppCommand) {
        this.userAppRepository = userAppRepository;
        this.userAppToUserAppCommand = userAppToUserAppCommand;
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
            throw new RuntimeException("UserApp is not found");
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
        userApp.getRoleSet().forEach(userApp::addRole);
        return userAppToUserAppCommand.convert(userApp);
    }

    @Override
    public void save(UserApp userApp) {

    }
}
