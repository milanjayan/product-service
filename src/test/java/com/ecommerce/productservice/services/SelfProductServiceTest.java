//package com.ecommerce.productservice.services;
//
//import com.ecommerce.productservice.exceptions.*;
//import com.ecommerce.productservice.models.Category;
//import com.ecommerce.productservice.models.Product;
//import com.ecommerce.productservice.repositories.ProductRepository;
//import org.junit.jupiter.api.Disabled;
//import org.junit.jupiter.api.Test;
//import org.mockito.Mock;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Qualifier;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//
//import java.util.List;
//import java.util.Optional;
//
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.Mockito.when;
//
//@Disabled
//@SpringBootTest
//class SelfProductServiceTest {
//
//    @Autowired
//    @Qualifier("selfProductService")
//    private ProductService productService;
//
//    @MockBean
//    private ProductRepository productRepository;
//
//    @MockBean
//    @Qualifier("SelfCategoryService")
//    private CategoryService categoryService;
//
//    @Test
//    void testGetProductById_validProductId_returnsProduct() throws ProductNotFoundException {
//        Product product = Product.builder()
//                .id(1L)
//                .title("iphone 14")
//                .price(150000.0)
//                .category(Category.builder().id(1L).title("smartphone").build())
//                .image("ss.sdfoisdhg.com")
//                .description("8gb ram, 512gb storage, black color")
//                .build();
//        when(productRepository.findById(1L))
//                .thenReturn(Optional.of(product));
//        Product responseProduct = productService.getProductById(1L);
//        assertEquals(product, responseProduct, "when repository return a product, service should return same");
//    }
//
//    @Test
//    void testGetProductId_invalidProductId_throwsException() {
//        when(productRepository.findById(any(Long.class)))
//                .thenReturn(Optional.empty());
//        assertThrows(ProductNotFoundException.class, ()-> productService.getProductById(1L));
//    }
//
//    @Test
//    void testGetAllProducts_validProductsFound_returnProducts() throws NoProductsFoundException {
////        Product product1 = Product.builder()
////                .id(1L)
////                .title("iphone 14")
////                .price(150000.0)
////                .category(Category.builder().id(1L).title("smartphone").build())
////                .image("ss.sdfoisdhg.com")
////                .description("8gb ram, 512gb storage, black color")
////                .build();
////        Product product2 = Product.builder()
////                .id(1L)
////                .title("iphone 14 pro")
////                .price(1700000.0)
////                .category(Category.builder().id(1L).title("smartphone").build())
////                .image("ss.sdfoisdhg.com")
////                .description("8gb ram, 512gb storage, black color")
////                .build();
////        List<Product> products = List.of(product1, product2);
////        when(productRepository.findAll())
////                .thenReturn(products);
////        List<Product> responseProducts = productService.getAllProducts();
////        assertEquals(products, responseProducts, "when repository returns products, service should return the same");
//    }
//
////    @Test
////    void testGetAllProducts_invalidProductsNotFound_throwsException() {
////        when(productRepository.findAll())
////                .thenReturn(List.of());
////        assertThrows(NoProductsFoundException.class, ()-> productService.getAllProducts());
////    }
//
////    @Test
////    void testGetInCategory_validCategory_returnsProducts() throws NoProductsFoundInCategoryException {
////        Category category = Category.builder().id(1L).title("smartphone").build();
////        Product product1 = Product.builder()
////                .id(1L)
////                .title("iphone 14")
////                .price(150000.0)
////                .category(category)
////                .image("ss.sdfoisdhg.com")
////                .description("8gb ram, 512gb storage, black color")
////                .build();
////        Product product2 = Product.builder()
////                .id(1L)
////                .title("iphone 14 pro")
////                .price(1700000.0)
////                .category(category)
////                .image("ss.sdfoisdhg.com")
////                .description("8gb ram, 512gb storage, black color")
////                .build();
////        List<Product> products = List.of(product1, product2);
////        when(productRepository.findAllByCategoryTitle(category.getTitle()))
////                .thenReturn(products);
////        List<Product> responseProducts = productService.getInCategory(category);
////        assertEquals(products, responseProducts, "when repository returns products, service should return the same");
//
//    }
//
//    @Test
//    void testGetInCategory_invalidCategory_throwsException() {
//        Category category = Category.builder().id(1L).title("smartphone").build();
//        when(productRepository.findAllByCategoryTitle(category.getTitle()))
//                .thenReturn(List.of());
//        assertThrows(NoProductsFoundInCategoryException.class, ()-> productService.getInCategory(category));
//    }
//
//    @Test
//    void testCreateProduct_validProduct_returnsProduct() throws ProductNotCreatedException, ProductCredentialMissingException {
//        Category category = Category.builder().id(1L).title("smartphone").build();
//        Product product = Product.builder()
//                .id(1L)
//                .title("iphone 14")
//                .price(150000.0)
//                .category(category)
//                .image("ss.sdfoisdhg.com")
//                .description("8gb ram, 512gb storage, black color")
//                .build();
//        when(categoryService.findOrCreate(any(Category.class)))
//                .thenReturn(category);
//        when(productRepository.save(product))
//                .thenReturn(product);
//        Product responseProduct = productService.createProduct(product);
//        assertEquals(product, responseProduct, "when repository saves and returns product, service should return the same");
//    }
//
//    @Test
//    void testReplaceProduct_validProductId_returnsProduct() throws ProductNotFoundException, ProductNotUpdatedException {
//        Category category = Category.builder().id(1L).title("smartphone").build();
//        Product product = Product.builder()
//                .id(1L)
//                .title("iphone 14")
//                .price(150000.0)
//                .category(category)
//                .image("ss.sdfoisdhg.com")
//                .description("8gb ram, 512gb storage, black color")
//                .build();
//        when(productRepository.findById(1L))
//                .thenReturn(Optional.of(product));
//        when(categoryService.findOrCreate(category))
//                .thenReturn(category);
//        when(productRepository.save(product))
//                .thenReturn(product);
//        Product responseProduct = productService.replaceProduct(product);
//        assertEquals(product, responseProduct, "when repository returns a product, service should return it as such");
//    }
//
//    @Test
//    void testReplaceProduct_invalidProductId_throwsException() {
//        Category category = Category.builder().id(1L).title("smartphone").build();
//        Product product = Product.builder()
//                .id(1L)
//                .title("iphone 14")
//                .price(150000.0)
//                .category(category)
//                .image("ss.sdfoisdhg.com")
//                .description("8gb ram, 512gb storage, black color")
//                .build();
//        when(productRepository.findById(any(Long.class)))
//                .thenReturn(Optional.empty());
//        assertThrows(ProductNotFoundException.class, ()->productService.replaceProduct(product));
//    }
//
//    @Test
//    void testUpdateProduct_validProductId_returnsProduct() throws ProductNotFoundException, ProductNotUpdatedException {
//        Category category = Category.builder().id(1L).title("smartphone").build();
//        Product product = Product.builder()
//                .id(1L)
//                .title("iphone 14")
//                .price(150000.0)
//                .category(category)
//                .image("ss.sdfoisdhg.com")
//                .description("8gb ram, 512gb storage, black color")
//                .build();
//        when(productRepository.findById(1L))
//                .thenReturn(Optional.of(product));
//        when(categoryService.findOrCreate(category))
//                .thenReturn(category);
//        when(productRepository.save(product))
//                .thenReturn(product);
//        Product responseProduct = productService.updateProduct(product);
//        assertEquals(product, responseProduct, "when repository returns a product, service should return it as such");
//    }
//
//    @Test
//    void testUpdateProduct_invalidProductId_returnsProduct() {
//        Category category = Category.builder().id(1L).title("smartphone").build();
//        Product product = Product.builder()
//                .id(1L)
//                .title("iphone 14")
//                .price(150000.0)
//                .category(category)
//                .image("ss.sdfoisdhg.com")
//                .description("8gb ram, 512gb storage, black color")
//                .build();
//        when(productRepository.findById(any(Long.class)))
//                .thenReturn(Optional.empty());
//        assertThrows(ProductNotFoundException.class, ()->productService.updateProduct(product));
//    }
//
//    @Test
//    void deleteProduct() {
//    }
//}