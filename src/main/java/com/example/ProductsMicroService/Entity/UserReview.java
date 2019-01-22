package com.example.ProductsMicroService.Entity;

import org.springframework.data.annotation.Id;

import javax.annotation.Generated;

public class UserReview {

    private String productId;
    private String userComment;
    private int userRatingOnProduct;

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getUserComment() {
        return userComment;
    }

    public void setUserComment(String userComment) {
        this.userComment = userComment;
    }

    public int getUserRatingOnProduct() {
        return userRatingOnProduct;
    }

    public void setUserRatingOnProduct(int userRatingOnProduct) {
        this.userRatingOnProduct = userRatingOnProduct;
    }
}
