package com.example.springboot.Service;

import com.example.springboot.command.UserAppCommand;
import com.example.springboot.converter.UserAppToUserAppCommand;
import com.example.springboot.model.UserApp;
import com.example.springboot.repository.UserAppRepository;
import org.junit.Before;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;


class UserAppServiceImplTest {

    @InjectMocks
    UserAppServiceImpl userAppService;
    @Mock
    UserAppRepository userAppRepository;
    @Mock
    UserAppToUserAppCommand userAppToUserAppCommand;


    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getUserApps()  {
        Mockito.when(userAppRepository.findAll()).
                thenReturn(IntStream.range(0,10).mapToObj(id -> new UserApp()).collect(Collectors.toSet()));
        Set<UserApp> userAppSet = userAppService.getUserApps();
        Assertions.assertEquals(userAppSet.size(),10);
        Assertions.assertTrue(userAppSet.stream().allMatch(Objects::nonNull),"some element is not instance of User App");
        verify(userAppRepository,times(1)).findAll();

        Mockito.when(userAppRepository.findAll()).thenReturn(Collections.EMPTY_SET);
        Assertions.assertNotNull(userAppService.getUserApps());

    }

    @Test
    void findById() {
        UserApp userApp = new UserApp();
        Optional<UserApp> userAppOptional = Optional.of(userApp);
        Mockito.when(userAppRepository.findById(anyLong())).thenReturn(userAppOptional);
        Assertions.assertNotNull(userAppService.findById(1L),"Null user app return");

        Mockito.when(userAppRepository.findById(anyLong())).thenReturn(Optional.empty());
        Assertions.assertNull(userAppService.findById(1L));
        verify(userAppRepository,times(2)).findById(anyLong());
    }


    @Test
    void findCommandByEmail() {
        UserApp userApp = new UserApp();
        Optional<UserApp> userAppOptional = Optional.of(userApp);
        Mockito.when(userAppRepository.findByEmail(anyString())).thenReturn(userAppOptional);

        UserAppCommand userAppCommand = new UserAppCommand();
        userAppCommand.setId(1L);
        Mockito.when(userAppToUserAppCommand.convert(any(UserApp.class))).thenReturn(userAppCommand);

        Assertions.assertNotNull(userAppService.findCommandByEmail(anyString()), "return null user app");

    }

}