package com.ph4ntom.of.codes.discovery_server.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

  @Bean
  public SecurityFilterChain filterChain(final HttpSecurity httpSecurity) throws Exception {

    return httpSecurity.csrf().ignoringRequestMatchers("/eureka/**").and().build();
  }
}
