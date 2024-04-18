package com.ecommerce.productservice.exceptions;

import com.ecommerce.productservice.Dtos.ProductRequestDto;
import lombok.Getter;

@Getter
public class ProductNotCreatedException extends Exception {
    private ProductRequestDto request;
    public ProductNotCreatedException(ProductRequestDto request) {
        this.request = request;
    }
}
