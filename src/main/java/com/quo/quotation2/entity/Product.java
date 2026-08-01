package com.quo.quotation2.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "products")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "product_code", unique = true, nullable = false)
    private String productCode;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "category")
    private String category;

    @Column(name = "section_code")
    private String sectionCode;

    @Column(name = "hsn_code")
    private String hsnCode;

    @Column(name = "gst_rate")
    private Double gstRate;

    @Column(name = "price")
    private Double price;

    @Column(name = "power_hp")
    private Double powerHP;

    @Column(name = "power_kw")
    private Double powerKW;

    @Column(name = "shed_size")
    private String shedSize;

    @Column(name = "labor")
    private Integer labor;

    @Column(name = "production")
    private String production;

    @Column(name = "power")
    private String power;

    // ✅ IMAGE as LONGBLOB
    @Lob
    @Column(name = "image", columnDefinition = "LONGBLOB")
    private byte[] image;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;

    // Constructors
    public Product() {}

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getProductCode() { return productCode; }
    public void setProductCode(String productCode) { this.productCode = productCode; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }

    public String getSectionCode() { return sectionCode; }
    public void setSectionCode(String sectionCode) { this.sectionCode = sectionCode; }

    public String getHsnCode() { return hsnCode; }
    public void setHsnCode(String hsnCode) { this.hsnCode = hsnCode; }

    public Double getGstRate() { return gstRate; }
    public void setGstRate(Double gstRate) { this.gstRate = gstRate; }

    public Double getPrice() { return price; }
    public void setPrice(Double price) { this.price = price; }

    public Double getPowerHP() { return powerHP; }
    public void setPowerHP(Double powerHP) { this.powerHP = powerHP; }

    public Double getPowerKW() { return powerKW; }
    public void setPowerKW(Double powerKW) { this.powerKW = powerKW; }

    public String getShedSize() { return shedSize; }
    public void setShedSize(String shedSize) { this.shedSize = shedSize; }

    public Integer getLabor() { return labor; }
    public void setLabor(Integer labor) { this.labor = labor; }

    public String getProduction() { return production; }
    public void setProduction(String production) { this.production = production; }

    public String getPower() { return power; }
    public void setPower(String power) { this.power = power; }

    public byte[] getImage() { return image; }
    public void setImage(byte[] image) { this.image = image; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }

    public LocalDateTime getDeletedAt() { return deletedAt; }
    public void setDeletedAt(LocalDateTime deletedAt) { this.deletedAt = deletedAt; }
}