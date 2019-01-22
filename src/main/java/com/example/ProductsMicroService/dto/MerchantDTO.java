package com.example.ProductsMicroService.dto;

public class MerchantDTO {
    private String merchantId;
    private String merchantName;
    private String merchantCity;
    private String merchantRating;

    public String getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(String merchantId) {
        this.merchantId = merchantId;
    }

    public String getMerchantName() {
        return merchantName;
    }

    public void setMerchantName(String merchantName) {
        this.merchantName = merchantName;
    }

    public String getMerchantCity() {
        return merchantCity;
    }

    public void setMerchantCity(String merchantCity) {
        this.merchantCity = merchantCity;
    }

    public String getMerchantRating() {
        return merchantRating;
    }

    public void setMerchantRating(String merchantRating) {
        this.merchantRating = merchantRating;
    }
}
