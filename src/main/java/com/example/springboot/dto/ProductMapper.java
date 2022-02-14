package com.example.springboot.dto;

import com.example.springboot.model.Category;
import com.example.springboot.model.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface ProductMapper {
    @Mapping(source = "price",target = "unitPrice",numberFormat = "$#.00")
    @Mapping(source = "category.name", target = "categoryName")
    ProductDTO productToProductDTO(Product product);

    CategoryDTO categoryToCategoryDTO(Category category);
}
