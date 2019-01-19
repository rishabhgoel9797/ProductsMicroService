package com.example.ProductsMicroService.Services;

import com.example.ProductsMicroService.Entity.Product;

import java.util.List;
import java.util.Optional;

public interface ProductService {
    Product addProduct(Product product);
    List<Product> getAllProducts();
    Product findProduct(String productId);
    void deleteProduct(String id);
    Product update(Product product);
}
