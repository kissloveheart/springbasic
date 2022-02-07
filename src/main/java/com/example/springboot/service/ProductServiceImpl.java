package com.example.springboot.service;

import com.example.springboot.dto.ProductInfoDTO;
import com.example.springboot.model.Product;
import com.example.springboot.projection.ProductInfo;
import com.example.springboot.repository.ProductRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
@Service
@Slf4j
public class ProductServiceImpl implements ProductService{
    @Autowired
    private ProductRepository productRepository;

    @Override
    public void saveAll(List<Product> productList) {
        productRepository.saveAll(productList);
        log.info("save list product successfully");

    }

    @Override
    public Collection<ProductInfoDTO> findAllProducts() {
        Collection<ProductInfoDTO> products = new ArrayList<>();
        productRepository.findBy(ProductInfoDTO.class).iterator().forEachRemaining(products::add);
        return products;
    }

    @Override
    public Product save(Product product) {
        return productRepository.save(product);
    }
}
