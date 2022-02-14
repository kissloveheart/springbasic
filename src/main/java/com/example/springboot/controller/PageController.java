package com.example.springboot.controller;

import com.example.springboot.model.Product;
import com.example.springboot.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class PageController {
    private final ProductRepository productRepository;

    public PageController(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @GetMapping("/list-product")
    ResponseEntity<Page<Product>> getProductList(Pageable pageable){
        Page<Product> products = productRepository.findAllProduct(pageable);
        return new ResponseEntity<>(products, HttpStatus.OK);
    }
}
