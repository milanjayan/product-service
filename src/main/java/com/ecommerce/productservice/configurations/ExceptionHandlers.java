package com.ecommerce.productservice.configurations;

import com.ecommerce.productservice.dtos.ExceptionDto;
import com.ecommerce.productservice.exceptions.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionHandlers {

    @ExceptionHandler(ArrayIndexOutOfBoundsException.class)
    public ResponseEntity<ExceptionDto> handleArrayIndexOutOfBoundsException() {
        ExceptionDto exceptionDto = ExceptionDto.builder()
                .message("Something went wrong")
                .resolution("Nothing can be done")
                .build();
        return new ResponseEntity<>(exceptionDto, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(ProductNotFoundException.class)
    public ResponseEntity<ExceptionDto> handleProductNotFoundException(ProductNotFoundException exception) {
        ExceptionDto exceptionDto = ExceptionDto.builder()
                .message("product with id:"+exception.getId()+" not found")
                .resolution("try again with another id")
                .build();
        return new ResponseEntity<>(exceptionDto, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(NoProductsFoundException.class)
    public ResponseEntity<ExceptionDto> handleNoProductsFoundException(NoProductsFoundException exception) {
        ExceptionDto exceptionDto = ExceptionDto.builder()
                .message("no products found")
                .resolution("Add new products")
                .build();
        return new ResponseEntity<>(exceptionDto, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(CategoryNotFoundException.class)
    public ResponseEntity<ExceptionDto> handleCategoryNotFoundException(CategoryNotFoundException exception) {
        ExceptionDto exceptionDto = ExceptionDto.builder()
                .message("category with id:"+exception.getId()+" not found")
                .resolution("try again with another id")
                .build();
        return new ResponseEntity<>(exceptionDto, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(NoCategoriesFoundException.class)
    public ResponseEntity<ExceptionDto> handleNoCategoriesFoundException(NoCategoriesFoundException exception) {
        ExceptionDto exceptionDto = ExceptionDto.builder()
                .message("no categories found")
                .resolution("Add new categories")
                .build();
        return new ResponseEntity<>(exceptionDto, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(NoProductsFoundInCategoryException.class)
    public ResponseEntity<ExceptionDto> handleNoProductsFoundInCategoryException(NoProductsFoundInCategoryException exception) {
        ExceptionDto exceptionDto = ExceptionDto.builder()
                .message("no products found in category: "+exception.getCategory())
                .resolution("Add new products in "+exception.getCategory()+" category")
                .build();
        return new ResponseEntity<>(exceptionDto, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ProductNotCreatedException.class)
    public ResponseEntity<ExceptionDto> handleProductNotCreatedException(ProductNotCreatedException exception) {
        ExceptionDto exceptionDto = ExceptionDto.builder()
                .message("product not created")
                .resolution("try again later with all credentials")
                .build();
        return new ResponseEntity<>(exceptionDto, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(ProductNotUpdatedException.class)
    public ResponseEntity<ExceptionDto> handleProductNotUpdatedException(ProductNotUpdatedException exception) {
        ExceptionDto exceptionDto = ExceptionDto.builder()
                .message("product with id: "+exception.getId()+" not updated")
                .resolution("try again later with all credentials")
                .build();
        return new ResponseEntity<>(exceptionDto, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(ProductCredentialMissingException.class)
    public ResponseEntity<ExceptionDto> handleProductCredentialMissingException(ProductCredentialMissingException exception) {
        ExceptionDto exceptionDto = ExceptionDto.builder()
                .message("product credential: "+exception.getCredential()+" missing")
                .resolution("try again with all required credentials")
                .build();
        return new ResponseEntity<>(exceptionDto, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ExceptionDto> handleUserNotFoundException(UserNotFoundException exception) {
        ExceptionDto exceptionDto = ExceptionDto.builder()
                .message(exception.getMessage())
                .resolution("try again with different user")
                .build();
        return new ResponseEntity<>(exceptionDto, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(UnauthorizedToAccessThisProductException.class)
    public ResponseEntity<ExceptionDto> handleUnauthorizedToAccessThisProductException(UnauthorizedToAccessThisProductException exception) {
        ExceptionDto exceptionDto = ExceptionDto.builder()
                .message(exception.getMessage())
                .resolution("try again with different user")
                .build();
        return new ResponseEntity<>(exceptionDto, HttpStatus.UNAUTHORIZED);
    }
}
