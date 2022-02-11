package com.example.springboot.repository;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;

@DataJpaTest
@Sql("/dataOrder.sql")
@Slf4j
class UserAppRepositoryTest {

    @Autowired
    UserAppRepository userAppRepository;

    @Test
    void findAllUserAppInfoNative() {
        userAppRepository.findAllUserAppInfoNative().
                forEach(iUserALLInfoDTO -> log.info(iUserALLInfoDTO.getEmail() +" + "+ iUserALLInfoDTO.getToken() +" + "+
                        iUserALLInfoDTO.getRoleName() +" + "+ iUserALLInfoDTO.getTotalAmount()));
    }

    @Test
    void findAllUserAppInfo(){
        userAppRepository.findAllUserAppInfo().
                forEach(iUserALLInfoDTO -> log.info(iUserALLInfoDTO.getEmail() +" + "+ iUserALLInfoDTO.getToken() +" + "+
                        iUserALLInfoDTO.getRoleName() +" + "+ iUserALLInfoDTO.getTotalAmount() +" + "+
                iUserALLInfoDTO.getProductName() +" + "+ iUserALLInfoDTO.getProductPrice()));
    }
}