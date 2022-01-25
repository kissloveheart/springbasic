package com.example.springboot.service;

import com.example.springboot.model.Role;
import com.example.springboot.model.UserApp;

import java.util.Set;

public interface RoleService {
    Set<Role> getRoleListFromUserApp(UserApp userApp);
    void deleteById(Long id);
    Role findByRoleName(String role);

}
