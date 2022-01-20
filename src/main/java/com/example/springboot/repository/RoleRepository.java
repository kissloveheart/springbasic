package com.example.springboot.repository;


import com.example.springboot.model.Role;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface RoleRepository extends CrudRepository<Role,Long> {
    Optional<Role> findByRoleName(String roleName);
}
