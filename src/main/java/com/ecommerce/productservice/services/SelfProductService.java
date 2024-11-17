package com.ecommerce.productservice.services;

import com.ecommerce.productservice.dtos.UserDto;
import com.ecommerce.productservice.exceptions.*;
import com.ecommerce.productservice.models.Category;
import com.ecommerce.productservice.models.Product;
import com.ecommerce.productservice.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
@Qualifier("selfProductService")
public class SelfProductService implements ProductService {

    private ProductRepository productRepository;
    private CategoryService categoryService;

    public SelfProductService(ProductRepository productRepository, @Qualifier("selfCategoryService") CategoryService categoryService) {
        this.productRepository = productRepository;
        this.categoryService = categoryService;
    }

    @Override
    public Product getProductById(Long id) throws ProductNotFoundException {
        Optional<Product> optionalProduct = productRepository.findById(id);
        optionalProduct.orElseThrow(() -> new ProductNotFoundException(id));
        return optionalProduct.get();
    }

    @Override
    public Page<Product> getAllProducts(int pageNumber, int pageSize, String sortBy) throws NoProductsFoundException {
        Page<Product> products = productRepository.findAll(PageRequest.of(
                pageNumber,
                pageSize,
                Sort.by(sortBy).ascending()));
        if (products.isEmpty()) {
            throw new NoProductsFoundException();
        }
        return products;
    }

    @Override
    public Page<Product> getInCategory(Category category, int pageNumber, int pageSize, String sortBy) throws NoProductsFoundInCategoryException {
        Page<Product> products = productRepository.findAllByCategoryTitle(category.getTitle(), PageRequest.of(
                pageNumber,
                pageSize,
                Sort.by(sortBy).ascending()
        ));
        if(products.isEmpty()) {
            throw new NoProductsFoundInCategoryException(category.getTitle());
        }
        return products;
    }

    @Override
    public Product createProduct(Product product) throws ProductNotCreatedException {
        Category category = categoryService.findOrCreate(product.getCategory());
        product.setCategory(category);
        return productRepository.save(product);
    }

    @Override
    public Product replaceProduct(Product product) throws ProductNotUpdatedException, ProductNotFoundException {
        Optional<Product> optionalProduct = productRepository.findById(product.getId());
        optionalProduct.orElseThrow(() -> new ProductNotFoundException(product.getId()));
        Category category = categoryService.findOrCreate(product.getCategory());
        product.setCategory(category);
        return productRepository.save(product);
    }

    @Override
    public Product updateProduct(Product product) throws ProductNotUpdatedException, ProductNotFoundException {
        Optional<Product> optionalProduct = productRepository.findById(product.getId());
        optionalProduct.orElseThrow(() -> new ProductNotFoundException(product.getId()));
        Product productToSave = optionalProduct.get();
        if(product.getCategory() != null) {
            Category category = categoryService.findOrCreate(product.getCategory());
            productToSave.setCategory(category);
        }
        if(product.getTitle() != null) {
            productToSave.setTitle(product.getTitle());
        }
        if(product.getPrice() != null) {
            productToSave.setPrice(product.getPrice());
        }
        if(product.getDescription() != null) {
            productToSave.setDescription(product.getDescription());
        }
        if(product.getImage() != null) {
            productToSave.setImage(product.getImage());
        }
        return productRepository.save(productToSave);
    }

    @Override
    public void deleteProduct(Long id) throws ProductNotFoundException {
        productRepository.deleteById(id);
    }

    @Override
    public Product demoGetAuthorizedProduct(Long userId, Long productId) throws ProductNotFoundException, UnauthorizedToAccessThisProductException, UserNotFoundException {
        return null;
    }
}


