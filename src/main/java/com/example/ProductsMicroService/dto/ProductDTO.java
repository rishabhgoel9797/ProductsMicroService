package com.example.ProductsMicroService.dto;

import com.example.ProductsMicroService.Entity.Brand;
import com.example.ProductsMicroService.Entity.Category;
import com.example.ProductsMicroService.Entity.Specification;
import com.example.ProductsMicroService.Entity.SubCategory;

import java.util.List;

public class ProductDTO {
    private String productId;
    private  String productName;
    private String description;
    private String usp;
    private String productImage;


    public List<SubCategory> subCategory;
    public Brand brand;
    public Specification specification;



    public List<SubCategory> getSubCategory() {
        return subCategory;
    }

    public void setSubCategory(List<SubCategory> subCategory) {
        this.subCategory = subCategory;
    }

    public Brand getBrand() {
        return brand;
    }

    public void setBrand(Brand brand) {
        this.brand = brand;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getUsp() {
        return usp;
    }

    public void setUsp(String usp) {
        this.usp = usp;
    }

    public String getProductImage() {
        return productImage;
    }

    public void setProductImage(String productImage) {
        this.productImage = productImage;
    }

    public Specification getSpecification() {
        return specification;
    }

    public void setSpecification(Specification specification) {
        this.specification = specification;
    }
}
