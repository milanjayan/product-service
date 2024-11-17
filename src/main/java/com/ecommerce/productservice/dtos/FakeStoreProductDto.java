package com.ecommerce.productservice.dtos;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class FakeStoreProductDto implements Serializable {
    private Long id;
    private String title;
    private Double price;
    private String category;
    private String description;
    private String image;
    private FakeStoreProductRatingDto rating;

    @Getter
    @Setter
    public static class FakeStoreProductRatingDto implements Serializable {
        private Double rate;
        private Integer count;
    }
}
