package com.ph4ntom.of.codes.notification_micro;

import io.micrometer.observation.Observation;
import io.micrometer.observation.ObservationRegistry;
import io.micrometer.tracing.Tracer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.annotation.KafkaListener;

@Slf4j
@SpringBootApplication
@RequiredArgsConstructor
public class NotificationMicroservice {

  private final Tracer tracer;
  private final ObservationRegistry observationRegistry;

  public static void main(final String[] arguments) {

    SpringApplication.run(NotificationMicroservice.class, arguments);
  }

  @KafkaListener(topics = "notificationTopic")
  public void handleNotification(final OrderPlacedEvent orderPlacedEvent) {

    Observation.createNotStarted("on-message", this.observationRegistry)
               .observe(() -> {

                log.info("Received the message <{}>.", orderPlacedEvent);

                log.info("[TraceId: {}] Got notification for Order #{}.",
                         this.tracer.currentSpan().context().traceId(),
                         orderPlacedEvent.getOrderNumber());
                });

    // Send out an Email Notification from here [logic]
  }
}
