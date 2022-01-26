package com.example.springboot.controller;

import com.example.springboot.command.UserAppCommand;
import com.example.springboot.service.UserAppService;
import com.example.springboot.utils.SecurityUtils;
import com.example.springboot.utils.UserUtils;
import com.example.springboot.validator.UserAppCommandValidator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.security.Principal;

@Controller
@Slf4j
public class WebController {
    @Autowired
    private UserAppService userAppService;
    @Autowired
    private SecurityUtils securityUtils;
    @Autowired
    private UserAppCommandValidator userAppCommandValidator;

    @InitBinder("userApp")
    public void customizeBinding (WebDataBinder binder){
        Object target = binder.getTarget();
        if (target == null) {
            return;
        }
        if (target.getClass() == UserAppCommand.class) {
            binder.setValidator(userAppCommandValidator);
        }
    }

    @GetMapping({"/","/index"})
    public String index(Model model){
        model.addAttribute("text"," guys!");
        return "/web/index";
    }

    @GetMapping("/login")
    public String loginPage(){
        return "/web/loginPage";
    }

    @GetMapping("/regis")
    public  String regisPage(Model model){
        model.addAttribute("userApp", new UserAppCommand());
        return "/web/regisPage";
    }

    @PostMapping("/regis")
    public String regis(@ModelAttribute("userApp") @Validated UserAppCommand userAppCommand
                        , BindingResult result){
        // Validate result
        if(result.hasErrors()){
            return "/web/regisPage";
        }
        UserAppCommand saveApp = userAppService.saveUserAppCommand(userAppCommand);
        securityUtils.autoLogin(saveApp);
        return "redirect:/userInfo";
    }

    @GetMapping("/userInfo")
    public String userInfo(Model model, Principal principal){
        String email = principal.getName();
        log.info("The user "+email+" login successfully");
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
