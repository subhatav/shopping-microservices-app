package com.ph4ntom.of.codes.order_micro.listener;

import com.ph4ntom.of.codes.order_micro.event.OrderPlacedEvent;
import io.micrometer.observation.Observation;
import io.micrometer.observation.ObservationRegistry;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class OrderPlacedEventListener {

  private final ObservationRegistry observationRegistry;
  private final KafkaTemplate<String, OrderPlacedEvent> kafkaTemplate;

  @EventListener
  public void handleOrderPlacedEvent(final OrderPlacedEvent orderPlacedEvent) {

    log.info("Received an `OrderPlacedEvent` <{}>.", orderPlacedEvent.getOrderNumber());
    log.info("Sending it to `notificationTopic`...");

    try {

      // Create an Observation for the Kafka Template

      Observation.createNotStarted("notification-topic", this.observationRegistry)
                 .observeChecked(() -> {

                  final CompletableFuture<SendResult<String, OrderPlacedEvent>> future = kafkaTemplate.send
                       ("notificationTopic", new OrderPlacedEvent(orderPlacedEvent.getOrderNumber()));

                  return future.handle((result, throwable) -> CompletableFuture.completedFuture(result));

                }).get();

    } catch (final InterruptedException | ExecutionException multiException) {

      Thread.currentThread().interrupt();

      throw new RuntimeException("Error faced while sending message to Kafka!", multiException);
    }
  }
}
