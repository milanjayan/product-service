package com.ecommerce.productservice.exceptions;

import lombok.Getter;

@Getter
public class ProductCredentialMissingException extends Exception {
    private String credential;
    public ProductCredentialMissingException(String credential) {
        this.credential = credential;
    }
}
