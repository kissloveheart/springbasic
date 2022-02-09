package com.example.springboot.repository;

import com.example.springboot.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.Collection;

public interface ProductRepository extends CrudRepository<Product, Long> {
    <T> Collection<T> findBy(Class<T> classType);
    <T> T findById(Long id, Class<T> classType);

    @Query("select p from Product  p where p.category.name =?1")
    Collection<Product> findAllByCategory(String categoryName);
    @Query(value = "select * from Product as p where p.price > :price1 and p.price < :price2", nativeQuery = true)
    Collection<Product> findAllBetween(@Param("price1") Double price1, @Param("price2") Double price2);

    Page<Product> findAllByCategory_Id(Long id,Pageable pageable);
}
