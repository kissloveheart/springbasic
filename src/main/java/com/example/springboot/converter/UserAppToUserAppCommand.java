package com.example.springboot.converter;

import com.example.springboot.Service.RoleService;
import com.example.springboot.command.UserAppCommand;
import com.example.springboot.model.UserApp;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

@Component
public class UserAppToUserAppCommand implements Converter<UserApp, UserAppCommand> {
    private final RoleService roleService;

    public UserAppToUserAppCommand(RoleService roleService) {
        this.roleService = roleService;
    }
    @Synchronized
    @Nullable
    @Override
    public UserAppCommand convert(UserApp source) {
        final UserAppCommand command = new UserAppCommand();
        command.setId(source.getId());
        command.setEmail(source.getEmail());
        command.setPassword(null);
        command.setCreateDate(source.getCreateDate());
        command.setEnabled(source.isEnabled());

        if (source.getRoleSet()!= null && source.getRoleSet().size() > 0) {
            source.getRoleSet().
                    forEach(role -> command.getRoleNameSet().add(role.getRoleName()));
        }

        return command;
    }

}
