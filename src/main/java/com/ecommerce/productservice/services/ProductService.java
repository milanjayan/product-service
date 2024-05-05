package com.ecommerce.productservice.services;

import com.ecommerce.productservice.dtos.ProductRequestDto;
import com.ecommerce.productservice.exceptions.*;
import com.ecommerce.productservice.models.Category;
import com.ecommerce.productservice.models.Product;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface ProductService {
    Product getProductById(Long id) throws ProductNotFoundException;
    List<Product> getAllProducts() throws NoProductsFoundException;

    List<Product> getInCategory(Category category) throws NoProductsFoundInCategoryException;

    Product createProduct(Product product) throws ProductNotCreatedException, ProductCredentialMissingException;

    Product replaceProduct(Product product) throws ProductNotUpdatedException, ProductNotFoundException;
    Product updateProduct(Product product) throws ProductNotUpdatedException, ProductNotFoundException;
    void deleteProduct(Long id) throws ProductNotFoundException;
}
