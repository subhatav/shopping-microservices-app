package com.ph4ntom.of.codes.inventory_micro.controller;

import com.ph4ntom.of.codes.inventory_micro.dto.InventoryResponse;
import com.ph4ntom.of.codes.inventory_micro.service.InventoryService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/inventory")
public class InventoryController {

  private final InventoryService inventoryService;

  // http://inventory-service/api/inventory?skuCode=iphone_14&skuCode=ipad_air
  @GetMapping()
  @ResponseStatus(HttpStatus.OK)
  public List<InventoryResponse> isInStock(final @RequestParam List<String> skuCode) {

    log.info("Received request to check the Inventory for `skuCode` <{}>.", skuCode);

    return inventoryService.isInStock(skuCode);
  }
}
