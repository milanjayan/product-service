package com.ecommerce.productservice.services;

import com.ecommerce.productservice.Dtos.ProductRequestDto;
import com.ecommerce.productservice.exceptions.*;
import com.ecommerce.productservice.models.Category;
import com.ecommerce.productservice.models.Product;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface ProductService {
    ResponseEntity<Product> getProductById(Long id) throws ProductNotFoundException;
    ResponseEntity<List<Product>> getAllProducts() throws NoProductsFoundException;

    ResponseEntity<List<Category>> getAllCategories() throws NoCategoriesFoundException;

    ResponseEntity<List<Product>> getInCategory(String category) throws NoProductsFoundInCategoryException;

    ResponseEntity<Product> createProduct(ProductRequestDto request) throws ProductNotCreatedException;

    ResponseEntity<Product> replaceProduct(Long id, ProductRequestDto request) throws ProductNotUpdatedException;
    ResponseEntity<Product> updateProduct(Long id, ProductRequestDto request) throws ProductNotUpdatedException;
    ResponseEntity<Product> deleteProduct(Long id) throws ProductNotFoundException;
}
