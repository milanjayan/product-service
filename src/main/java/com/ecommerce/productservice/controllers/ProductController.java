package com.ecommerce.productservice.controllers;

import com.ecommerce.productservice.dtos.CategoryRequestDto;
import com.ecommerce.productservice.dtos.ProductRequestDto;
import com.ecommerce.productservice.exceptions.*;
import com.ecommerce.productservice.models.Category;
import com.ecommerce.productservice.models.Product;
import com.ecommerce.productservice.services.CategoryService;
import com.ecommerce.productservice.services.ProductService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/product")
public class ProductController {
    private ProductService productService;

    public ProductController(@Qualifier("selfProductService")ProductService productService) {
        this.productService = productService;
    }

    //get by id
    @GetMapping("/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable("id") Long id) throws ProductNotFoundException {
        Product product = productService.getProductById(id);
        return new ResponseEntity<>(product, HttpStatus.OK);
    }

    //get all products
    @GetMapping("")
    public ResponseEntity<List<Product>> getAllProducts() throws NoProductsFoundException {
        List<Product> products =  productService.getAllProducts();
        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    @GetMapping("/category/{category}")
    public ResponseEntity<List<Product>> getInCategory(@PathVariable("category") String categoryTitle) throws NoProductsFoundInCategoryException, CategoryRequiredException {
        if(categoryTitle == null) {
            throw new CategoryRequiredException();
        }
        Category category = Category.builder()
                .title(categoryTitle)
                .build();
        List<Product> products = productService.getInCategory(category);
        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    //create
    @PostMapping("")
    public ResponseEntity<Product> createProduct(@RequestBody ProductRequestDto request) throws ProductNotCreatedException, ProductCredentialMissingException {
        validateProduct(request);
        Product product = convertProductRequestDtoToProduct(request);
        Product responseProduct = productService.createProduct(product);
        return new ResponseEntity<>(responseProduct, HttpStatus.OK);
    }

    //replace
    @PutMapping("/{id}")
    public ResponseEntity<Product> replaceProduct(@PathVariable Long id, @RequestBody ProductRequestDto request) throws ProductNotUpdatedException, ProductCredentialMissingException, ProductNotFoundException {
        validateProduct(request);
        Product product = convertProductRequestDtoToProduct(request);
        product.setId(id);
        Product responseProduct = productService.replaceProduct(product);
        return new ResponseEntity<>(responseProduct, HttpStatus.OK);
    }

    //update
    @PatchMapping("/{id}")
    public ResponseEntity<Product> updateProduct(@PathVariable Long id, @RequestBody ProductRequestDto request) throws ProductNotUpdatedException, ProductNotFoundException {
        Product product = Product.builder().build();
        if(request.getCategory() != null) {
            Category category = Category.builder().title(request.getCategory()).build();
            product.setCategory(category);
        }
        if(request.getTitle() != null) {
            product.setTitle(request.getTitle());
        }
        if(request.getPrice() != null) {
            product.setPrice(request.getPrice());
        }
        if(request.getDescription() != null) {
            product.setDescription(request.getDescription());
        }
        if(request.getImage() != null) {
            product.setImage(request.getImage());
        }
        product.setId(id);
        Product responseProduct = productService.updateProduct(product);
        return new ResponseEntity<>(responseProduct, HttpStatus.OK);
    }

    //delete
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) throws ProductNotFoundException {
        productService.deleteProduct(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }


    private void validateProduct(ProductRequestDto request) throws ProductCredentialMissingException {
        if(request.getTitle() == null) {
            throw new ProductCredentialMissingException("title");
        }
        if(request.getPrice() == null) {
            throw new ProductCredentialMissingException("price");
        }
        if(request.getDescription() == null) {
            throw new ProductCredentialMissingException("description");
        }
        if(request.getImage() == null) {
            throw new ProductCredentialMissingException("image");
        }
        if(request.getCategory() == null) {
            throw new ProductCredentialMissingException("category");
        }
    }


    private Product convertProductRequestDtoToProduct(ProductRequestDto request) {
        Category category = Category.builder().title(request.getCategory()).build();
        return Product.builder()
                .title(request.getTitle())
                .price(request.getPrice())
                .description(request.getDescription())
                .category(category)
                .image(request.getImage())
                .build();
    }
}
