package com.youtube.react.service;

import com.youtube.react.model.Product;
import com.youtube.react.model.ProductDto;
import com.youtube.react.repo.ProductRepo;
import com.youtube.react.utils.AppUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Range;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    private ProductRepo repository;
    @Override
    public Flux<Product> getAllProduct(){
        return repository.findAll();
    }
    @Override
    public Mono<ProductDto> getProduct(String id){
        return repository.findById(id).map(AppUtils::entityToDto);
    }
    @Override
    public Flux<ProductDto> getProductsInRange(double min, double max){
        return repository.findByPriceBetween(Range.closed(min,max));
    }
    @Override
    public Mono<ProductDto> addProduct(Mono<ProductDto> productDtoMono){
        return productDtoMono.map(AppUtils::dtoToEntity)
                .flatMap(repository::insert) // one to many
                .map(AppUtils::entityToDto); // one to one
    }
    @Override
    public Mono<ProductDto> updateProduct(Mono<ProductDto> productDtoMono, String id){
        return repository.findById(id)
                .flatMap(p -> productDtoMono.map(AppUtils::dtoToEntity))
                .doOnNext(e -> e.setId(id))
                .doOnNext(q -> q.setName(""))
                .flatMap(repository::save)
                .map(AppUtils::entityToDto);
    }
    @Override
    public Mono<Void> deleteProduct(String id){
        return repository.deleteById(id);
    }
}
