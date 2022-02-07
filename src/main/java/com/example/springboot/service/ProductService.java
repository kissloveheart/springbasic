package com.example.springboot.service;

import com.example.springboot.dto.ProductInfoDTO;
import com.example.springboot.model.Product;

import java.util.Collection;
import java.util.List;

public interface ProductService {
    void saveAll(List<Product> productList);
    Collection<ProductInfoDTO> findAllProducts();

    Product save(Product product);


}
