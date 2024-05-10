package com.ecommerce.productservice.services;

import com.ecommerce.productservice.exceptions.CategoryNotFoundException;
import com.ecommerce.productservice.exceptions.NoCategoriesFoundException;
import com.ecommerce.productservice.models.Category;
import com.ecommerce.productservice.repositories.CategoryRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest
class SelfCategoryServiceTest {

    @Autowired
    @Qualifier("selfCategoryService")
    private CategoryService categoryService;
    @MockBean
    private CategoryRepository categoryRepository;

    @Test
    void testGetCategoryById_validCategoryId_returnsCategory() throws CategoryNotFoundException {
        Category category = Category.builder()
                .id(1L)
                .title("iphone 13")
                .build();
        when(categoryRepository.findById(1L))
                .thenReturn(Optional.of(category));
        Category responseCategory = categoryService.getCategoryById(1L);
        assertEquals(category, responseCategory, "when repository returns category object, service should return same object");
    }

    @Test
    void testGetCategoryById_invalidCategoryId_throwsException() {
        when(categoryRepository.findById(1L))
                .thenReturn(Optional.empty());
        assertThrows(CategoryNotFoundException.class, ()->categoryService.getCategoryById(1L));
    }

    @Test
    void testGetAllCategories_validCategoriesFound_returnsCategories() throws NoCategoriesFoundException {
        Category category1 = Category.builder()
                .id(1L)
                .title("smartphone")
                .build();
        Category category2 = Category.builder()
                .id(2L)
                .title("laptop")
                .build();
        List<Category> categories = List.of(category1, category2);
        when(categoryRepository.findAll())
                .thenReturn(categories);
        List<Category> responseCategories = categoryService.getAllCategories();
        assertEquals(categories, responseCategories, "when repository returns list of categories, service should return it as such");
    }

    @Test
    void testGetAllCategories_invalidNoCategoriesFound_throwsException() {
        when(categoryRepository.findAll())
                .thenReturn(List.of());
        assertThrows(NoCategoriesFoundException.class, ()-> categoryService.getAllCategories());
    }

    @Test
    void createCategory() {
    }

    @Test
    void replaceCategory() {
    }

    @Test
    void updateCategory() {
    }

    @Test
    void deleteCategory() {
    }
}