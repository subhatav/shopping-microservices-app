package com.ph4ntom.of.codes.productmicro.controller;

import com.ph4ntom.of.codes.productmicro.dto.ProductRequest;
import com.ph4ntom.of.codes.productmicro.dto.ProductResponse;
import com.ph4ntom.of.codes.productmicro.service.ProductService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/product")
public class ProductController {

  private final ProductService productService;

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public void createProduct(final @RequestBody ProductRequest productRequest) {

    productService.createProduct(productRequest);
  }

  @GetMapping
  @ResponseStatus(HttpStatus.OK)
  public List<ProductResponse> getAllProducts() {

    return productService.getAllProducts();
  }
}
