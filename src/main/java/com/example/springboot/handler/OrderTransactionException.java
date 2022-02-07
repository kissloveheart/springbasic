package com.example.springboot.handler;

public class OrderTransactionException extends Exception{
    public OrderTransactionException(String message) {
        super(message);
    }
}
