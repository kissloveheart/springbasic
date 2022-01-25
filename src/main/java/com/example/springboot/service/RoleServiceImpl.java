package com.example.springboot.Service;

import com.example.springboot.model.Role;
import com.example.springboot.model.UserApp;
import com.example.springboot.repository.RoleRepository;
import lombok.extern.log4j.Log4j;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
@Slf4j
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleRepository roleRepository;


    @Override
    public Set<Role> getRoleListFromUserApp(UserApp userApp) {
        Set<Role> roleSet = new HashSet<>();
        userApp.getRoleSet().forEach(role -> {
            Optional<Role> roleOptional = roleRepository.findById(role.getId());
            if (!roleOptional.isPresent()) {
                throw new RuntimeException("Role not found");
            }
            roleSet.add(roleOptional.get());
        });
        return roleSet;
    }

    @Override
    public void deleteById(Long id) {
        roleRepository.deleteById(id);
    }

    @Override
    public Role findByRoleName(String roleName) {
        Optional<Role> roleOptional = roleRepository.findByRoleName(roleName);
        if (!roleOptional.isPresent()) {
            throw new RuntimeException("Role is not found!");
        }
        return roleOptional.get();
    }
}
