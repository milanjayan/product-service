package com.ecommerce.productservice.Dtos;


import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ExceptionDto {
    private String message;
    private String resolution;
}
