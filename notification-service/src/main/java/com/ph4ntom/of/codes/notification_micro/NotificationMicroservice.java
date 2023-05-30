package com.ph4ntom.of.codes.notification_micro;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.annotation.KafkaListener;

@Slf4j
@SpringBootApplication
public class NotificationMicroservice {

  public static void main(final String[] arguments) {

    SpringApplication.run(NotificationMicroservice.class, arguments);
  }

  @KafkaListener(topics = "notificationTopic")
  public void handleNotification(final OrderPlacedEvent orderPlacedEvent) {

    // Send out an Email Notification from here [logic]

    log.info("Notification received for Order #{}.", orderPlacedEvent.getOrderNumber());
  }
}
