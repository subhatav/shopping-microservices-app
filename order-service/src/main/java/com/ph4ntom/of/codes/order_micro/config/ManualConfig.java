package com.ph4ntom.of.codes.order_micro.config;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.KafkaTemplate;

/** Class for all Manual Configurations required for Observability to work */

@RequiredArgsConstructor
@Configuration(proxyBeanMethods = false)
public class ManualConfig {

  private final KafkaTemplate kafkaTemplate;

  @PostConstruct
  public void setup() { this.kafkaTemplate.setObservationEnabled(true); }
}
