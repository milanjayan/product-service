package com.ecommerce.productservice.services;

import com.ecommerce.productservice.Dtos.FakeStoreProductDto;
import com.ecommerce.productservice.Dtos.ProductRequest;
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
    public ResponseEntity<Product> getProductById(Long id) {
        FakeStoreProductDto fakeStoreProductDto = restTemplate.getForObject(fakeStoreProductUrl+id, FakeStoreProductDto.class);
        if(fakeStoreProductDto == null) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
        Product product = convertFakeStoreProductDtoToProduct(fakeStoreProductDto);
        return new ResponseEntity<>(product, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<List<Product>> getAllProducts() {
        FakeStoreProductDto[] fakeStoreProductDtos = restTemplate.getForObject(fakeStoreProductUrl, FakeStoreProductDto[].class);
        List<Product> products = new ArrayList<>();
        if(fakeStoreProductDtos == null) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
        for(FakeStoreProductDto fakeStoreProductDto : fakeStoreProductDtos) {
            Product product = convertFakeStoreProductDtoToProduct(fakeStoreProductDto);
            products.add(product);
        }
        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<List<Category>> getAllCategories() {
        String[] categoriesArray = restTemplate.getForObject(fakeStoreProductUrl+"categories", String[].class);
        List<Category> categories = new ArrayList<>();
        if(categoriesArray == null) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
        for(String element : categoriesArray) {
            Category category = Category.builder().title(element).build();
            categories.add(category);
        }
        return new ResponseEntity<>(categories, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<List<Product>> getInCategory(String category) {
        FakeStoreProductDto[] FakeStoreProductDtos = restTemplate.getForObject(fakeStoreProductUrl+"category/"+category, FakeStoreProductDto[].class);
        List<Product> products = new ArrayList<>();
        if(FakeStoreProductDtos == null) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
        for(FakeStoreProductDto element : FakeStoreProductDtos) {
            Product product = convertFakeStoreProductDtoToProduct(element);
            products.add(product);
        }
        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Product> createProduct(ProductRequest request) {

        FakeStoreProductDto fakeStoreProductDto = restTemplate.postForObject(fakeStoreProductUrl, request, FakeStoreProductDto.class);
        if(fakeStoreProductDto == null) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
        Product response = convertFakeStoreProductDtoToProduct(fakeStoreProductDto);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Product> replaceProduct(Long id, ProductRequest request) {
        RequestCallback requestCallback = restTemplate.httpEntityCallback(request);
        ResponseExtractor<ResponseEntity<FakeStoreProductDto>> responseExtractor = restTemplate.responseEntityExtractor(FakeStoreProductDto.class);
        ResponseEntity<FakeStoreProductDto> response = restTemplate.execute(fakeStoreProductUrl+id, HttpMethod.PUT, requestCallback, responseExtractor);
        FakeStoreProductDto fakeStoreProductDto = response.getBody();
        if(fakeStoreProductDto == null) return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        Product product = convertFakeStoreProductDtoToProduct(fakeStoreProductDto);
        return new ResponseEntity<>(product, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Product> updateProduct(Long id, ProductRequest request) {
        RequestCallback requestCallback = restTemplate.httpEntityCallback(request);
        ResponseExtractor<ResponseEntity<FakeStoreProductDto>> responseExtractor = restTemplate.responseEntityExtractor(FakeStoreProductDto.class);
        ResponseEntity<FakeStoreProductDto> response = restTemplate.execute(fakeStoreProductUrl+id, HttpMethod.PUT, requestCallback, responseExtractor);
        FakeStoreProductDto fakeStoreProductDto = response.getBody();
        if(fakeStoreProductDto == null) return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        Product product = convertFakeStoreProductDtoToProduct(fakeStoreProductDto);
        return new ResponseEntity<>(product, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Product> deleteProduct(Long id) {
        ResponseExtractor<ResponseEntity<FakeStoreProductDto>> responseExtractor = restTemplate.responseEntityExtractor(FakeStoreProductDto.class);
        ResponseEntity<FakeStoreProductDto> response = restTemplate.execute(fakeStoreProductUrl+id, HttpMethod.DELETE, null, responseExtractor);
        FakeStoreProductDto fakeStoreProductDto = response.getBody();
        if(fakeStoreProductDto == null) return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        Product product = convertFakeStoreProductDtoToProduct(fakeStoreProductDto);
        return new ResponseEntity<>(product, HttpStatus.OK);
    }
}
