package com.example.springboot.projection;

public interface ProductInfo {
    Long getId();
    String getName();
    Double getPrice();
    CategoryName getCategory();
    interface CategoryName{
        String getName();
    }

}
