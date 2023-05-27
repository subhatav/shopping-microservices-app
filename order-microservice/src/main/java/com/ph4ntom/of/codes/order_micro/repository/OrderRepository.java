package com.ph4ntom.of.codes.order_micro.repository;

import com.ph4ntom.of.codes.order_micro.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {}
