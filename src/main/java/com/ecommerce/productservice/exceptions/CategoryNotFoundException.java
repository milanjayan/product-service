package com.ecommerce.productservice.exceptions;

import lombok.Getter;

@Getter
public class CategoryNotFoundException extends Exception {
    private Long id;

    public CategoryNotFoundException(Long id) {
        this.id = id;
    }
}
