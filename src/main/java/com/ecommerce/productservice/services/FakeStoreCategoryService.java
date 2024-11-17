package com.ecommerce.productservice.services;

import com.ecommerce.productservice.exceptions.CategoryNotCreatedException;
import com.ecommerce.productservice.exceptions.CategoryNotFoundException;
import com.ecommerce.productservice.exceptions.CategoryNotUpdatedException;
import com.ecommerce.productservice.exceptions.NoCategoriesFoundException;
import com.ecommerce.productservice.models.Category;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Qualifier("fakeStoreCategoryService")
public class FakeStoreCategoryService implements CategoryService {

    private RestTemplate restTemplate;
    @Value("${fakestore.url}")
    private String fakeStoreUrl;

    public FakeStoreCategoryService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }
    @Override
    public Category getCategoryById(Long id) throws CategoryNotFoundException {
        String stringCategory = restTemplate.getForObject(fakeStoreUrl+id, String.class);
        Optional<String> optionalCategory = Optional.ofNullable(stringCategory);
        optionalCategory.orElseThrow(() -> new CategoryNotFoundException(id));
        return Category.builder().title(stringCategory).build();
    }

    @Override
    public List<Category> getAllCategories() throws NoCategoriesFoundException {
        String[] categoriesArray = restTemplate.getForObject(fakeStoreUrl+"categories", String[].class);
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
