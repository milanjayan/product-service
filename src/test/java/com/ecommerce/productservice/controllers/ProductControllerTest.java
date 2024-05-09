package com.ecommerce.productservice.controllers;

import com.ecommerce.productservice.dtos.ProductRequestDto;
import com.ecommerce.productservice.exceptions.*;
import com.ecommerce.productservice.models.Category;
import com.ecommerce.productservice.models.Product;
import com.ecommerce.productservice.services.ProductService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
class ProductControllerTest {

    @Autowired
    private ProductController productController;
    @MockBean
    @Qualifier("selfProductService")
    private ProductService productService;
    @Test
    void testGetProductById_validId_returnsProduct() throws ProductNotFoundException {
        Product product = Product.builder()
                .id(1L)
                .title("Macbook pro")
                .build();
        when(productService.getProductById(1L))
                .thenReturn(product);
        ResponseEntity<Product> response = productController.getProductById(1L);
        assertEquals(HttpStatus.OK, response.getStatusCode(), "When product found, return status should be Ok 200");
        assertEquals(product, response.getBody(), "When service returns a product, controller should return that product");
    }

    @Test
    void testGetProductById_invalidId_throwsException() throws ProductNotFoundException {
        when(productService.getProductById(1L))
                .thenThrow(ProductNotFoundException.class);
        assertThrows(ProductNotFoundException.class, ()-> productController.getProductById(1L));
    }

    @Test
    void testGetAllProducts_valid_returnsProducts() throws NoProductsFoundException {
        Product product1 = Product.builder()
                .id(1L)
                .title("iphone 13 pro")
                .build();
        Product product2 = Product.builder()
                .id(2L)
                .title("iphone 14 pro")
                .build();
        List<Product> products = List.of(product1, product2);
        when(productService.getAllProducts())
                .thenReturn(products);
        ResponseEntity<List<Product>> response = productController.getAllProducts();
        assertEquals(HttpStatus.OK, response.getStatusCode(), "When products found, return status should be OK 200");
        assertEquals(products, response.getBody(), "When service returns List of products, controller should return same List of products");
    }

    @Test
    void testGetAllProducts_invalid_throwsException() throws NoProductsFoundException {
        when(productService.getAllProducts())
                .thenThrow(NoProductsFoundException.class);
        assertThrows(NoProductsFoundException.class, ()->productController.getAllProducts());
    }

    @Test
    void testGetInCategory_validCategory_returnsProducts() throws NoProductsFoundInCategoryException, CategoryRequiredException {
        Product product1 = Product.builder()
                .id(1L)
                .title("iphone 13 pro")
                .build();
        Product product2 = Product.builder()
                .id(2L)
                .title("iphone 14 pro")
                .build();
        List<Product> products = List.of(product1, product2);
        when(productService.getInCategory(any(Category.class)))
                .thenReturn(products);
        ResponseEntity<List<Product>> response = productController.getInCategory("smartphone");
        assertEquals(HttpStatus.OK, response.getStatusCode(), "When products found, return status should be OK 200");
        assertEquals(products, response.getBody(), "When service returns List of products, controller should return same List of products");
    }

    @Test
    void testGetInCategory_invalidCategory_throwsException() {
        String categoryTitle = null;
        assertThrows(CategoryRequiredException.class, ()->productController.getInCategory(categoryTitle));
    }

    @Test
    void testCreateProduct_validProductRequest_returnsProduct() throws ProductNotCreatedException, ProductCredentialMissingException {
        ProductRequestDto requestDto = ProductRequestDto.builder()
                .title("iphone 14 pro")
                .price(150000.0)
                .image("ropgierign")
                .category("smartphone")
                .description("6gb ram, 512gb storage, black color")
                .build();
        Product product = Product.builder()
                .id(1L)
                .title("iphone 14 pro")
                .price(150000.0)
                .image("ropgierign")
                .category(Category.builder().title("smartphone").build())
                .description("6gb ram, 512gb storage, black color")
                .build();
        when(productService.createProduct(any(Product.class)))
                .thenReturn(product);
        ResponseEntity<Product> response = productController.createProduct(requestDto);
        assertEquals(HttpStatus.OK, response.getStatusCode(), "When product created, return should be status OK 200");
        assertEquals(product, response.getBody(), "When service creates product and returns, controller should return same product");
    }

