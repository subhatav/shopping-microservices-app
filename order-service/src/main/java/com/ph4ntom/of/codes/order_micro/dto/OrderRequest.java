package com.ph4ntom.of.codes.order_micro.dto;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderRequest {

  private List<OrderLineItemsDto> orderLineItemsDtos;
}
