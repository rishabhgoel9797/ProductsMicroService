package com.example.ProductsMicroService.dto;

public class ShortListingDTO {
    private String productId;
    private String productName;
    private String productImage;
    private int marketRetailPrice;
    private int discountedPrice;

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductImage() {
        return productImage;
    }

    public void setProductImage(String productImage) {
        this.productImage = productImage;
    }

    public int getMarketRetailPrice() {
        return marketRetailPrice;
    }

    public void setMarketRetailPrice(int marketRetailPrice) {
        this.marketRetailPrice = marketRetailPrice;
    }

    public int getDiscountedPrice() {
        return discountedPrice;
    }

    public void setDiscountedPrice(int discountedPrice) {
        this.discountedPrice = discountedPrice;
    }
}
