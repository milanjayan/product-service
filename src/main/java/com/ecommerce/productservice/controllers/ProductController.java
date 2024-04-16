package com.ecommerce.productservice.controllers;

import com.ecommerce.productservice.Dtos.ProductRequest;
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
    public ResponseEntity<Product> getProductById(@PathVariable("id") Long id) {
        return productService.getProductById(id);
    }

    //get all products
    @GetMapping("")
    public ResponseEntity<List<Product>> getAllProducts() {
        return productService.getAllProducts();
    }

    @GetMapping("/category")
    public ResponseEntity<List<Category>> getAllCategories() {
        return productService.getAllCategories();
    }

    @GetMapping("/category/{category}")
    public ResponseEntity<List<Product>> getInCategory(@PathVariable String category) {
        return productService.getInCategory(category);
    }

    //create
    @PostMapping("")
    public ResponseEntity<Product> createProduct(@RequestBody ProductRequest request) {
        return productService.createProduct(request);
    }

    //delete
    //update
    //replace



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
