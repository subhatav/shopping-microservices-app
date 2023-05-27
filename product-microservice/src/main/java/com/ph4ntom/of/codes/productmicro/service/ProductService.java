package com.ph4ntom.of.codes.productmicro.service;

import com.ph4ntom.of.codes.productmicro.dto.ProductRequest;
import com.ph4ntom.of.codes.productmicro.dto.ProductResponse;
import com.ph4ntom.of.codes.productmicro.model.Product;
import com.ph4ntom.of.codes.productmicro.repository.ProductRepository;
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

  public void createProduct(final ProductRequest productRequest) {

    final Product product = Product.builder().name(productRequest.getName())
                                   .description(productRequest.getDescription())
                                   .price(productRequest.getPrice()).build();

    productRepository.save(product);

    log.info("Product #{} is saved.", product.getId());
  }

  public List<ProductResponse> getAllProducts() {

    final List<Product> products = productRepository.findAll();

    return products.stream().map(this::mapToProductResponse).collect(Collectors.toList());
  }

  private ProductResponse mapToProductResponse(final Product product) {

    return ProductResponse.builder().id(product.getId())
                          .name(product.getName())
                          .description(product.getDescription())
                          .price(product.getPrice()).build();
  }
}
