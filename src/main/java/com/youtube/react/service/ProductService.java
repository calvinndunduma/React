package com.youtube.react.service;

import com.youtube.react.model.Product;
import com.youtube.react.model.ProductDto;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDate;

public interface ProductService {
    Flux<Product> getAllProduct();

    Mono<ProductDto> getProduct(String id);

    Flux<ProductDto> getProductsInRange(double min, double max);
    Flux<ProductDto> getProductsCreatedBetween(LocalDate startDate, LocalDate endDate);

    Mono<ProductDto> addProduct(Mono<ProductDto> productDtoMono);

    Mono<ProductDto> updateProduct(ProductDto productDtoMono, String id);

    Mono<Void> deleteProduct(String id);
}
