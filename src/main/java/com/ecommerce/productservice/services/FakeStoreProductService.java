package com.ecommerce.productservice.services;

import com.ecommerce.productservice.Dtos.FakeStoreProductDto;
import com.ecommerce.productservice.Dtos.ProductRequestDto;
import com.ecommerce.productservice.exceptions.*;
import com.ecommerce.productservice.models.Category;
import com.ecommerce.productservice.models.Product;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RequestCallback;
import org.springframework.web.client.ResponseExtractor;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class FakeStoreProductService implements ProductService{

    private RestTemplate restTemplate;
    private final String fakeStoreProductUrl = "https://fakestoreapi.com/products/";
    private Product convertFakeStoreProductDtoToProduct(FakeStoreProductDto dto) {
        Category category = Category.builder()
                .title(dto.getCategory())
                .build();

        return Product.builder()
                .id(dto.getId())
                .title(dto.getTitle())
                .price(dto.getPrice())
                .Description(dto.getDescription())
                .category(category)
                .image(dto.getImage())
                .build();
    }

    @Override
    public ResponseEntity<Product> getProductById(Long id) throws ProductNotFoundException {
        FakeStoreProductDto fakeStoreProductDto = restTemplate.getForObject(fakeStoreProductUrl+id, FakeStoreProductDto.class);
        Optional<FakeStoreProductDto> optionalFakeStoreProductDto = Optional.ofNullable(fakeStoreProductDto);
        optionalFakeStoreProductDto.orElseThrow(() -> new ProductNotFoundException(id));
        Product product = convertFakeStoreProductDtoToProduct(fakeStoreProductDto);
        return new ResponseEntity<>(product, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<List<Product>> getAllProducts() throws NoProductsFoundException {
        FakeStoreProductDto[] fakeStoreProductDtos = restTemplate.getForObject(fakeStoreProductUrl, FakeStoreProductDto[].class);
        Optional<FakeStoreProductDto[]> optionalFakeStoreProductDtos = Optional.ofNullable(fakeStoreProductDtos);
        if(optionalFakeStoreProductDtos.isEmpty() || fakeStoreProductDtos.length == 0) {
            throw new NoProductsFoundException();
        }
        List<Product> products = new ArrayList<>();
        for(FakeStoreProductDto fakeStoreProductDto : fakeStoreProductDtos) {
            Product product = convertFakeStoreProductDtoToProduct(fakeStoreProductDto);
            products.add(product);
        }
        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<List<Category>> getAllCategories() throws NoCategoriesFoundException {
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
        return new ResponseEntity<>(categories, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<List<Product>> getInCategory(String category) throws NoProductsFoundInCategoryException {
        FakeStoreProductDto[] fakeStoreProductDtos = restTemplate.getForObject(fakeStoreProductUrl+"category/"+category, FakeStoreProductDto[].class);
        Optional<FakeStoreProductDto[]> optionalFakeStoreProductDtos = Optional.ofNullable(fakeStoreProductDtos);
        if(optionalFakeStoreProductDtos.isEmpty() || fakeStoreProductDtos.length == 0) {
            throw new NoProductsFoundInCategoryException(category);
        }
        List<Product> products = new ArrayList<>();
        for(FakeStoreProductDto element : fakeStoreProductDtos) {
            Product product = convertFakeStoreProductDtoToProduct(element);
            products.add(product);
        }
        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Product> createProduct(ProductRequestDto request) throws ProductNotCreatedException {
        FakeStoreProductDto fakeStoreProductDto = restTemplate.postForObject(fakeStoreProductUrl, request, FakeStoreProductDto.class);
        Optional<FakeStoreProductDto> optionalFakeStoreProductDto = Optional.ofNullable(fakeStoreProductDto);
        optionalFakeStoreProductDto.orElseThrow(() -> new ProductNotCreatedException(request));
        Product response = convertFakeStoreProductDtoToProduct(fakeStoreProductDto);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Product> replaceProduct(Long id, ProductRequestDto request) throws ProductNotUpdatedException {
        RequestCallback requestCallback = restTemplate.httpEntityCallback(request);
        ResponseExtractor<ResponseEntity<FakeStoreProductDto>> responseExtractor = restTemplate.responseEntityExtractor(FakeStoreProductDto.class);
        ResponseEntity<FakeStoreProductDto> response = restTemplate.execute(fakeStoreProductUrl+id, HttpMethod.PUT, requestCallback, responseExtractor);
        if(response == null || response.getBody() == null) {
            throw new ProductNotUpdatedException(id);
        }
        FakeStoreProductDto fakeStoreProductDto = response.getBody();
        Product product = convertFakeStoreProductDtoToProduct(fakeStoreProductDto);
        return new ResponseEntity<>(product, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Product> updateProduct(Long id, ProductRequestDto request) throws ProductNotUpdatedException {
        RequestCallback requestCallback = restTemplate.httpEntityCallback(request);
        ResponseExtractor<ResponseEntity<FakeStoreProductDto>> responseExtractor = restTemplate.responseEntityExtractor(FakeStoreProductDto.class);
        ResponseEntity<FakeStoreProductDto> response = restTemplate.execute(fakeStoreProductUrl+id, HttpMethod.PUT, requestCallback, responseExtractor);
        if(response == null || response.getBody() == null) {
            throw new ProductNotUpdatedException(id);
        }
        FakeStoreProductDto fakeStoreProductDto = response.getBody();
        Product product = convertFakeStoreProductDtoToProduct(fakeStoreProductDto);
        return new ResponseEntity<>(product, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Product> deleteProduct(Long id) throws ProductNotFoundException {
        ResponseExtractor<ResponseEntity<FakeStoreProductDto>> responseExtractor = restTemplate.responseEntityExtractor(FakeStoreProductDto.class);
        ResponseEntity<FakeStoreProductDto> response = restTemplate.execute(fakeStoreProductUrl+id, HttpMethod.DELETE, null, responseExtractor);
        if(response == null || response.getBody() == null) {
            throw new ProductNotFoundException(id);
        }
        FakeStoreProductDto fakeStoreProductDto = response.getBody();
        Product product = convertFakeStoreProductDtoToProduct(fakeStoreProductDto);
        return new ResponseEntity<>(product, HttpStatus.OK);
    }
}
