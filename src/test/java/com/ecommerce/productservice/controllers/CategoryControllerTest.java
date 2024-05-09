package com.ecommerce.productservice.controllers;

import com.ecommerce.productservice.exceptions.CategoryNotFoundException;
import com.ecommerce.productservice.exceptions.NoCategoriesFoundException;
import com.ecommerce.productservice.models.Category;
import com.ecommerce.productservice.services.CategoryService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@SpringBootTest
class CategoryControllerTest {

    @Autowired
    private CategoryController categoryController;

    @MockBean
    @Qualifier("SelfCategoryService")
    private CategoryService categoryService;

    @Test
    void validGetCategoryByIdTest() throws CategoryNotFoundException {
        Category category = Category.builder()
                .id(1L)
                .title("Smartphone")
                .build();
        when(categoryService.getCategoryById(1L))
                .thenReturn(category);
        ResponseEntity<Category> response = categoryController.getCategoryById(1L);
        assertEquals(HttpStatus.OK, response.getStatusCode(), "When found, status should be Ok 200");
        assertEquals(category, response.getBody(), "when found, controller should return same category object");
    }

    @Test
    void invalidGetCategoryByIdTest() throws CategoryNotFoundException {
        when(categoryService.getCategoryById(1L))
                .thenThrow(CategoryNotFoundException.class);
        assertThrows(CategoryNotFoundException.class, ()->categoryController.getCategoryById(1L));
    }

    @Test
    void validGetAllCategoriesTest() throws NoCategoriesFoundException {
        Category category1 = Category.builder()
                .id(1L)
                .title("Smartphone")
                .build();
        Category category2 = Category.builder()
                .id(1L)
                .title("Smartphone")
                .build();
        List<Category> categories = List.of(category1, category2);
        when(categoryService.getAllCategories())
                .thenReturn(categories);
        ResponseEntity<List<Category>> response = categoryController.getAllCategories();
        assertEquals(HttpStatus.OK, response.getStatusCode(), "When found, status should be Ok 200");
        assertEquals(categories, response.getBody(), "when found, controller should return same List of categories objects");
    }

    @Test
    void invalidGetAllCategoriesTest() throws NoCategoriesFoundException {
        when(categoryService.getAllCategories())
                .thenThrow(NoCategoriesFoundException.class);
        assertThrows(NoCategoriesFoundException.class, ()->categoryController.getAllCategories());
    }
}