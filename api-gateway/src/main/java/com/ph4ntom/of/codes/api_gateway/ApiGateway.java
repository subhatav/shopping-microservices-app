package com.ph4ntom.of.codes.api_gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
public class ApiGateway {

  public static void main(final String[] arguments) {

    SpringApplication.run(ApiGateway.class, arguments);
  }
}
