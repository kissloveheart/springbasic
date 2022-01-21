package com.example.springboot.controller;

import com.example.springboot.Service.UserAppService;
import com.example.springboot.command.UserAppCommand;
import com.example.springboot.model.UserApp;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
@Slf4j
@RestController
@RequestMapping("/api")
public class ApiController {
    private final UserAppService userAppService;

    public ApiController(UserAppService userAppService) {
        this.userAppService = userAppService;
    }


    @GetMapping("/get/{email:^[A-Za-z0-9+_.-]+@.+$}")
    public ResponseEntity<UserAppCommand> getUser(@PathVariable String email){
        UserAppCommand command = userAppService.findCommandByEmail(email);
        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.CONTENT_TYPE, "application/json");
        return new ResponseEntity<>(command,headers,HttpStatus.OK);
    }

    @PostMapping("/post")
    public ResponseEntity<UserAppCommand> regisUser(@ModelAttribute UserAppCommand userAppCommand){
        if(userAppCommand== null){
            return null;
        }
        log.info("Email of user: "+userAppCommand.getEmail());
        UserAppCommand saveUser = userAppService.saveUserAppCommand(userAppCommand);
        log.info("User have been saved with ID: "+saveUser.getId());
        return new ResponseEntity<>(saveUser,HttpStatus.OK);
    }

    @PutMapping("/put")
    public ResponseEntity<UserAppCommand> updateUser(@ModelAttribute UserAppCommand userAppCommand){

        if(userAppCommand== null && userAppCommand.getEmail() == null){
            return null;
        }
        UserApp userApp = userAppService.findById(userAppCommand.getId());
        if(userApp == null){
            return  null;
        }
        log.info("Email of user: "+userAppCommand.getEmail());
        UserAppCommand saveUser = userAppService.saveUserAppCommand(userAppCommand);
        log.info("User have been saved with ID: "+saveUser.getId());
        return new ResponseEntity<>(saveUser,HttpStatus.OK);
    }



    @DeleteMapping("/del/{id:\\d+}")
    public ResponseEntity<String> delUser(@PathVariable String id){
        boolean isDel = userAppService.deleteUser(Long.parseLong(id));
        if (isDel){
            return new ResponseEntity<>("Deleted: "+id,HttpStatus.OK);
        }
        return new ResponseEntity<>("User "+id+" is not exist!",HttpStatus.OK);
    }

}
