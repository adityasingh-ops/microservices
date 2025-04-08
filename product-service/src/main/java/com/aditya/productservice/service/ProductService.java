package com.aditya.productservice.service;

import com.aditya.productservice.dto.ProductRequest;
import com.aditya.productservice.dto.ProductResponse;
import com.aditya.productservice.model.Product;
import com.aditya.productservice.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;

    public void createProduct(ProductRequest productRequest) {

        productRepository.save(converToProduct(productRequest));
        log.info("Product created: " + productRequest.toString());
    }
    public List<ProductResponse> getAllProducts() {
        List<Product> products = productRepository.findAll();
//        return products.stream().map(this::convertProductToProductResponse).toList();
        /// or we can say it like this too
        return products.stream().map(product -> convertProductToProductResponse(product)).toList();
    }
    private Product converToProduct(ProductRequest productRequest) {
        return Product.builder()
                .description(productRequest.getDescription())
                .price(productRequest.getPrice())
                .name(productRequest.getName())
                .build();
    }
    private ProductResponse convertProductToProductResponse(Product product) {
        return ProductResponse.builder()
                .id(product.getId())
                .name(product.getName())
                .description(product.getDescription())
                .price(product.getPrice())
                .build();
    }
}
