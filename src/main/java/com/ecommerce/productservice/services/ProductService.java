package com.ecommerce.productservice.services;

import com.ecommerce.productservice.dtos.ProductRequestDto;
import com.ecommerce.productservice.dtos.UserDto;
import com.ecommerce.productservice.exceptions.*;
import com.ecommerce.productservice.models.Category;
import com.ecommerce.productservice.models.Product;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface ProductService {
    Product getProductById(Long id) throws ProductNotFoundException;
    Page<Product> getAllProducts(int pageNumber, int pageSize, String sortBy) throws NoProductsFoundException;

    Page<Product> getInCategory(Category category, int pageNumber, int pageSize, String sortBy) throws NoProductsFoundInCategoryException;

    Product createProduct(Product product) throws ProductNotCreatedException, ProductCredentialMissingException;

    Product replaceProduct(Product product) throws ProductNotUpdatedException, ProductNotFoundException;
    Product updateProduct(Product product) throws ProductNotUpdatedException, ProductNotFoundException;
    void deleteProduct(Long id) throws ProductNotFoundException;

    Product demoGetAuthorizedProduct(Long userId, Long productId) throws ProductNotFoundException, UnauthorizedToAccessThisProductException, UserNotFoundException;
}