    @Test
    void testCreateProduct_invalidProductRequest_throwsException() {
        ProductRequestDto requestDto = ProductRequestDto.builder().build();
        assertThrows(ProductCredentialMissingException.class, ()-> productController.createProduct(requestDto));
        requestDto.setTitle("iphone 14 pro");
        assertThrows(ProductCredentialMissingException.class, ()-> productController.createProduct(requestDto));
        requestDto.setPrice(150000.0);
        assertThrows(ProductCredentialMissingException.class, ()-> productController.createProduct(requestDto));
        requestDto.setCategory("smartphone");
        assertThrows(ProductCredentialMissingException.class, ()-> productController.createProduct(requestDto));
        requestDto.setDescription("6gb ram, 512gb storage, black color");
        assertThrows(ProductCredentialMissingException.class, ()-> productController.createProduct(requestDto));
    }

    @Test
    void testReplaceProduct_validProductId_returnsProduct() throws ProductCredentialMissingException, ProductNotFoundException, ProductNotUpdatedException {
        ProductRequestDto requestDto = ProductRequestDto.builder()
                .title("iphone 14 pro")
                .price(150000.0)
                .image("ropgierign")
                .category("smartphone")
                .description("6gb ram, 512gb storage, black color")
                .build();
        Product product = Product.builder()
                .id(1L)
                .title("iphone 14 pro")
                .price(150000.0)
                .image("ropgierign")
                .category(Category.builder().title("smartphone").build())
                .description("6gb ram, 512gb storage, black color")
                .build();
        when(productService.replaceProduct(any(Product.class)))
                .thenReturn(product);
        ResponseEntity<Product> response = productController.replaceProduct(1L, requestDto);
        assertEquals(HttpStatus.OK, response.getStatusCode(), "When product replaced, return status should be OK 200");
        assertEquals(product, response.getBody(), "When service replace product and returns, controller should return same product");
    }

    @Test
    void testReplaceProduct_invalidProductId_throwsException() throws ProductNotFoundException, ProductNotUpdatedException {
        ProductRequestDto requestDto = ProductRequestDto.builder()
                .title("iphone 14 pro")
                .price(150000.0)
                .image("ropgierign")
                .category("smartphone")
                .description("6gb ram, 512gb storage, black color")
                .build();
        when(productService.replaceProduct(any(Product.class)))
                .thenThrow(ProductNotFoundException.class);
        assertThrows(ProductNotFoundException.class, ()-> productController.replaceProduct(1L, requestDto));
    }

    @Test
    void testReplaceProduct_invalidProductRequest_throwsException() {
        ProductRequestDto requestDto = ProductRequestDto.builder().build();
        assertThrows(ProductCredentialMissingException.class, ()-> productController.replaceProduct(1L, requestDto));
        requestDto.setTitle("iphone 14 pro");
        assertThrows(ProductCredentialMissingException.class, ()-> productController.replaceProduct(1L, requestDto));
        requestDto.setPrice(150000.0);
        assertThrows(ProductCredentialMissingException.class, ()-> productController.replaceProduct(1L, requestDto));
        requestDto.setCategory("smartphone");
        assertThrows(ProductCredentialMissingException.class, ()-> productController.replaceProduct(1L, requestDto));
        requestDto.setDescription("6gb ram, 512gb storage, black color");
        assertThrows(ProductCredentialMissingException.class, ()-> productController.replaceProduct(1L, requestDto));
    }

    @Test
    void testUpdateProduct_validProductId_returnsProduct() throws ProductNotFoundException, ProductNotUpdatedException {
        ProductRequestDto requestDto = ProductRequestDto.builder()
                .title("iphone 15 pro max")
                .build();
        Product product = Product.builder()
                .title("iphone 15 pro max")
                .build();
        when(productService.updateProduct(any(Product.class)))
                .thenReturn(product);
        ResponseEntity<Product> response = productController.updateProduct(1L, requestDto);
        assertEquals(HttpStatus.OK, response.getStatusCode(), "When product updated, return status should be OK 200");
        assertEquals(product, response.getBody(), "When service updates product and returns, controller should return same product");
    }

    @Test
    void testUpdateProduct_invalidProductId_throwsException() throws ProductNotFoundException, ProductNotUpdatedException {
        ProductRequestDto requestDto = ProductRequestDto.builder()
                .title("Iphone 14")
                .build();
        when(productService.updateProduct(any(Product.class)))
                .thenThrow(ProductNotFoundException.class);
        assertThrows(ProductNotFoundException.class, ()-> productController.updateProduct(1L, requestDto));
    }
}