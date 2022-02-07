package com.example.springboot.repository;

import com.example.springboot.model.Orders;
import com.example.springboot.model.UserApp;
import org.springframework.data.repository.CrudRepository;

import java.util.Collection;

public interface OrdersRepository extends CrudRepository<Orders, Long> {
    <T> Collection<T> findByUserApp(Class<T> classType,UserApp userApp);
}
