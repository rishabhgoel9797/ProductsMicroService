package com.example.ProductsMicroService.Services;

import com.example.ProductsMicroService.Entity.Category;
import com.example.ProductsMicroService.Entity.Product;

import java.util.List;
import java.util.Optional;

public interface ProductService {
    Product addProduct(Product product);
    List<Product> getAllProducts();
    Product findProduct(String productId);
    void deleteProduct(String id);
    Product update(Product product);
    List<Product> findByProductName(String productName);
    List<Product> findAllCategories();

    List<Product> findByCategoryName(String categoryName);

    List<Product> findBySubCategory(String subCategoryName);
}
