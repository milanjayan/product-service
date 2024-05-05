package com.ecommerce.productservice.services;

import com.ecommerce.productservice.dtos.CategoryRequestDto;
import com.ecommerce.productservice.exceptions.CategoryNotCreatedException;
import com.ecommerce.productservice.exceptions.CategoryNotFoundException;
import com.ecommerce.productservice.exceptions.CategoryNotUpdatedException;
import com.ecommerce.productservice.exceptions.NoCategoriesFoundException;
import com.ecommerce.productservice.models.Category;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Qualifier("fakeStoreCategoryService")
@AllArgsConstructor
public class FakeStoreCategoryService implements CategoryService {

    private RestTemplate restTemplate;
    private final String fakeStoreProductUrl = "https://fakestoreapi.com/products/";
    @Override
    public Category getCategoryById(Long id) throws CategoryNotFoundException {
        String stringCategory = restTemplate.getForObject(fakeStoreProductUrl+id, String.class);
        Optional<String> optionalCategory = Optional.ofNullable(stringCategory);
        optionalCategory.orElseThrow(() -> new CategoryNotFoundException(id));
        return Category.builder().title(stringCategory).build();
    }

    @Override
    public List<Category> getAllCategories() throws NoCategoriesFoundException {
        String[] categoriesArray = restTemplate.getForObject(fakeStoreProductUrl+"categories", String[].class);
        Optional<String[]> optionalCategoriesArray = Optional.ofNullable(categoriesArray);
        if(optionalCategoriesArray.isEmpty() || categoriesArray.length == 0) {
            throw new NoCategoriesFoundException();
        }
        List<Category> categories = new ArrayList<>();
        for(String element : categoriesArray) {
            Category category = Category.builder().title(element).build();
            categories.add(category);
        }
        return categories;
    }

    @Override
    public Category findOrCreate(Category category) {
        return null;
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
