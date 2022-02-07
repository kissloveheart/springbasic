package com.example.springboot.controller;

import com.example.springboot.command.UserAppCommand;
import com.example.springboot.listener.OnRegistrationCompleteEvent;
import com.example.springboot.model.UserApp;
import com.example.springboot.model.VerificationToken;
import com.example.springboot.service.OrdersService;
import com.example.springboot.service.ProductService;
import com.example.springboot.service.UserAppService;
import com.example.springboot.session.CartInfo;
import com.example.springboot.utils.SecurityUtils;
import com.example.springboot.utils.SessionUtil;
import com.example.springboot.utils.UserUtils;
import com.example.springboot.validator.UserAppCommandValidator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.Calendar;

@Controller
@Slf4j
public class WebController {
    @Autowired
    ApplicationEventPublisher eventPublisher;
    @Autowired
    private UserAppService userAppService;
    @Autowired
    private SecurityUtils securityUtils;
    @Autowired
    private UserAppCommandValidator userAppCommandValidator;
    @Autowired
    private ProductService productService;
    @Autowired
    private OrdersService ordersService;

    @InitBinder("userApp")
    public void customizeBinding(WebDataBinder binder) {
        Object target = binder.getTarget();
        if (target == null) {
            return;
        }
        if (target.getClass() == UserAppCommand.class) {
            binder.setValidator(userAppCommandValidator);
        }
    }

    @GetMapping({"/", "/index"})
    public String index(Model model, HttpServletRequest request) {
        model.addAttribute("text", " guys!");
        model.addAttribute("products",productService.findAllProducts());
        CartInfo myCart = SessionUtil.getCartInSession(request);
        model.addAttribute("cartForm", myCart);
        return "/web/index";
    }

    @GetMapping("/login")
    public String loginPage() {
        return "/web/loginPage";
    }

    @GetMapping("/regis")
    public String regisPage(Model model) {
        model.addAttribute("userApp", new UserAppCommand());
        return "/web/regisPage";
    }

    @PostMapping("/regis")
    public String regis(@ModelAttribute("userApp") @Validated UserAppCommand userAppCommand
            , BindingResult result, HttpServletRequest request,Model model) {
        // Validate result
        if (result.hasErrors()) {
            return "/web/regisPage";
        }
        UserAppCommand saveApp = userAppService.saveUserAppCommand(userAppCommand);
        //
        String appUrl = String.valueOf(request.getRequestURL());
        eventPublisher.publishEvent(new OnRegistrationCompleteEvent(saveApp,
                request.getLocale(), appUrl));
        model.addAttribute("message",
                "Registration successfully. Please active account on your email!");
        return "/web/regisNotify";
    }

    @GetMapping("/regis/confirm/{token:[A-Za-z0-9-]{36}}")
    public String confirmRegis(Model model, @PathVariable String token, HttpServletRequest request) {
        VerificationToken verificationToken = userAppService.getVerificationToken(token);

        if (verificationToken == null) {
            model.addAttribute("message", "Invalid token");
            return "/web/error";
        }

        UserApp userApp = verificationToken.getUserApp();
        Calendar cal = Calendar.getInstance();

        if ((verificationToken.getExpiryDate().getTime() - cal.getTime().getTime()) <= 0) {
            model.addAttribute("message", "Your registration token has expired. Please register again.");
            return "/web/error";
        }

        userApp.setEnabled(true);
        userAppService.save(userApp);
        model.addAttribute("message", "Active account successfully");
        return "/web/loginPage";
    }

    @GetMapping("/userInfo")
    public String userInfo(Model model, Principal principal) {
        String email = principal.getName();
        log.info("The user " + email + " login successfully");
        User loginUser = (User) ((Authentication) principal).getPrincipal();
        String userInfo = UserUtils.userToString(loginUser);
        model.addAttribute("userInfo", userInfo);

        model.addAttribute("orders", ordersService.getOrderListByUser());
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
    public String adminPage(Model model, Principal principal) {
        User loginUser = (User) ((Authentication) principal).getPrincipal();
        String userInfo = UserUtils.userToString(loginUser);
        model.addAttribute("userInfo", userInfo);
        return "/web/adminPage";
    }


}
