package com.example.springboot.service;

import com.example.springboot.model.Role;
import com.example.springboot.model.UserApp;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Slf4j
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    private UserAppService userAppService;
    private static final boolean enabled = true;
    private static final boolean accountNonExpired = true;
    private static final boolean credentialsNonExpired = true;
    private static final boolean accountNonLocked = true;

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        UserApp userApp = userAppService.findByEmail(email);
        if (userApp == null){
            log.info("User not found! " + email);
            throw new UsernameNotFoundException("User " + email + " was not found in the database");
        }
        log.info("Found user! " + email);
        Set<String> listRoleName = userApp.getRoleSet().stream().
                                map(Role::getRoleName).collect(Collectors.toSet());
        List<GrantedAuthority> grantList = new ArrayList<>();
        listRoleName.iterator().forEachRemaining(roleName -> grantList.add(new SimpleGrantedAuthority(roleName)));
        return new User(userApp.getEmail(),
                        userApp.getEncryptedPassword(),
                        userApp.isEnabled(),
                        accountNonExpired,
                        credentialsNonExpired,
                        accountNonLocked,grantList);
    }
}
