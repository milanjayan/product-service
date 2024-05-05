package com.ecommerce.productservice.exceptions;

public class CategoryNotFoundException extends Exception {
    private Long id;

    public CategoryNotFoundException(Long id) {
        this.id = id;
    }
}
