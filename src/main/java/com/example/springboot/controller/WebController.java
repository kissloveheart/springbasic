package com.example.springboot.controller;

import com.example.springboot.utils.UserUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;

@Controller
@Slf4j
public class WebController {
    @GetMapping({"/","/index"})
    public String index(Model model){
        model.addAttribute("text"," guys!");
        return "/web/index";
    }

    @GetMapping("/login")
    public String loginPage(){
        return "/web/loginPage";
    }

    @GetMapping("/userInfo")
    public String userInfo(Model model, Principal principal){
        String email = principal.getName();
        log.info("The user "+email+" login successfully");
/*
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (principal instanceof UserDetails) {
            String username = ((UserDetails) principal).getUsername();
        } else {
            String username = principal.toString();
        }
 */
        User loginUser = (User) ((Authentication) principal).getPrincipal();
        String userInfo = UserUtils.userToString(loginUser);
        model.addAttribute("userInfo", userInfo);
        return "/web/userInfo";

    }

    @GetMapping("/logoutSuccessful")
    public String logoutSuccessfulPage(Model model) {
        model.addAttribute("title", "Logout");
        return "/web/logoutSuccessfulPage";
    }

    @GetMapping("/403")
    public String accessDenied(Model model, Principal principal) {

        if (principal != null) {
            User loginedUser = (User) ((Authentication) principal).getPrincipal();

            String userInfo = UserUtils.userToString(loginedUser);

            model.addAttribute("userInfo", userInfo);

            String message = "Hi " + principal.getName() //
                    + "<br> You do not have permission to access this page!";
            model.addAttribute("message", message);

        }

        return "/web/403Page";
    }

    @GetMapping("/admin")
    public String adminPage(Model model, Principal principal){
        User loginUser = (User) ((Authentication) principal).getPrincipal();
        String userInfo = UserUtils.userToString(loginUser);
        model.addAttribute("userInfo", userInfo);
        return "/web/adminPage";
    }


}
