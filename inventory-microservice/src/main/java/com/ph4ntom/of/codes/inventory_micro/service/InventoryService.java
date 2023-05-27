package com.ph4ntom.of.codes.inventory_micro.service;

import com.ph4ntom.of.codes.inventory_micro.dto.InventoryResponse;
import com.ph4ntom.of.codes.inventory_micro.model.Inventory;
import com.ph4ntom.of.codes.inventory_micro.repository.InventoryRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class InventoryService {

  private final InventoryRepository inventoryRepository;

  private static InventoryResponse mapToDto(Inventory inventory) {

    return InventoryResponse.builder().skuCode(inventory.getSkuCode())
                            .isInStock(inventory.getQuantity() > 0).build();
  }

  @Transactional(readOnly = true)
  public List<InventoryResponse> isInStock(final List<String> skuCode) {

    return inventoryRepository.findBySkuCodeIn(skuCode).stream()
                              .map(InventoryService::mapToDto).toList();
  }
}
