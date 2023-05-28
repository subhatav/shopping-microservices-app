package com.ph4ntom.of.codes.product_micro;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
public class ProductMicroservice {

  public static void main(final String[] arguments) {

    SpringApplication.run(ProductMicroservice.class, arguments);
  }
}
