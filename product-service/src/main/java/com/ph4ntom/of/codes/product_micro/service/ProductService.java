package com.ph4ntom.of.codes.product_micro.service;

import com.ph4ntom.of.codes.product_micro.dto.ProductRequest;
import com.ph4ntom.of.codes.product_micro.dto.ProductResponse;
import com.ph4ntom.of.codes.product_micro.model.Product;
import com.ph4ntom.of.codes.product_micro.repository.ProductRepository;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductService {

  private final ProductRepository productRepository;

  private static ProductResponse mapToProductResponse(final Product product) {

    return ProductResponse.builder().id(product.getId())
                          .name(product.getName())
                          .description(product.getDescription())
                          .price(product.getPrice()).build();
  }

  public void createProduct(final ProductRequest productRequest) {

    final Product product = Product.builder().name(productRequest.getName())
                                   .description(productRequest.getDescription())
                                   .price(productRequest.getPrice()).build();

    productRepository.save(product);

    log.info("Product #{} is saved.", product.getId());
  }

  public List<ProductResponse> getAllProducts() {

    final List<Product> products = productRepository.findAll();

    return products.stream().map(ProductService::mapToProductResponse).collect(Collectors.toList());
  }
}
