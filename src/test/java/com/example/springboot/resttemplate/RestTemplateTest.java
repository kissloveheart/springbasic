package com.example.springboot.resttemplate;

import com.example.springboot.command.UserAppCommand;
import com.example.springboot.jwt.JwtRequest;
import com.example.springboot.model.UserApp;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;

@Slf4j
public class RestTemplateTest {
    static final String API_URL = "http://localhost:8080/api/";
    static String token;
    static RestTemplate restTemplate = new RestTemplate();
    static HttpHeaders headers = new HttpHeaders();

    @BeforeAll
    static void getToken(){
        JwtRequest jwtRequest = new JwtRequest();
        jwtRequest.setEmail("admin@admin.com");
        jwtRequest.setPassword("12345");

        // Dữ liệu đính kèm theo yêu cầu.
        HttpEntity<JwtRequest> requestBody = new HttpEntity<>(jwtRequest);

        // Gửi yêu cầu với phương thức POST.
        ResponseEntity<String> result
                = restTemplate.postForEntity(API_URL+"login", requestBody, String.class);

        log.info("Status code:" + result.getStatusCode());

        // Code = 200.
        if (result.getStatusCode() == HttpStatus.OK) {
            token ="Bearer " + result.getBody();
            log.info("Token authorize: "+token);
            headers.setAccept(Arrays.asList(new MediaType[] { MediaType.APPLICATION_JSON }));
            // Yêu cầu trả về định dạng JSON
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.set("Authorization", token);
        }
    }

    @Test
    void getExchange(){

        // HttpEntity<String>
        HttpEntity<String> entity = new HttpEntity<>(headers);

        // Gửi yêu cầu với phương thức GET, và các thông tin Headers.
        ResponseEntity<UserApp> response = restTemplate.exchange(API_URL+"get/admin@admin.com",
                HttpMethod.GET, entity, UserApp.class);
        UserApp userApp = response.getBody();
        log.info(userApp.toString());
        Assertions.assertEquals("admin@admin.com",userApp.getEmail());
        Assertions.assertEquals(5000D,userApp.getCash());
    }

    @Test
    void deleteExchange(){
        HttpEntity<String> entity = new HttpEntity<>(headers);
        ResponseEntity<String> response =
                restTemplate.exchange(API_URL+"del/3", HttpMethod.DELETE,entity,String.class);
        log.info(response.getBody());
        Assertions.assertTrue(response.getBody().contains("3"));
    }

    @Test
    void postExchange(){
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> map= new LinkedMultiValueMap<>();
        map.add("email", "test1@test.com");
        map.add("password","12345");

        // Dữ liệu đính kèm theo yêu cầu.
        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(map,headers);

        // Gửi yêu cầu với phương thức POST.
        ResponseEntity<UserAppCommand> result
                = restTemplate.exchange(API_URL+"post",HttpMethod.POST, request, UserAppCommand.class);

        Assertions.assertEquals("test@test.com",result.getBody().getEmail());
    }




}
