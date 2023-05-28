package com.ph4ntom.of.codes.product_micro.repository;

import com.ph4ntom.of.codes.product_micro.model.Product;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ProductRepository extends MongoRepository<Product, String> {}
