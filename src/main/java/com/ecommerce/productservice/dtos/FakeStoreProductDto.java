package com.ecommerce.productservice.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FakeStoreProductDto {
    private Long id;
    private String title;
    private Double price;
    private String category;
    private String description;
    private String image;
    private FakeStoreProductRatingDto rating;

    @Getter
    @Setter
    public static class FakeStoreProductRatingDto {
        private Double rate;
        private Integer count;
    }
}
