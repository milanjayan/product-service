package com.ecommerce.productservice.controllers;

import com.ecommerce.productservice.Dtos.ProductRequestDto;
import com.ecommerce.productservice.exceptions.*;
import com.ecommerce.productservice.models.Category;
import com.ecommerce.productservice.models.Product;
import com.ecommerce.productservice.services.ProductService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/product")
@AllArgsConstructor
public class ProductController {

    private ProductService productService;

    //get by id
    @GetMapping("/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable("id") Long id) throws ProductNotFoundException {
        return productService.getProductById(id);
    }

    //get all products
    @GetMapping("")
    public ResponseEntity<List<Product>> getAllProducts() throws NoProductsFoundException {
        return productService.getAllProducts();
    }

    @GetMapping("/category")
    public ResponseEntity<List<Category>> getAllCategories() throws NoCategoriesFoundException {
        return productService.getAllCategories();
    }

    @GetMapping("/category/{category}")
    public ResponseEntity<List<Product>> getInCategory(@PathVariable String category) throws NoProductsFoundInCategoryException {
        return productService.getInCategory(category);
    }

    //create
    @PostMapping("")
    public ResponseEntity<Product> createProduct(@RequestBody ProductRequestDto request) throws ProductNotCreatedException {
        return productService.createProduct(request);
    }

    //replace
    @PutMapping("/{id}")
    public ResponseEntity<Product> replaceProduct(@PathVariable Long id, @RequestBody ProductRequestDto request) throws ProductNotUpdatedException {
        return productService.replaceProduct(id, request);
    }

    //update
    @PatchMapping("/{id}")
    public ResponseEntity<Product> updateProduct(@PathVariable Long id, @RequestBody ProductRequestDto request) throws ProductNotUpdatedException {
        return productService.updateProduct(id, request);
    }

    //delete
    @DeleteMapping("/{id}")
    public ResponseEntity<Product> deleteProduct(@PathVariable Long id) throws ProductNotFoundException {
        return productService.deleteProduct(id);
    }



//    private Product convertCreateProductRequestToProduct(ProductRequest request) {
//        Category category = Category.builder().title(request.getCategory()).build();
//        return Product.builder()
//                .title(request.getTitle())
//                .price(request.getPrice())
//                .Description(request.getDescription())
//                .category(category)
//                .image(request.getImage())
//                .build();
//    }
}
