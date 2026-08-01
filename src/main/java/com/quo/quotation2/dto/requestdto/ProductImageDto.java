package com.quo.quotation2.dto.requestdto;

public class ProductImageDto {
    private String productId;
    private String productName;
    private byte[] image; // ✅ Image as byte array

    public ProductImageDto() {}

    public ProductImageDto(String productId, String productName, byte[] image) {
        this.productId = productId;
        this.productName = productName;
        this.image = image;
    }

    // Getters and Setters
    public String getProductId() { return productId; }
    public void setProductId(String productId) { this.productId = productId; }

    public String getProductName() { return productName; }
    public void setProductName(String productName) { this.productName = productName; }

    public byte[] getImage() { return image; }
    public void setImage(byte[] image) { this.image = image; }
}