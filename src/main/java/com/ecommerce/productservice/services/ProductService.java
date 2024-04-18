package com.ecommerce.productservice.services;

import com.ecommerce.productservice.Dtos.ProductRequestDto;
import com.ecommerce.productservice.models.Category;
import com.ecommerce.productservice.models.Product;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface ProductService {
    ResponseEntity<Product> getProductById(Long id);
    ResponseEntity<List<Product>> getAllProducts();

    ResponseEntity<List<Category>> getAllCategories();

    ResponseEntity<List<Product>> getInCategory(String category);

    ResponseEntity<Product> createProduct(ProductRequestDto request);

    ResponseEntity<Product> replaceProduct(Long id, ProductRequestDto request);
    ResponseEntity<Product> updateProduct(Long id, ProductRequestDto request);
    ResponseEntity<Product> deleteProduct(Long id);
}
