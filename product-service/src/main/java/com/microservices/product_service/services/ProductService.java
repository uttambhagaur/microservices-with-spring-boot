package com.microservices.product_service.services;

import com.microservices.product_service.dto.ProductRequest;
import com.microservices.product_service.dto.ProductResponse;
import com.microservices.product_service.exceptions.ProductNotFoundException;
import com.microservices.product_service.models.Product;
import com.microservices.product_service.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductService {

    private final ProductRepository productRepository;


    public ProductResponse addProduct(ProductRequest product) {
        log.info("product is going to save {}",product.getName());
        Product product1 = mapProductRequestToProduct(product);
        return mapProductToProductResponse(this.productRepository.save(product1));
    }

    private ProductResponse mapProductToProductResponse(Product product) {
        return ProductResponse.builder()
                .id(product.getId())
                .name(product.getName())
                .description(product.getDescription())
                .price(product.getPrice())
                .build();
    }

    private Product mapProductRequestToProduct(ProductRequest product) {
        return Product.builder()
                .name(product.getName())
                .price(product.getPrice())
                .description(product.getDescription())
                .build();
    }

    public List<ProductResponse> getAllProducts() {
        return this.productRepository.findAll().stream().map(this::mapProductToProductResponse).collect(Collectors.toList());
    }

    public ProductResponse getProductById(String productId) {
        return this.productRepository.findById(productId).map(product->mapProductToProductResponse(product)).orElseThrow(()-> new ProductNotFoundException(productId));
    }
}
