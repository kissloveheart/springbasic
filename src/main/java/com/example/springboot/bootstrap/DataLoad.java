package com.example.springboot.bootstrap;



import com.example.springboot.Service.RoleService;
import com.example.springboot.Service.UserAppService;
import com.example.springboot.model.Role;
import com.example.springboot.model.UserApp;
import com.example.springboot.repository.UserAppRepository;
import lombok.extern.log4j.Log4j;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Slf4j
@Component
public class DataLoad implements ApplicationListener<ContextRefreshedEvent> {
    private static final String PASSWORD ="$2a$10$f7QCa1euQIci96KeGFZ.meDqNC53EFoeK0ic0T94AE/SJIhB2kKxy" ;

    private final UserAppRepository userAppRepository;
    private final RoleService roleService;
    private final UserAppService userAppService;

    public DataLoad(UserAppRepository userAppRepository, RoleService roleService, UserAppService userAppService) {
        this.userAppRepository = userAppRepository;
        this.roleService = roleService;
        this.userAppService = userAppService;
    }

    @Override
    @Transactional
    public void onApplicationEvent(ContextRefreshedEvent event) {
        //roleRepository.saveAll(getRoleList());
        userAppRepository.saveAll(getUserAppList());

        UserApp userAppAdmin = userAppService.findByEmail("admin@admin.com");
       // testSession(userAppAdmin);
        log.info(roleService.getRoleListFromUserApp(userAppAdmin).toString());
        UserApp userAppUser = userAppService.findByEmail("user@user.com");
        log.info(roleService.getRoleListFromUserApp(userAppUser).toString());
        log.info("Data user save successfully");

    }

    public List<Role> getRoleList(){
        List<Role> roleList= new ArrayList<>();

        Role roleAdmin = new Role();
        roleAdmin.setRoleName("ADMIN");

        Role roleUser = new Role();
        roleUser.setRoleName("USER");
        roleList.add(roleAdmin);
        roleList.add(roleUser);
        return roleList;
    }

    public List<UserApp> getUserAppList(){
        Date date = new Date();

/*        // get user roles
        Optional<Role> roleAdminOptional = roleRepository.findByRoleName("ADMIN");
        if(!roleAdminOptional.isPresent()){
            throw new RuntimeException("Role admin not found!");
        }

        Optional<Role> roleUserOptional = roleRepository.findByRoleName("USER");
        if(!roleUserOptional.isPresent()){
            throw new RuntimeException("Role user not found!");
        }
        Role roleAdmin = roleAdminOptional.get();
        Role roleUser = roleUserOptional.get();*/

        Role roleAdmin = new Role();
        roleAdmin.setRoleName("ADMIN");

        Role roleUser = new Role();
        roleUser.setRoleName("USER");




        List<UserApp> userAppList = new ArrayList<>();
        UserApp userApp1 = new UserApp();
        userApp1.setEmail("admin@admin.com");
        userApp1.setEncryptedPassword(PASSWORD);
        userApp1.setEnabled(true);
       // userApp1.setCreateDate(date);
        userApp1.getRoleSet().add(roleAdmin);
        userApp1.getRoleSet().add(roleUser);



        userAppList.add(userApp1);



        UserApp userApp2 = new UserApp();
        userApp2.setEmail("user@user.com");
        userApp2.setEncryptedPassword(PASSWORD);
        userApp2.setEnabled(true);
       // userApp2.setCreateDate(date);
        userApp2.getRoleSet().add(roleUser);

        userAppList.add(userApp2);
        return userAppList;
    }

    public void testSession(UserApp userApp){
        userApp.getRoleSet().forEach(role -> log.info(role.getRoleName()));
    }
}
