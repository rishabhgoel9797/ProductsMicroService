package com.example.ProductsMicroService.Services;

import com.example.ProductsMicroService.Entity.Category;
import com.example.ProductsMicroService.Entity.Product;
import com.example.ProductsMicroService.Repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceImplementation implements ProductService{

    @Autowired
    ProductRepository productRepository;


    @Override
    public Product addProduct(Product product) {
        return productRepository.insert(product);
    }

    @Override
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    @Override
    public Product findProduct(String productId) {
        return productRepository.findOne(productId);
    }


    @Override
    public void deleteProduct(String id) {
        productRepository.delete(id);
    }

    @Override
    public Product update(Product product) {
        return productRepository.save(product);
    }

    @Override
    public List<Product> findByProductName(String productName) {
        return productRepository.findByProductName(productName);
    }

    @Override
    public List<Product> findAllCategories() {
        return productRepository.findAll();
    }

    @Override
    public List<Product> findByCategoryName(String categoryName) {
        return productRepository.findByCategoryName(categoryName);
    }

    @Override
    public List<Product> findBySubCategory(String subCategoryName) {
        return productRepository.findBySubCategory(subCategoryName);
    }

    @Override
    public Product findOneProduct(String id) {
        return productRepository.findOne(id);
    }


}
