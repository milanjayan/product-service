package com.ecommerce.productservice.services;

import com.ecommerce.productservice.dtos.FakeStoreProductDto;
import com.ecommerce.productservice.dtos.ProductRequestDto;
import com.ecommerce.productservice.exceptions.*;
import com.ecommerce.productservice.models.Category;
import com.ecommerce.productservice.models.Product;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RequestCallback;
import org.springframework.web.client.ResponseExtractor;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
@Qualifier("fakeStoreProductService")
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
                .description(dto.getDescription())
                .category(category)
                .image(dto.getImage())
                .build();
    }

    @Override
    public Product getProductById(Long id) throws ProductNotFoundException {
        FakeStoreProductDto fakeStoreProductDto = restTemplate.getForObject(fakeStoreProductUrl+id, FakeStoreProductDto.class);
        Optional<FakeStoreProductDto> optionalFakeStoreProductDto = Optional.ofNullable(fakeStoreProductDto);
        optionalFakeStoreProductDto.orElseThrow(() -> new ProductNotFoundException(id));
        return convertFakeStoreProductDtoToProduct(fakeStoreProductDto);

    }

    @Override
    public Page<Product> getAllProducts(int pageNumber, int pageSize, String sortBy) throws NoProductsFoundException {
//        FakeStoreProductDto[] fakeStoreProductDtos = restTemplate.getForObject(fakeStoreProductUrl, FakeStoreProductDto[].class);
//        Optional<FakeStoreProductDto[]> optionalFakeStoreProductDtos = Optional.ofNullable(fakeStoreProductDtos);
//        if(optionalFakeStoreProductDtos.isEmpty() || fakeStoreProductDtos.length == 0) {
//            throw new NoProductsFoundException();
//        }
//        List<Product> products = new ArrayList<>();
//        for(FakeStoreProductDto fakeStoreProductDto : fakeStoreProductDtos) {
//            Product product = convertFakeStoreProductDtoToProduct(fakeStoreProductDto);
//            products.add(product);
//        }
//        return products;
        return null;
    }

    @Override
    public Page<Product> getInCategory(Category category, int pageNumber, int pageSize, String sortBy) throws NoProductsFoundInCategoryException {
        return null;
    }

//    @Override
//    public List<Product> getInCategory(Category category) throws NoProductsFoundInCategoryException {
//        String categoryTitle = category.getTitle();
//        FakeStoreProductDto[] fakeStoreProductDtos = restTemplate.getForObject(fakeStoreProductUrl+"category/"+categoryTitle, FakeStoreProductDto[].class);
//        Optional<FakeStoreProductDto[]> optionalFakeStoreProductDtos = Optional.ofNullable(fakeStoreProductDtos);
//        if(optionalFakeStoreProductDtos.isEmpty() || fakeStoreProductDtos.length == 0) {
//            throw new NoProductsFoundInCategoryException(category.getTitle());
//        }
//        return Arrays.stream(fakeStoreProductDtos)
//                .map(this::convertFakeStoreProductDtoToProduct)
//                .toList();
//    }

    @Override
    public Product createProduct(Product product) throws ProductNotCreatedException {
        ProductRequestDto requestDto = convertProductToProductRequestDto(product);
        FakeStoreProductDto fakeStoreProductDto = restTemplate.postForObject(fakeStoreProductUrl, requestDto, FakeStoreProductDto.class);
        Optional<FakeStoreProductDto> optionalFakeStoreProductDto = Optional.ofNullable(fakeStoreProductDto);
        optionalFakeStoreProductDto.orElseThrow(() -> new ProductNotCreatedException(product));
        return convertFakeStoreProductDtoToProduct(fakeStoreProductDto);

    }

    private ProductRequestDto convertProductToProductRequestDto(Product product) {
        return ProductRequestDto.builder()
                .title(product.getTitle())
                .price(product.getPrice())
                .description(product.getDescription())
                .category(product.getCategory().getTitle())
                .image(product.getImage())
                .build();
    }

    @Override
    public Product replaceProduct(Product product) throws ProductNotUpdatedException {
        ProductRequestDto requestDto = convertProductToProductRequestDto(product);
        RequestCallback requestCallback = restTemplate.httpEntityCallback(requestDto);
        ResponseExtractor<ResponseEntity<FakeStoreProductDto>> responseExtractor = restTemplate.responseEntityExtractor(FakeStoreProductDto.class);
        ResponseEntity<FakeStoreProductDto> response = restTemplate.execute(fakeStoreProductUrl+product.getId(), HttpMethod.PUT, requestCallback, responseExtractor);
        if(response == null || response.getBody() == null) {
            throw new ProductNotUpdatedException(product.getId());
        }
        FakeStoreProductDto fakeStoreProductDto = response.getBody();
        return convertFakeStoreProductDtoToProduct(fakeStoreProductDto);
    }

    @Override
    public Product updateProduct(Product product) throws ProductNotUpdatedException {
        ProductRequestDto requestDto = convertProductToProductRequestDto(product);
        RequestCallback requestCallback = restTemplate.httpEntityCallback(requestDto);
        ResponseExtractor<ResponseEntity<FakeStoreProductDto>> responseExtractor = restTemplate.responseEntityExtractor(FakeStoreProductDto.class);
        ResponseEntity<FakeStoreProductDto> response = restTemplate.execute(fakeStoreProductUrl+product.getId(), HttpMethod.PUT, requestCallback, responseExtractor);
        if(response == null || response.getBody() == null) {
            throw new ProductNotUpdatedException(product.getId());
        }
        FakeStoreProductDto fakeStoreProductDto = response.getBody();
        return convertFakeStoreProductDtoToProduct(fakeStoreProductDto);
    }

    @Override
    public void deleteProduct(Long id) throws ProductNotFoundException {
        ResponseExtractor<ResponseEntity<FakeStoreProductDto>> responseExtractor = restTemplate.responseEntityExtractor(FakeStoreProductDto.class);
        ResponseEntity<FakeStoreProductDto> response = restTemplate.execute(fakeStoreProductUrl+id, HttpMethod.DELETE, null, responseExtractor);
        if(response == null || response.getBody() == null) {
            throw new ProductNotFoundException(id);
        }
    }
}
