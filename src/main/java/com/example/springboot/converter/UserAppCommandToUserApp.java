package com.example.springboot.converter;

import com.example.springboot.service.RoleService;
import com.example.springboot.command.UserAppCommand;
import com.example.springboot.model.UserApp;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class UserAppCommandToUserApp implements Converter<UserAppCommand, UserApp> {

    private final BCryptPasswordEncoder passwordEncoder;
    private final RoleService roleService;


    public UserAppCommandToUserApp(BCryptPasswordEncoder passwordEncoder, RoleService roleService) {
        this.passwordEncoder = passwordEncoder;
        this.roleService = roleService;
    }

    @Override
    public UserApp convert(UserAppCommand source) {

        final UserApp userApp = new UserApp();
        userApp.setId(source.getId());
        userApp.setEmail(source.getEmail());
        if (source.getPassword() != null){
            userApp.setEncryptedPassword(passwordEncoder.encode(source.getPassword()));
        }
        userApp.setCreateDate(source.getCreateDate());
        userApp.setEnabled(source.isEnabled());
        userApp.setCash(source.getCash());
        userApp.setAddress(source.getAddress());
        userApp.setPhoneNumber(source.getPhoneNumber());

        if (source.getRoleNameSet()!= null && source.getRoleNameSet().size() > 0) {
            source.getRoleNameSet().
                    forEach(roleName -> userApp.getRoleSet().add(roleService.findByRoleName(roleName)));
        }

        return userApp;
    }
}
