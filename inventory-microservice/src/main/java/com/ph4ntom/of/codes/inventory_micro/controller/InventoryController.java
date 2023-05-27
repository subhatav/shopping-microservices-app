package com.ph4ntom.of.codes.inventory_micro.controller;

import com.ph4ntom.of.codes.inventory_micro.service.InventoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/inventory")
public class InventoryController {

  private final InventoryService inventoryService;

  @GetMapping("/{sku-code}")
  @ResponseStatus(HttpStatus.OK)
  public boolean isInStock(final @PathVariable("sku-code") String skuCode) {

    return inventoryService.isInStock(skuCode);
  }
}
