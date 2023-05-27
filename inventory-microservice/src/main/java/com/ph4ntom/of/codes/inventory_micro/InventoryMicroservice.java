package com.ph4ntom.of.codes.inventory_micro;

import com.ph4ntom.of.codes.inventory_micro.model.Inventory;
import com.ph4ntom.of.codes.inventory_micro.repository.InventoryRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class InventoryMicroservice {

  public static void main(final String[] arguments) {

    SpringApplication.run(InventoryMicroservice.class, arguments);
  }

  @Bean
  public CommandLineRunner loadInventoryData(final InventoryRepository inventoryRepository) {

    return arguments -> {

      final Inventory inventory1 = new Inventory();

      inventory1.setSkuCode("iphone_14");
      inventory1.setQuantity(100);

      final Inventory inventory2 = new Inventory();

      inventory2.setSkuCode("ipad_air");
      inventory2.setQuantity(0);

      inventoryRepository.save(inventory1);
      inventoryRepository.save(inventory2);
    };
  }
}
