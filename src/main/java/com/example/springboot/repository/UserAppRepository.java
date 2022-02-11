package com.example.springboot.repository;

import com.example.springboot.model.UserApp;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface UserAppRepository extends CrudRepository<UserApp,Long> {
    @Query("select u from UserApp u join fetch u.roleSet where u.email =:email")
    @Cacheable(cacheNames = "users", key = "#email")
    UserApp findByEmail(@Param("email") String email);

    @Override
    //@CachePut(cacheNames = "users",key = "#entity.email")
    @CacheEvict(cacheNames = "users",key = "#entity.email")
    <S extends UserApp> S save(S entity);
}
