package com.example.springboot.Service;

import com.example.springboot.model.UserApp;
import com.example.springboot.repository.UserAppRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Slf4j
public class userDetailServiceImpl implements UserDetailsService {
    private final UserAppRepository userAppRepository;

    public userDetailServiceImpl(UserAppRepository userAppRepository) {
        this.userAppRepository = userAppRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<UserApp> userAppOptional = userAppRepository.findByEmail(email);
        if (!userAppOptional.isPresent()){
            log.info("User not found!" + email);
            throw new UsernameNotFoundException("User " + email + " was not found in the database");
        }
        UserApp userApp = userAppOptional.get();
        log.info("Found user!" + email);
        Set<String> listRoleName = userApp.getRoleSet().stream().
                                map(role -> role.getRoleName()).collect(Collectors.toSet());
        List<GrantedAuthority> grantList = new ArrayList<GrantedAuthority>();
        if(listRoleName != null){
            listRoleName.iterator().forEachRemaining(roleName -> grantList.add(new SimpleGrantedAuthority(roleName)));
        }
        UserDetails userDetails = new User(userApp.getEmail(),userApp.getEncryptedPassword(),grantList);
        return userDetails;

    }
}
