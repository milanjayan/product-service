package com.ecommerce.productservice.services;

import com.ecommerce.productservice.models.Product;

import java.util.List;

public interface ProductService {
    Product getProductById(Long id);
    List<Product> getAllProducts();
}
