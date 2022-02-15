package com.example.springboot.repository;

import com.example.springboot.model.UserApp;
import com.example.springboot.service.UserAppService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.*;
import javax.sql.DataSource;

@SpringBootTest
//@Sql("/createUser.sql")
@Slf4j
@ActiveProfiles("test")
class RoleRepositoryTest {
    @Autowired
    private DataSource dataSource;
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired private RoleRepository roleRepository;
    @Autowired private UserAppRepository userAppRepository;

    @Autowired private UserAppService userAppService;

    @PersistenceContext
    EntityManager entityManager;

    @PersistenceUnit
    EntityManagerFactory entityManagerFactory;

    @Test
    void testAllNotNull(){
        Assertions.assertNotNull(dataSource);
        Assertions.assertNotNull(jdbcTemplate);
        Assertions.assertNotNull(entityManager);
    }

    @Test
    @Transactional
    void findByEmailRepo() {
        UserApp userApp = userAppRepository.findByEmail("admin@admin.com");
        if (userApp != null) {
            userApp.getRoleSet().forEach(role -> log.info(role.getRoleName()));
        }
        Assertions.assertEquals("admin@admin.com", userApp.getEmail());
        Assertions.assertEquals(2,userApp.getRoleSet().size());
    }

    @Test
    void findByEmailFetchJoin(){
        TypedQuery<UserApp> query = entityManager
                .createQuery("select u from UserApp u join fetch u.roleSet where u.email =?1", UserApp.class);
        query.setParameter(1,"admin@admin.com");
        UserApp userApp = query.getSingleResult();
        if (userApp != null) {
            userApp.getRoleSet().forEach(role -> log.info(role.getRoleName()));
        }
        Assertions.assertEquals("admin@admin.com", userApp.getEmail());
        Assertions.assertEquals(2,userApp.getRoleSet().size());
    }

    @Test
    void findByEmailRepoTransactional() {
        EntityTransaction transaction = entityManagerFactory.createEntityManager().getTransaction();
        UserApp userApp = null;
        try {
            transaction.begin();
            userApp = userAppRepository.findByEmail("admin@admin.com");
            if (userApp != null) {
                userApp.getRoleSet().forEach(role -> log.info(role.getRoleName()));
            }
            transaction.commit();
        } catch (RuntimeException e) {
            transaction.rollback();
            log.info("Rollback ", e);
        }

        Assertions.assertEquals("admin@admin.com", userApp.getEmail());
        Assertions.assertEquals(2,userApp.getRoleSet().size());
    }



    @Test
    @Transactional
    void findByEmailService(){
        UserApp userApp = userAppService.findByEmail("user@user.com");
        //show the role of user
        if (userApp.getRoleSet() != null && userApp.getRoleSet().size() != 0) {
            userApp.getRoleSet().forEach(role -> log.info(role.getRoleName()));
        } else{
            log.info("Can not load role list");
        }
        Assertions.assertEquals("user@user.com",userApp.getEmail());
    }
/*
    UserTransaction userTransaction = entityManager.getTransaction();
    try{
        // begin a new transaction if expected
        // (depending on the current transaction context and/or propagation mode setting)
        userTransaction.begin();
        registerNewAccount();
        // the actual method invocation user
        Transaction.commit();}
    catch(RuntimeException e){
        userTransaction.rollback();
        // initiate rollback if business code fails
        Throw e;}
*/
}