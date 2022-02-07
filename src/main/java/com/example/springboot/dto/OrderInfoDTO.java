package com.example.springboot.dto;

import lombok.Value;

import java.util.Date;


@Value
public class OrderInfoDTO {
    Long id;
    Date orderDate;
    Double totalAmount;
}
