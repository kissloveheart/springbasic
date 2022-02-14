package com.example.springboot.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ProductDTO {
    private Long id;
    private String name;
    private Double price;
    private String unitPrice;
    private String categoryName;
}
