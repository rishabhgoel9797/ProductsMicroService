package com.example.ProductsMicroService.Repository;

import com.example.ProductsMicroService.Entity.Product;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ProductRepository extends MongoRepository<Product,String> {
}
