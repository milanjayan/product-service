package com.ecommerce.productservice.services;

import com.ecommerce.productservice.dtos.CategoryRequestDto;
import com.ecommerce.productservice.dtos.ProductRequestDto;
import com.ecommerce.productservice.exceptions.*;
import com.ecommerce.productservice.models.Category;
import com.ecommerce.productservice.models.Product;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface CategoryService {

    Category getCategoryById(Long id) throws CategoryNotFoundException;
    List<Category> getAllCategories() throws NoCategoriesFoundException;

    Category findOrCreate(Category category);
    Category createCategory(Category category) throws CategoryNotCreatedException;

    Category replaceCategory(Category category) throws CategoryNotUpdatedException;
    Category updateCategory(Category category) throws CategoryNotUpdatedException;
    void deleteCategory(Long id) throws CategoryNotFoundException;
}
