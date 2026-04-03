package com.shopwave.shopwave_starter.exception;
//Hawa Nursefa ATE/7005/14
public class ProductNotFoundException extends RuntimeException {
    public ProductNotFoundException(String message) {
        super(message);
    }
}