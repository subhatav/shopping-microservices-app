package com.ph4ntom.of.codes.inventory_micro.repository;

import com.ph4ntom.of.codes.inventory_micro.model.Inventory;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InventoryRepository extends JpaRepository<Inventory, Long> {

  Optional<Inventory> findBySkuCode(final String skuCode);
}
