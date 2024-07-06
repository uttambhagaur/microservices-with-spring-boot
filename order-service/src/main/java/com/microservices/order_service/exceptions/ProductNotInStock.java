package com.microservices.order_service.exceptions;

public class ProductNotInStock extends RuntimeException{

    public ProductNotInStock(String message){
        super(message);
    }
}
