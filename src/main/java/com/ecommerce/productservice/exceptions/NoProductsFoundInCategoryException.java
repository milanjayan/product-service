package com.ecommerce.productservice.exceptions;

import lombok.Getter;

@Getter
public class NoProductsFoundInCategoryException extends Exception {

    private String category;
    public NoProductsFoundInCategoryException(String category) {
        this.category = category;
    }
}
