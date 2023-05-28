package com.ph4ntom.of.codes.order_micro.controller;

import com.ph4ntom.of.codes.order_micro.dto.OrderRequest;
import com.ph4ntom.of.codes.order_micro.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/order")
public class OrderController {

  private final OrderService orderService;

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public String placeOrder(final @RequestBody OrderRequest orderRequest) {

    orderService.placeOrder(orderRequest);

    return "Order placed successfully.";
  }
}
