package com.example.springboot.controller;

import com.example.springboot.service.UserAppService;
import com.example.springboot.command.UserAppCommand;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.nio.charset.Charset;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.hasSize;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Slf4j
class ApiControllerTest {
    public static final MediaType APPLICATION_JSON_UTF8 = new MediaType(MediaType.APPLICATION_JSON.getType(),
            MediaType.APPLICATION_JSON.getSubtype(), Charset.forName("utf8"));
    @Mock
    UserAppService userAppService;
    MockMvc mvc;
    ApiController apiController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        apiController = new ApiController(userAppService);
        mvc = MockMvcBuilders.standaloneSetup(apiController).build();
    }

    @Test
    public void getUser() throws Exception {
        UserAppCommand command = new UserAppCommand();
        command.setEmail("admin@admin.com");
        command.setId(1L);
        // giả lập trả về user app
        given(userAppService.findCommandByEmail(anyString())).willReturn(command);

        MvcResult result = mvc.perform(get("/api/get/admin@admin.com").contentType(APPLICATION_JSON_UTF8))
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn();
        String resultBody = result.getResponse().getContentAsString();
        log.info(resultBody);
    }

    @Test
    public void regisUser() throws Exception{
        UserAppCommand command = new UserAppCommand();
        command.setEmail("test@test.com");
        command.setPassword("123456789");
        command.setEnabled(true);
        command.setId(2L);

        given(userAppService.saveUserAppCommand(any())).willReturn(command);

        MvcResult result = mvc.perform((post("/api/post").flashAttr("userAppCommand",command)))
                .andExpect(status().isOk())
                .andReturn();
        String resultBody = result.getResponse().getContentAsString();
        log.info(resultBody);
    }
}