package com.example.springboot.Service;

import com.example.springboot.model.Role;
import com.example.springboot.repository.RoleRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;

@ExtendWith(SpringExtension.class)
class RoleServiceImplTest {
    /* Tạo ra trong Context một Bean*/

    @TestConfiguration
    static class testConfiguralation{
        @Bean
        RoleServiceImpl roleService() {
            return new RoleServiceImpl();
        }
    }

    @MockBean
    RoleRepository roleRepository;

    @Autowired
    RoleServiceImpl roleService;

    @Test
    void findByRoleName() {
        Role role = new Role();
        role.setRoleName("ADMIN");
        Optional<Role> roleOptional = Optional.of(role);
        Mockito.when(roleRepository.findByRoleName(anyString())).thenReturn(roleOptional);
        Assertions.assertNotNull(roleService.findByRoleName(anyString()),"return role null");
    }
}