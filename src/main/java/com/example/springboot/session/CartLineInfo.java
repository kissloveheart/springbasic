package com.example.springboot.session;

import com.example.springboot.dto.ProductInfoDTO;
import lombok.Data;


@Data
public class CartLineInfo {
    private ProductInfoDTO productInfo;
    private Integer quantity;
}
