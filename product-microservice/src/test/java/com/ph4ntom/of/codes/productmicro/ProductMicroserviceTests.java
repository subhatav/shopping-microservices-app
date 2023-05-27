package com.ph4ntom.of.codes.productmicro;

import static com.mongodb.assertions.Assertions.assertTrue;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ph4ntom.of.codes.productmicro.dto.ProductRequest;
import com.ph4ntom.of.codes.productmicro.repository.ProductRepository;
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

    assertTrue(productRepository.findAll().size() == 1);
  }

  private ProductRequest getProductRequest() {

    return ProductRequest.builder().name("iPhone 14")
                         .description("Great opportunity to waste your money!")
                         .price(BigDecimal.valueOf(1200)).build();
  }
}
