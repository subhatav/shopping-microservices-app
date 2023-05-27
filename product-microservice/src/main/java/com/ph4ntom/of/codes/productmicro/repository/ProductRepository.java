package com.ph4ntom.of.codes.productmicro.repository;

import com.ph4ntom.of.codes.productmicro.model.Product;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ProductRepository extends MongoRepository<Product, String> {}
