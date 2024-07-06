package com.microservices.order_service.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(OrderNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String handleOrderNotFoundException(OrderNotFoundException ex){
        return ex.getMessage();
    }

    @ExceptionHandler(ProductNotInStock.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handleProductNotInStock(ProductNotInStock ex){
        return ex.getMessage();
    }
}
