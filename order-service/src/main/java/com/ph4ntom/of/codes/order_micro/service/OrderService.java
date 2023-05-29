package com.ph4ntom.of.codes.order_micro.service;

import com.ph4ntom.of.codes.order_micro.dto.InventoryResponse;
import com.ph4ntom.of.codes.order_micro.dto.OrderLineItemsDto;
import com.ph4ntom.of.codes.order_micro.dto.OrderRequest;
import com.ph4ntom.of.codes.order_micro.model.Order;
import com.ph4ntom.of.codes.order_micro.model.OrderLineItems;
import com.ph4ntom.of.codes.order_micro.repository.OrderRepository;
import java.net.URI;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.function.Function;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriBuilder;

@Service
@Transactional
@RequiredArgsConstructor
public class OrderService {

  private final OrderRepository orderRepository;
  private final WebClient.Builder webClientBuilder;

  private static OrderLineItems mapToOrderLineItems(final OrderLineItemsDto orderLineItemsDto) {

    final OrderLineItems orderLineItems = new OrderLineItems();

    orderLineItems.setSkuCode(orderLineItemsDto.getSkuCode());
    orderLineItems.setPrice(orderLineItemsDto.getPrice());
    orderLineItems.setQuantity(orderLineItemsDto.getQuantity());

    return orderLineItems;
  }

  private static Function<UriBuilder, URI> buildQueryParam(final List<String> skuCodes) {

    return (uriBuilder -> uriBuilder.queryParam("skuCode", skuCodes).build());
  }

  public String placeOrder(final OrderRequest orderRequest) {

    final Order order = new Order();

    order.setOrderNumber(UUID.randomUUID().toString());
    order.setOrderLineItems(orderRequest.getOrderLineItemsDtos()
         .stream().map(OrderService::mapToOrderLineItems).toList());

    // Call the Inventory Service, and place the Order if the Product is in stock

    final List<String> skuCodes = order.getOrderLineItems().stream()
                                       .map(OrderLineItems::getSkuCode).toList();

    final boolean allProductsInStock = Arrays.stream(getInventoryResponses(skuCodes))
                                             .allMatch(InventoryResponse::isInStock);

    if (allProductsInStock) {

      orderRepository.save(order);
      return "Order placed successfully.";

    } else throw new IllegalArgumentException("Product is not in stock. Please try again later!");
  }

  private InventoryResponse[] getInventoryResponses(final List<String> skuCodes) {

    return webClientBuilder.build().get().uri("http://inventory-service/api/inventory",
           buildQueryParam(skuCodes)).retrieve().bodyToMono(InventoryResponse[].class).block();
  }
}
