package com.example.springboot.dto;

import com.example.springboot.model.Category;
import com.example.springboot.model.Product;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

class ProductMapperTest {

    ProductMapper productMapper = Mappers.getMapper(ProductMapper.class);


    @Test
    void productToProductInfoDTO() {
        Category category = new Category();
        category.setId(2L);
        category.setName("Category");

        Product product = new Product();
        product.setName("TestMapper");
        product.setPrice(10000D);
        product.setId(1L);
        product.setCategory(category);

        ProductDTO productDTO = productMapper.productToProductDTO(product);
        Assertions.assertEquals("TestMapper",productDTO.getName());
        Assertions.assertEquals("$10000.00",productDTO.getUnitPrice());
        Assertions.assertEquals(1L,productDTO.getId());
        Assertions.assertEquals("Category",productDTO.getCategoryName());

    }
}