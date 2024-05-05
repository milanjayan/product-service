package com.ecommerce.productservice.exceptions;

import com.ecommerce.productservice.dtos.CategoryRequestDto;

public class CategoryNotCreatedException extends Exception {
    private CategoryRequestDto requestDto;
    public CategoryNotCreatedException(CategoryRequestDto requestDto) {
        this.requestDto = requestDto;
    }
}
