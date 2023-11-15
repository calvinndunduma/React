package com.youtube.react.controller;

import com.youtube.react.model.Product;
import com.youtube.react.model.ProductDto;
import com.youtube.react.service.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/product")
@Slf4j
public class ProductController {
    @Autowired
    private ProductService productService;
    @GetMapping("/get-all")
    public Flux<Product> products(){
        return productService.getAllProduct();
    }
    @GetMapping("/{id}")
    public Mono<ProductDto> product(@PathVariable String id){
        log.info("get all {}", productService.getAllProduct());
        return productService.getProduct(id);
    }
    @GetMapping("/product-range")
    public Flux<ProductDto> productRange(@RequestParam("min") double min, @RequestParam("max") double max){
        return productService.getProductsInRange(min, max);
    }
    @PostMapping("/create")
    public Mono<ProductDto> createProduct(@RequestBody Mono<ProductDto> productDtoMono){
        return productService.addProduct(productDtoMono);
    }
    @PutMapping("/update/{id}")
    public Mono<ProductDto> updateProduct(@RequestBody Mono<ProductDto> productDtoMono, @PathVariable String id){
        log.info(" updating records");
        return productService.updateProduct(productDtoMono,id);
    }
    @DeleteMapping("/delete/{id}")
    public Mono<Void> deleteProduct(@PathVariable  String id){
        log.info("deleting record");
        return productService.deleteProduct(id);
    }
}
