package com.example.springboot.repository;

import com.example.springboot.dto.ProductInfoDTO;
import com.example.springboot.model.Category;
import com.example.springboot.model.Product;
import com.example.springboot.projection.ProductInfo;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.jdbc.Sql;

import java.awt.print.Pageable;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;
@DataJpaTest
@Sql("/product.sql")
@Slf4j
class ProductRepositoryTest {

    @Autowired
    ProductRepository productRepository;

    @BeforeEach
    void setUp(){
        Category category = new Category();
        category.setName("test");
        productRepository.saveAll(IntStream.range(0,20)
                .mapToObj(i-> {Product product = new Product();
                product.setCategory(category);
                product.setName("test"+i);
                    return product;
                }).collect(Collectors.toList()));
    }

    @Test
    void findBy() {

    }

    @Test
    void findById() {
        ProductInfoDTO productInfoDTO =productRepository.findById(1L, ProductInfoDTO.class);
        ProductInfo productInfo = productRepository.findById(2L, ProductInfo.class);
        Assertions.assertNotNull(productInfoDTO);
        Assertions.assertNotNull(productInfo);
        log.info(productInfoDTO.toString());
        log.info(productInfo.getName());
        log.info(String.valueOf(productInfo.getPrice()));
    }
    @Test
    void findAllByCategory(){
        List<Product> products = new ArrayList<>(productRepository.findAllByCategory("Phone"));
        Assertions.assertNotNull(products);
        products.forEach(product -> log.info(product.toString()));
    }

    @Test
    void findAllBetween(){
        List<Product> products = new ArrayList<>(productRepository.findAllBetween(200D,700D));
        Assertions.assertNotNull(products);
        products.forEach(product -> log.info(product.toString()));
    }
    @Test
    void findAllByCategory_Id(){
        Page<Product> pages = productRepository.findAllByCategory_Id(3L, PageRequest.of(0,5, Sort.by("id").descending()));
        Assertions.assertEquals(5,pages.getSize());
        pages.forEach(product -> log.info(product.toString()));
    }
}