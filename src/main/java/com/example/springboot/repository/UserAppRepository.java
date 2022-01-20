package com.example.springboot.repository;

import com.example.springboot.model.UserApp;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UserAppRepository extends CrudRepository<UserApp,Long> {
    Optional<UserApp> findByEmail(String email);
}
