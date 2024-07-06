package com.microservices.product_service.exceptions;

public class ProductNotFoundException extends RuntimeException{
    public ProductNotFoundException(String id){
        super("product "+id+" is not found");
    }
}
