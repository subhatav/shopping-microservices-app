package com.ph4ntom.of.codes.inventory_micro.service;

import com.ph4ntom.of.codes.inventory_micro.dto.InventoryResponse;
import com.ph4ntom.of.codes.inventory_micro.model.Inventory;
import com.ph4ntom.of.codes.inventory_micro.repository.InventoryRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class InventoryService {

  private final InventoryRepository inventoryRepository;

  private static InventoryResponse mapToInventoryResponse(final Inventory inventory) {

    return InventoryResponse.builder().skuCode(inventory.getSkuCode())
                            .isInStock(inventory.getQuantity() > 0).build();
  }

  // @SneakyThrows
  @Transactional(readOnly = true)
  public List<InventoryResponse> isInStock(final List<String> skuCode) {

    log.info("Checking the Inventory for availability...");

    // log.info("Waiting has started!");
    // Thread.sleep(10000);
    // log.info("Waiting has finished!");

    return inventoryRepository.findBySkuCodeIn(skuCode).stream()
                              .map(InventoryService::mapToInventoryResponse).toList();
  }
}
