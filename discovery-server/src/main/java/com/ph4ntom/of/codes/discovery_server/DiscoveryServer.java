package com.ph4ntom.of.codes.discovery_server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@EnableEurekaServer
@SpringBootApplication
public class DiscoveryServer {

  public static void main(final String[] arguments) {

    SpringApplication.run(DiscoveryServer.class, arguments);
  }
}
