package com.example.springboot.service;

import com.example.springboot.dto.OrderInfoDTO;
import com.example.springboot.handler.OrderTransactionException;
import com.example.springboot.model.Orders;
import com.example.springboot.session.CartInfo;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface OrdersService {
    public Orders saveOrder(CartInfo cartInfo)  throws OrderTransactionException;
    public Long cashOrder(HttpServletRequest request) throws OrderTransactionException;
    public List<OrderInfoDTO> getOrderListByUser();
}
