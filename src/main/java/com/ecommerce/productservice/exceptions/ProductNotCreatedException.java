package com.ecommerce.productservice.exceptions;

import com.ecommerce.productservice.dtos.ProductRequestDto;
import com.ecommerce.productservice.models.Product;
import lombok.Getter;

@Getter
public class ProductNotCreatedException extends Exception {
    private Product product;
    public ProductNotCreatedException(Product product) {
        this.product = product;
    }
}
