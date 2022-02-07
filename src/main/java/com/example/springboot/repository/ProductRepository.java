package com.example.springboot.repository;

import com.example.springboot.model.Product;
import com.example.springboot.projection.ProductInfo;
import org.springframework.data.repository.CrudRepository;

import java.util.Collection;

public interface ProductRepository extends CrudRepository<Product, Long> {
    <T> Collection<T> findBy(Class<T> classType);
    <T> T findById(Long id, Class<T> classType);
}
