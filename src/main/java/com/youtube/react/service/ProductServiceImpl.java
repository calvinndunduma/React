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

import java.time.LocalDate;

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
    public Flux<ProductDto> getProductsCreatedBetween(LocalDate startDate, LocalDate endDate) {
        return repository.findAllByLocalDateBetween(startDate,endDate);
    }
    @Override
    public Mono<ProductDto> addProduct(Mono<ProductDto> productDtoMono){
        return productDtoMono.map(AppUtils::dtoToEntity)
                .flatMap(repository::save) // one to many
                .map(AppUtils::entityToDto); // one to one
    }
    @Override
    public Mono<ProductDto> updateProduct(ProductDto product, String id){
        return repository.findById(id)
                .map(
                        (p)->{
                            p.setName(product.getName());
                            p.setLocalDate(product.getLocalDate());
                            p.setPrice(product.getPrice());
                            p.setQuantity(product.getQuantity());
                            return p;
                        }).flatMap(repository::save) //p->repository.save(p)
                .map(AppUtils::entityToDto);
    }
    @Override
    public Mono<Void> deleteProduct(String id){
        return repository.deleteById(id);
    }
}
