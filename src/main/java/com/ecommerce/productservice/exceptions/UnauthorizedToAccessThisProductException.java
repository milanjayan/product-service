package com.ecommerce.productservice.exceptions;

public class UnauthorizedToAccessThisProductException extends Exception {
    public UnauthorizedToAccessThisProductException(String s) {
        super(s);
    }
}
