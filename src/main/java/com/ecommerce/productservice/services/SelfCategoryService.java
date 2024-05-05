package com.ecommerce.productservice.services;

import com.ecommerce.productservice.exceptions.CategoryNotCreatedException;
import com.ecommerce.productservice.exceptions.CategoryNotFoundException;
import com.ecommerce.productservice.exceptions.CategoryNotUpdatedException;
import com.ecommerce.productservice.exceptions.NoCategoriesFoundException;
import com.ecommerce.productservice.models.Category;
import com.ecommerce.productservice.repositories.CategoryRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Qualifier("SelfCategoryService")
@AllArgsConstructor
public class SelfCategoryService implements CategoryService {

    private CategoryRepository categoryRepository;
    public Category findOrCreate(Category category) {
        return categoryRepository.findByTitle(category.getTitle())
                .orElseGet(() -> categoryRepository.save(category));
    }
    @Override
    public Category getCategoryById(Long id) throws CategoryNotFoundException {
        Optional<Category> optionalCategory = categoryRepository.findById(id);
        optionalCategory.orElseThrow(() -> new CategoryNotFoundException(id));
        return optionalCategory.get();
    }

    @Override
    public List<Category> getAllCategories() throws NoCategoriesFoundException {
        List<Category> categories = categoryRepository.findAll();
        if(categories.isEmpty()) {
            throw new NoCategoriesFoundException();
        }
        return categories;
    }

    @Override
    public Category createCategory(Category category) throws CategoryNotCreatedException {
        return null;
    }

    @Override
    public Category replaceCategory(Category category) throws CategoryNotUpdatedException {
        return null;
    }

    @Override
    public Category updateCategory(Category category) throws CategoryNotUpdatedException {
        return null;
    }

    @Override
    public void deleteCategory(Long id) throws CategoryNotFoundException {

    }
}
