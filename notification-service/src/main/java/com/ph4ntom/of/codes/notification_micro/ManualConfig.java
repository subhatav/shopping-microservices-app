package com.ph4ntom.of.codes.notification_micro;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;

/** Class for all Manual Configurations required for Observability to work */
@RequiredArgsConstructor
@Configuration(proxyBeanMethods = false)
public class ManualConfig {

  private final ConcurrentKafkaListenerContainerFactory kafkaListenerContainerFactory;

  @PostConstruct
  public void setup() {

    this.kafkaListenerContainerFactory.getContainerProperties().setObservationEnabled(true);
  }
}
