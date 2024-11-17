package com.ecommerce.productservice.services;

import com.ecommerce.productservice.dtos.FakeStoreProductDto;
import com.ecommerce.productservice.dtos.ProductRequestDto;
import com.ecommerce.productservice.dtos.UserDto;
import com.ecommerce.productservice.exceptions.*;
import com.ecommerce.productservice.models.Category;
import com.ecommerce.productservice.models.Product;
import com.ecommerce.productservice.models.Role;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RequestCallback;
import org.springframework.web.client.ResponseExtractor;
import org.springframework.web.client.RestTemplate;

import java.util.*;

@Service
@Qualifier("fakeStoreProductService")
public class FakeStoreProductService implements ProductService{

    private RestTemplate restTemplate;
    private RedisTemplate<String, Object> redisTemplate;
    @Value("${fakestore.url}")
    private String fakeStoreUrl;

    private final String PRODUCT_KEY = "product";
    private final String CATEGORY_KEY = "category";

    public FakeStoreProductService(RestTemplate restTemplate, RedisTemplate<String, Object> redisTemplate) {
        this.restTemplate = restTemplate;
        this.redisTemplate = redisTemplate;
    }

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
        /*
        check if id present in cache
        if cache hit ->> return object
        if cache miss ->> get object, store in cache
        */
        FakeStoreProductDto fakeStoreProductDto = (FakeStoreProductDto) redisTemplate.opsForHash().get(PRODUCT_KEY, PRODUCT_KEY+"_"+id);
        if(fakeStoreProductDto != null) {
            return convertFakeStoreProductDtoToProduct(fakeStoreProductDto);
        }
        FakeStoreProductDto fakeStoreProductDto2 = restTemplate.getForObject(fakeStoreUrl+"/"+id, FakeStoreProductDto.class);
        Optional<FakeStoreProductDto> optionalFakeStoreProductDto = Optional.ofNullable(fakeStoreProductDto2);
        optionalFakeStoreProductDto.orElseThrow(() -> new ProductNotFoundException(id));
        redisTemplate.opsForHash().put(PRODUCT_KEY, PRODUCT_KEY+"_"+id, optionalFakeStoreProductDto.get());
        return convertFakeStoreProductDtoToProduct(fakeStoreProductDto2);
    }

    @Override
    public Page<Product> getAllProducts(int pageNumber, int pageSize, String sortBy) throws NoProductsFoundException {
        FakeStoreProductDto[] fakeStoreProductDtosCache = (FakeStoreProductDto[]) redisTemplate.opsForHash().get(PRODUCT_KEY, PRODUCT_KEY+"_pagesize_"+pageSize);
        if(fakeStoreProductDtosCache != null) {
            List<Product> products = Arrays.stream(fakeStoreProductDtosCache)
                    .map(this::convertFakeStoreProductDtoToProduct)
                    .toList();
            Pageable pageable = PageRequest.of(0, pageSize);
            return new PageImpl<>(products, pageable, products.size());
        }
        FakeStoreProductDto[] fakeStoreProductDtos = restTemplate.getForObject(fakeStoreUrl+"?limit="+pageSize+"&sort="+sortBy, FakeStoreProductDto[].class);
        Optional<FakeStoreProductDto[]> optionalFakeStoreProductDtos = Optional.ofNullable(fakeStoreProductDtos);
        if(optionalFakeStoreProductDtos.isEmpty() || fakeStoreProductDtos.length == 0) {
            throw new NoProductsFoundException();
        }
        redisTemplate.opsForHash().put(PRODUCT_KEY, PRODUCT_KEY+"_pagesize_"+pageSize, optionalFakeStoreProductDtos.get());
        List<Product> products = Arrays.stream(fakeStoreProductDtos)
                .map(this::convertFakeStoreProductDtoToProduct)
                .toList();
        Pageable pageable = PageRequest.of(0, pageSize);
        return new PageImpl<>(products, pageable, products.size());
    }

    @Override
    public Page<Product> getInCategory(Category category, int pageNumber, int pageSize, String sortBy) throws NoProductsFoundInCategoryException {
        String categoryTitle = category.getTitle();
        FakeStoreProductDto[] fakeStoreProductDtosCache = (FakeStoreProductDto[]) redisTemplate.opsForHash().get(PRODUCT_KEY, PRODUCT_KEY+"_category_"+categoryTitle);
        if(fakeStoreProductDtosCache != null) {
            List<Product> products = Arrays.stream(fakeStoreProductDtosCache)
                    .map(this::convertFakeStoreProductDtoToProduct)
                    .toList();
            Pageable pageable = PageRequest.of(0, pageSize);
            return new PageImpl<>(products, pageable, products.size());
        }
        FakeStoreProductDto[] fakeStoreProductDtos = restTemplate.getForObject(fakeStoreUrl+"/category/"+categoryTitle, FakeStoreProductDto[].class);
        Optional<FakeStoreProductDto[]> optionalFakeStoreProductDtos = Optional.ofNullable(fakeStoreProductDtos);
        if(optionalFakeStoreProductDtos.isEmpty() || fakeStoreProductDtos.length == 0) {
            throw new NoProductsFoundInCategoryException(category.getTitle());
        }
        redisTemplate.opsForHash().put(PRODUCT_KEY, PRODUCT_KEY+"_category_"+categoryTitle, fakeStoreProductDtos);
        List<Product> products = Arrays.stream(fakeStoreProductDtos)
                .map(this::convertFakeStoreProductDtoToProduct)
                .toList();
        Pageable pageable = PageRequest.of(0, pageSize);
        return new PageImpl<>(products, pageable, products.size());
    }

    @Override
    public Product createProduct(Product product) throws ProductNotCreatedException {
        ProductRequestDto requestDto = convertProductToProductRequestDto(product);
        FakeStoreProductDto fakeStoreProductDto = restTemplate.postForObject(fakeStoreUrl, requestDto, FakeStoreProductDto.class);
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
        ResponseEntity<FakeStoreProductDto> response = restTemplate.execute(fakeStoreUrl +product.getId(), HttpMethod.PUT, requestCallback, responseExtractor);
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
        ResponseEntity<FakeStoreProductDto> response = restTemplate.execute(fakeStoreUrl +product.getId(), HttpMethod.PUT, requestCallback, responseExtractor);
        if(response == null || response.getBody() == null) {
            throw new ProductNotUpdatedException(product.getId());
        }
        FakeStoreProductDto fakeStoreProductDto = response.getBody();
        return convertFakeStoreProductDtoToProduct(fakeStoreProductDto);
    }

    @Override
    public void deleteProduct(Long id) throws ProductNotFoundException {
        ResponseExtractor<ResponseEntity<FakeStoreProductDto>> responseExtractor = restTemplate.responseEntityExtractor(FakeStoreProductDto.class);
        ResponseEntity<FakeStoreProductDto> response = restTemplate.execute(fakeStoreUrl +id, HttpMethod.DELETE, null, responseExtractor);
        if(response == null || response.getBody() == null) {
            throw new ProductNotFoundException(id);
        }
    }

    @Override
    public Product demoGetAuthorizedProduct(Long userId, Long productId) throws ProductNotFoundException, UserNotFoundException, UnauthorizedToAccessThisProductException {
        String USER_SERVICE_URL = "http://user-service/user/";
        String AUTHORIZED_ROLE = "admin";
        try {
            UserDto userDto = restTemplate.getForObject(USER_SERVICE_URL+userId, UserDto.class);
            if(userDto.getRoles().stream().anyMatch(role -> AUTHORIZED_ROLE.equalsIgnoreCase(role.getName()))) {
                return getProductById(productId);
            }
            throw new UnauthorizedToAccessThisProductException("User " + userId + " not authorized to access product " + productId);
        } catch (HttpClientErrorException.NotFound e) {
            throw new UserNotFoundException("User with id "+userId+" not found");
        }
    }
}
