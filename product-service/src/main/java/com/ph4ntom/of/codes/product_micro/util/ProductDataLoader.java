package com.ph4ntom.of.codes.product_micro.util;

import com.ph4ntom.of.codes.product_micro.model.Product;
import com.ph4ntom.of.codes.product_micro.repository.ProductRepository;
import java.math.BigDecimal;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ProductDataLoader implements CommandLineRunner {

  private final ProductRepository productRepository;

  @Override
  public void run(final String... arguments) throws Exception {

    if (productRepository.count() < 1) {

      final Product product = new Product();

      product.setName("iPhone 15");
      product.setDescription("Time to waste your money!");
      product.setPrice(BigDecimal.valueOf(1500));

      productRepository.save(product);
    }
  }
}
