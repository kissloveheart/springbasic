package com.example.springboot.repository;

import com.example.springboot.model.VerificationToken;
import org.springframework.data.repository.CrudRepository;

public interface TokenRepository extends CrudRepository<VerificationToken,Long> {
    VerificationToken findByToken(String verificationToken);
}
