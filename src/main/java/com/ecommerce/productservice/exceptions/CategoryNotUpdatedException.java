package com.ecommerce.productservice.exceptions;

import com.ecommerce.productservice.dtos.CategoryRequestDto;

public class CategoryNotUpdatedException extends Exception {
    private CategoryRequestDto requestDto;

    public CategoryNotUpdatedException(CategoryRequestDto requestDto) {
        this.requestDto = requestDto;
    }
}
