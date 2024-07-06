package com.microservices.order_service.exceptions;

public class OrderNotFoundException extends RuntimeException{
    public OrderNotFoundException(long id){
        super("Order not found for "+id);
    }
}
