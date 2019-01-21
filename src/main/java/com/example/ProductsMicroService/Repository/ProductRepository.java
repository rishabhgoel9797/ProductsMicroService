package com.example.ProductsMicroService.Repository;

import com.example.ProductsMicroService.Entity.Category;
import com.example.ProductsMicroService.Entity.Product;
import com.mongodb.client.MongoCollection;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.ArrayList;
import java.util.List;

public interface ProductRepository extends MongoRepository<Product,String> {
    @Query(value = "{'productName':?0}")
    List<Product> findByProductName(String productName);

    @Query(value = "{'subCategory.category.categoryName':?0}")
    List<Product> findByCategoryName(String categoryName);

    @Query(value = "{'subCategory.subcategoryName':?0}")
    List<Product> findBySubCategory(String subcategoryName);

//    @Query(value = "{categoryName}")
//    List<Category> findAllCategories();
//    List<String> productCategories=new ArrayList<>();
}
