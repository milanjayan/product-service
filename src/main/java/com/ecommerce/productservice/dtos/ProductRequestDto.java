package com.ecommerce.productservice.dtos;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ProductRequestDto {
    private String title;
    private Double price;
    private String description;
    private String image;
    private String category;
}
