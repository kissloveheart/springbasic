package com.example.springboot.utils;

import com.example.springboot.command.UserAppCommand;
import com.example.springboot.model.UserApp;
import com.example.springboot.repository.UserAppRepository;
import com.example.springboot.service.UserDetailsServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class SecurityUtils {
    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @Autowired
    private UserAppRepository userAppRepository;

    public void autoLogin(UserAppCommand userAppCommand){
        UserDetails userDetails= userDetailsService.loadUserByUsername(userAppCommand.getEmail());
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
                userDetails, null, userDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
    }
}
