package com.ecommerce.productservice.services;

import com.ecommerce.productservice.Dtos.FakeStoreProductDto;
import com.ecommerce.productservice.models.Category;
import com.ecommerce.productservice.models.Product;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
@AllArgsConstructor
public class FakeStoreProductService implements ProductService{

    private RestTemplate restTemplate;
    private final String fakeStoreProductUrl = "https://fakestoreapi.com/products/";
    private Product convertDtotoProduct(FakeStoreProductDto dto) {
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
    public Product getProductById(Long id) {
        FakeStoreProductDto dto = restTemplate.getForObject(fakeStoreProductUrl+id, FakeStoreProductDto.class);
        assert dto != null;
        return convertDtotoProduct(dto);
    }

    @Override
    public List<Product> getAllProducts() {
        System.out.println();
        return null;
    }
}
