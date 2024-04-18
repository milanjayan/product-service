package com.ecommerce.productservice.exceptions;

import lombok.Getter;

@Getter
public class ProductNotFoundException extends Exception{
    private Long id;
    public ProductNotFoundException(Long id) {
        this.id = id;
    }
}
