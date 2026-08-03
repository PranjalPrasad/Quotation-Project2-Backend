package com.quo.quotation2.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "products")
public class ProductEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String name;

    @Column(unique = true, nullable = false, length = 50)
    private String sku;

    @Column(length = 50)
    private String modelCode;

    @Column(length = 50)
    private String brand;

    @Column(nullable = false, length = 20)
    private String type;

    @Column(nullable = false, length = 50)
    private String category;

    @Column(length = 50)
    private String subCategory;

    @Column(length = 20)
    private String hsn;

    private Integer gst;

    private Double mrp;
    private String discountType;
    private Double discountValue;
    private Double calculatedPrice;
    private Double finalPrice;

    private Integer stock;
    private Integer threshold;
    private Integer reorderQuantity;
    private Integer leadTimeDays;
    private String status;

    private Double powerConsumptionKw;
    private Double weightKg;

    @Column(name = "dimension_length_cm")
    private Double lengthCm;

    @Column(name = "dimension_width_cm")
    private Double widthCm;

    @Column(name = "dimension_height_cm")
    private Double heightCm;

    @Column(name = "warranty_period_years")
    private Integer warrantyPeriodYears;

    @Column(name = "warranty_type", length = 50)
    private String warrantyType;

    @Column(name = "warranty_parts_covered", length = 255)
    private String warrantyPartsCovered;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    @JoinColumn(name = "product_id")
    private List<Feature> features = new ArrayList<>();

    @Lob
    @Column(name = "thumbnail_image", columnDefinition = "LONGBLOB")
    private byte[] thumbnailImage;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "product_gallery", joinColumns = @JoinColumn(name = "product_id"))
    @Lob
    @Column(name = "gallery_image", columnDefinition = "LONGBLOB")
    private List<byte[]> galleryImages = new ArrayList<>();

    @Lob
    @Column(name = "brochure_pdf", columnDefinition = "LONGBLOB")
    private byte[] brochurePdf;

    @Column(length = 1000)
    private String description;

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getSku() { return sku; }
    public void setSku(String sku) { this.sku = sku; }

    public String getModelCode() { return modelCode; }
    public void setModelCode(String modelCode) { this.modelCode = modelCode; }

    public String getBrand() { return brand; }
    public void setBrand(String brand) { this.brand = brand; }

    public String getType() { return type; }
    public void setType(String type) { this.type = type; }

    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }

    public String getSubCategory() { return subCategory; }
    public void setSubCategory(String subCategory) { this.subCategory = subCategory; }

    public String getHsn() { return hsn; }
    public void setHsn(String hsn) { this.hsn = hsn; }

    public Integer getGst() { return gst; }
    public void setGst(Integer gst) { this.gst = gst; }

    public Double getMrp() { return mrp; }
    public void setMrp(Double mrp) { this.mrp = mrp; }

    public String getDiscountType() { return discountType; }
    public void setDiscountType(String discountType) { this.discountType = discountType; }

    public Double getDiscountValue() { return discountValue; }
    public void setDiscountValue(Double discountValue) { this.discountValue = discountValue; }

    public Double getCalculatedPrice() { return calculatedPrice; }
    public void setCalculatedPrice(Double calculatedPrice) { this.calculatedPrice = calculatedPrice; }

    public Double getFinalPrice() { return finalPrice; }
    public void setFinalPrice(Double finalPrice) { this.finalPrice = finalPrice; }

    public Integer getStock() { return stock; }
    public void setStock(Integer stock) { this.stock = stock; }

    public Integer getThreshold() { return threshold; }
    public void setThreshold(Integer threshold) { this.threshold = threshold; }

    public Integer getReorderQuantity() { return reorderQuantity; }
    public void setReorderQuantity(Integer reorderQuantity) { this.reorderQuantity = reorderQuantity; }

    public Integer getLeadTimeDays() { return leadTimeDays; }
    public void setLeadTimeDays(Integer leadTimeDays) { this.leadTimeDays = leadTimeDays; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public Double getPowerConsumptionKw() { return powerConsumptionKw; }
    public void setPowerConsumptionKw(Double powerConsumptionKw) { this.powerConsumptionKw = powerConsumptionKw; }

    public Double getWeightKg() { return weightKg; }
    public void setWeightKg(Double weightKg) { this.weightKg = weightKg; }

    public Double getLengthCm() { return lengthCm; }
    public void setLengthCm(Double lengthCm) { this.lengthCm = lengthCm; }

    public Double getWidthCm() { return widthCm; }
    public void setWidthCm(Double widthCm) { this.widthCm = widthCm; }

    public Double getHeightCm() { return heightCm; }
    public void setHeightCm(Double heightCm) { this.heightCm = heightCm; }

    public Integer getWarrantyPeriodYears() { return warrantyPeriodYears; }
    public void setWarrantyPeriodYears(Integer warrantyPeriodYears) { this.warrantyPeriodYears = warrantyPeriodYears; }

    public String getWarrantyType() { return warrantyType; }
    public void setWarrantyType(String warrantyType) { this.warrantyType = warrantyType; }

    public String getWarrantyPartsCovered() { return warrantyPartsCovered; }
    public void setWarrantyPartsCovered(String warrantyPartsCovered) { this.warrantyPartsCovered = warrantyPartsCovered; }

    public List<Feature> getFeatures() { return features; }
    public void setFeatures(List<Feature> features) { this.features = features; }

    public byte[] getThumbnailImage() { return thumbnailImage; }
    public void setThumbnailImage(byte[] thumbnailImage) { this.thumbnailImage = thumbnailImage; }

    public List<byte[]> getGalleryImages() { return galleryImages; }
    public void setGalleryImages(List<byte[]> galleryImages) { this.galleryImages = galleryImages; }

    public byte[] getBrochurePdf() { return brochurePdf; }
    public void setBrochurePdf(byte[] brochurePdf) { this.brochurePdf = brochurePdf; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }

    @Entity
    @Table(name = "product_features")
    public static class Feature {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        @Column(name = "feature_id", length = 20)
        private String featureId;

        @Column(nullable = false, length = 50)
        private String label;

        @Column(nullable = false, length = 100)
        private String value;

        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "product_id")
        private ProductEntity product;

        public Feature() {}

        public Feature(String featureId, String label, String value) {
            this.featureId = featureId;
            this.label = label;
            this.value = value;
        }

        public Long getId() { return id; }
        public void setId(Long id) { this.id = id; }

        public String getFeatureId() { return featureId; }
        public void setFeatureId(String featureId) { this.featureId = featureId; }

        public String getLabel() { return label; }
        public void setLabel(String label) { this.label = label; }

        public String getValue() { return value; }
        public void setValue(String value) { this.value = value; }

        public ProductEntity getProduct() { return product; }
        public void setProduct(ProductEntity product) { this.product = product; }
    }
}


