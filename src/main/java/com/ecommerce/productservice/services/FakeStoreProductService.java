package com.ecommerce.productservice.services;

import com.ecommerce.productservice.Dtos.FakeStoreProductDto;
import com.ecommerce.productservice.Dtos.ProductRequest;
import com.ecommerce.productservice.models.Category;
import com.ecommerce.productservice.models.Product;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

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
        return null;
    }

    @Override
    public ResponseEntity<List<Category>> getAllCategories() {
        return null;
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
    public ResponseEntity<Product> updateProduct(ProductRequest request) {
        return null;
    }

    @Override
    public ResponseEntity<Product> deleteProduct(Long id) {
        return null;
    }
}
