package com.example.springboot.validator;

import com.example.springboot.command.UserAppCommand;
import com.example.springboot.model.UserApp;
import com.example.springboot.service.UserAppService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.validator.routines.EmailValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component
@Slf4j
public class UserAppCommandValidator implements Validator {
    private EmailValidator emailValidator = EmailValidator.getInstance();
    @Autowired private UserAppService userAppService;

    @Override
    public boolean supports(Class<?> clazz) {
        return clazz == UserAppCommand.class;
    }

    @Override
    public void validate(Object target, Errors errors) {
        UserAppCommand userAppCommand = (UserAppCommand) target;

        // check field of campaignForm.
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "email","NotEmpty.userAppCommand.email");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password","NotEmpty.userAppCommand.password");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "rePassword","NotEmpty.userAppCommand.rePassword");

        if (!errors.hasFieldErrors("email")) {
            if(!emailValidator.isValid(userAppCommand.getEmail())){
                log.info("Email không hợp lệ");
                errors.rejectValue("email", "Pattern.userAppCommand.email");
            } else if(userAppCommand.getId() == null){
                UserApp userApp = userAppService.findByEmail(userAppCommand.getEmail());
                if(userApp != null){
                    log.info("Email đã được sử dụng bởi tài khoản khác");
                    errors.rejectValue("email", "Duplicate.userAppCommand.email");
                };
            }
        }

        if (!errors.hasErrors()) {
            if (!userAppCommand.getPassword().equals(userAppCommand.getRePassword())) {
                errors.rejectValue("rePassword", "Match.userAppCommand.rePassword");
            }
        }

    }
}
