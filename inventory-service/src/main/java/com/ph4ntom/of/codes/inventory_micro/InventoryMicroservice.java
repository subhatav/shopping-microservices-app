package com.ph4ntom.of.codes.inventory_micro;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
public class InventoryMicroservice {

  public static void main(final String[] arguments) {

    SpringApplication.run(InventoryMicroservice.class, arguments);
  }
}
