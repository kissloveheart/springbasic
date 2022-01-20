package com.example.springboot.repository;

import com.example.springboot.Service.UserAppService;
import com.example.springboot.Service.UserAppServiceImpl;
import com.example.springboot.model.UserApp;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.sql.DataSource;
import java.util.Optional;

@SpringBootTest
//@Sql("/createUser.sql")
@Slf4j
class RoleRepositoryTest {
    @Autowired
    private DataSource dataSource;
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private EntityManager entityManager;

    @Autowired private RoleRepository roleRepository;
    @Autowired private UserAppRepository userAppRepository;

    @Autowired private UserAppService userAppService;

    @Test
    void testAllNotNull(){
        Assertions.assertNotNull(dataSource);
        Assertions.assertNotNull(jdbcTemplate);
        Assertions.assertNotNull(entityManager);
    }

    @Test
    void findByEmailRepo() {
        Optional<UserApp> userApp = userAppRepository.findByEmail("admin@admin.com");
        if (userApp.isPresent()) {
            userApp.get().getRoleSet().forEach(role -> log.info(role.getRoleName()));
        }
        Assertions.assertEquals("admin@admin.com", userApp.get().getEmail());
        Assertions.assertEquals(2,userApp.get().getRoleSet().size());
    }

    @Test
    @Transactional
    void findByEmailService(){
        UserApp userApp = userAppService.findByEmail("user@user.com");
        //show the role of user
        if (userApp.getRoleSet() != null && userApp.getRoleSet().size() != 0) {
            userApp.getRoleSet().forEach(role -> log.info(role.getRoleName()));
        } else{
            log.info("Can not load role list from service layer");
        }
        Assertions.assertEquals("user@user.com",userApp.getEmail());
    }


}