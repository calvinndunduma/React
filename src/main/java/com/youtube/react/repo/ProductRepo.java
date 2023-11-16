package com.youtube.react.repo;

import com.youtube.react.model.Product;
import com.youtube.react.model.ProductDto;
import org.springframework.data.domain.Range;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;

import java.time.LocalDate;

public interface ProductRepo extends ReactiveMongoRepository<Product, String> {
    Flux<ProductDto> findByPriceBetween(Range<Double> closed);
    Flux<ProductDto> findAllByLocalDateBetween(LocalDate startDate, LocalDate endDate);
}
