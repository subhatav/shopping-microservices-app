package com.ph4ntom.of.codes.inventory_micro.util;

import com.ph4ntom.of.codes.inventory_micro.model.Inventory;
import com.ph4ntom.of.codes.inventory_micro.repository.InventoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class InventoryDataLoader implements CommandLineRunner {

  private final InventoryRepository inventoryRepository;

  @Override
  public void run(final String... arguments) throws Exception {

    final Inventory inventory1 = new Inventory();

    inventory1.setSkuCode("ipad_air");
    inventory1.setQuantity(0);

    final Inventory inventory2 = new Inventory();

    inventory2.setSkuCode("iphone_15");
    inventory2.setQuantity(100);

    inventoryRepository.save(inventory1);
    inventoryRepository.save(inventory2);
  }
}
