package com.example.springboot.repository;

import com.example.springboot.model.Role;
import com.example.springboot.model.UserApp;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.jdbc.Sql;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@DataJpaTest
@Sql("/createUser.sql")
@Slf4j
class RoleRepositoryTestJpa {

    private static final String PASSWORD ="$2a$10$f7QCa1euQIci96KeGFZ.meDqNC53EFoeK0ic0T94AE/SJIhB2kKxy" ;

    @Autowired private UserAppRepository userAppRepository;
    @Autowired private RoleRepository roleRepository;
    @Autowired
    TestEntityManager testEntityManager;
    @PersistenceContext
    EntityManager entityManager;

    private Role roleMod;
    private UserApp userApp3;

    @BeforeEach
    public void setUp(){
        roleMod = new Role();
        roleMod.setRoleName("Mod");

        userApp3 = new UserApp();
        userApp3.setEmail("mod@mod.com");
        userApp3.setEncryptedPassword(PASSWORD);
        userApp3.setEnabled(true);
        userApp3.addRole(roleMod);
    }

    @Test
    void findByEmailRepo() {
        UserApp userApp = userAppRepository.findByEmail("admin@admin.com");
        if (userApp != null) {
            userApp.getRoleSet().forEach(role -> log.info(role.getRoleName()));
        }
        Assertions.assertEquals("admin@admin.com", userApp.getEmail());
        Assertions.assertEquals(2,userApp.getRoleSet().size());
    }
    @Test
    void saveUserApp(){
        userApp3 = userAppRepository.save(userApp3);
        Assertions.assertEquals("Mod",roleRepository.findByRoleName("Mod").get().getRoleName());
        roleRepository.findAll().forEach(role -> log.info(role.getRoleName()) );
        log.info("Role after delete");
        userAppRepository.deleteById(userApp3.getId());
        roleRepository.findAll().forEach(role -> log.info(role.getRoleName()) );
    }




}