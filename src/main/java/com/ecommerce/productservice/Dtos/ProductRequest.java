package com.ecommerce.productservice.Dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductRequest {
    private String title;
    private Double price;
    private String description;
    private String image;
    private String category;
}
