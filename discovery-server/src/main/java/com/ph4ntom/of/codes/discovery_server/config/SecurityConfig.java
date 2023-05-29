package com.ph4ntom.of.codes.discovery_server.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

  @Value("${eureka.username}")
  private String username;

  @Value("${eureka.password}")
  private String password;

  @Bean
  public PasswordEncoder passwordEncoder() {

    // return new BCryptPasswordEncoder();
    return NoOpPasswordEncoder.getInstance();
  }

  @Bean
  public InMemoryUserDetailsManager userDetailsService() {

    final UserDetails userDetails = User.withUsername(username).password(password)
                                        .authorities("USER").build();

    return new InMemoryUserDetailsManager(userDetails);
  }

  @Bean
  public SecurityFilterChain filterChain(final HttpSecurity httpSecurity) throws Exception {

    return httpSecurity.csrf().disable().authorizeHttpRequests(auth -> auth.anyRequest().authenticated())
                                        .httpBasic(Customizer.withDefaults()).build();
  }
}
