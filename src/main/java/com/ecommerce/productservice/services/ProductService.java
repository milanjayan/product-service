package com.ecommerce.productservice.services;

import com.ecommerce.productservice.Dtos.ProductRequest;
import com.ecommerce.productservice.models.Category;
import com.ecommerce.productservice.models.Product;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface ProductService {
    ResponseEntity<Product> getProductById(Long id);
    ResponseEntity<List<Product>> getAllProducts();

    ResponseEntity<List<Category>> getAllCategories();

    ResponseEntity<List<Product>> getInCategory(String category);

    ResponseEntity<Product> createProduct(ProductRequest request);

    ResponseEntity<Product> updateProduct(ProductRequest request);
    ResponseEntity<Product> deleteProduct(Long id);
}
