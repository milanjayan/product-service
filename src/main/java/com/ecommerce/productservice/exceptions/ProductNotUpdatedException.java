package com.ecommerce.productservice.exceptions;

import lombok.Getter;

@Getter
public class ProductNotUpdatedException extends Exception {
    private Long id;
    public ProductNotUpdatedException(Long id) {
        this.id = id;
    }
}
