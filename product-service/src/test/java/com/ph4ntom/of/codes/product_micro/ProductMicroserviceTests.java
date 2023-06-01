package com.ph4ntom.of.codes.product_micro;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ph4ntom.of.codes.product_micro.dto.ProductRequest;
import com.ph4ntom.of.codes.product_micro.repository.ProductRepository;
import java.math.BigDecimal;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.junit.jupiter.Container;

@SpringBootTest
// @Testcontainers
@AutoConfigureMockMvc
class ProductMicroserviceTests {

  @Container
  static final MongoDBContainer mongoDBContainer = new MongoDBContainer("mongo:5.0.18-focal");

  static { mongoDBContainer.start(); }

  @Autowired private MockMvc mockMvc;
  @Autowired private ObjectMapper objectMapper;
  @Autowired private ProductRepository productRepository;

  @DynamicPropertySource
  static void setProperties(final DynamicPropertyRegistry dynamicPropertyRegistry) {

    dynamicPropertyRegistry.add("spring.data.mongodb.uri", mongoDBContainer::getReplicaSetUrl);
  }

  @Test
  void shouldCreateProduct() throws Exception {

    final ProductRequest productRequest = getProductRequest();

    mockMvc.perform(MockMvcRequestBuilders.post("/api/product").contentType(MediaType.APPLICATION_JSON)
           .content(objectMapper.writeValueAsString(productRequest))).andExpect(status().isCreated());

    assertEquals(2, productRepository.findAll().size());
  }

  private ProductRequest getProductRequest() {

    return ProductRequest.builder().name("iPad Air")
                         .description("Time to blow your money!")
                         .price(BigDecimal.valueOf(2500)).build();
  }
}
