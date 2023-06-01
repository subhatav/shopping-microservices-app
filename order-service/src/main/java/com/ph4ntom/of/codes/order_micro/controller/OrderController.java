package com.ph4ntom.of.codes.order_micro.controller;

import com.ph4ntom.of.codes.order_micro.dto.OrderRequest;
import com.ph4ntom.of.codes.order_micro.service.OrderService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import io.github.resilience4j.timelimiter.annotation.TimeLimiter;
import java.util.concurrent.CompletableFuture;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/order")
public class OrderController {

  private final OrderService orderService;

  @PostMapping
  @Retry(name = "inventory")
  @TimeLimiter(name = "inventory")
  @ResponseStatus(HttpStatus.CREATED)
  @CircuitBreaker(name = "inventory", fallbackMethod = "fallback")
  public CompletableFuture<String> placeOrder(final @RequestBody OrderRequest orderRequest) {

    log.info("Placing the Order...");

    return CompletableFuture.supplyAsync(() -> orderService.placeOrder(orderRequest));
  }

  public CompletableFuture<String> fallback(final OrderRequest orderRequest,
                                            final RuntimeException runtimeException) {

    log.info("Cannot place the Order! Executing the fallback logic...");

    return CompletableFuture.supplyAsync(() -> "Oops! Something went wrong, please order after sometime.");
  }
}
