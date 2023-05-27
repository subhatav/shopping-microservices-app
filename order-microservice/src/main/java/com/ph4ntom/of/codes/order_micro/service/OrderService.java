package com.ph4ntom.of.codes.order_micro.service;

import com.ph4ntom.of.codes.order_micro.dto.OrderLineItemsDto;
import com.ph4ntom.of.codes.order_micro.dto.OrderRequest;
import com.ph4ntom.of.codes.order_micro.model.Order;
import com.ph4ntom.of.codes.order_micro.model.OrderLineItems;
import com.ph4ntom.of.codes.order_micro.repository.OrderRepository;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderService {

  private final OrderRepository orderRepository;

  public void placeOrder(final OrderRequest orderRequest) {

    final Order order = new Order();

    order.setOrderNumber(UUID.randomUUID().toString());
    order.setOrderLineItems(orderRequest.getOrderLineItemsDtos().stream()
                                        .map(this::mapToDto).toList());

    orderRepository.save(order);
  }

  private OrderLineItems mapToDto(final OrderLineItemsDto orderLineItemsDto) {

    final OrderLineItems orderLineItems = new OrderLineItems();

    orderLineItems.setSkuCode(orderLineItemsDto.getSkuCode());
    orderLineItems.setPrice(orderLineItemsDto.getPrice());
    orderLineItems.setQuantity(orderLineItemsDto.getQuantity());

    return orderLineItems;
  }
}
