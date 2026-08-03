package com.quo.quotation2.dto.requestdto;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

public class ProductRequestDto {

    private String name;
    private String sku;
    private String modelCode;
    private String brand;
    private String type;
    private String category;
    private String subCategory;
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
    private Map<String, Double> dimensions;
    private Map<String, Object> warranty;
    private List<Map<String, String>> features;
    private String description;

    private byte[] thumbnailImage;
    private List<byte[]> galleryImages;
    private byte[] brochurePdf;

    private MultipartFile thumbnailFile;
    private List<MultipartFile> galleryFiles;
    private MultipartFile brochurePdfFile;

    // Getters and Setters
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

    public Map<String, Double> getDimensions() { return dimensions; }
    public void setDimensions(Map<String, Double> dimensions) { this.dimensions = dimensions; }

    public Map<String, Object> getWarranty() { return warranty; }
    public void setWarranty(Map<String, Object> warranty) { this.warranty = warranty; }

    public List<Map<String, String>> getFeatures() { return features; }
    public void setFeatures(List<Map<String, String>> features) { this.features = features; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public byte[] getThumbnailImage() { return thumbnailImage; }
    public void setThumbnailImage(byte[] thumbnailImage) { this.thumbnailImage = thumbnailImage; }

    public List<byte[]> getGalleryImages() { return galleryImages; }
    public void setGalleryImages(List<byte[]> galleryImages) { this.galleryImages = galleryImages; }

    public byte[] getBrochurePdf() { return brochurePdf; }
    public void setBrochurePdf(byte[] brochurePdf) { this.brochurePdf = brochurePdf; }

    public MultipartFile getThumbnailFile() { return thumbnailFile; }
    public void setThumbnailFile(MultipartFile thumbnailFile) { this.thumbnailFile = thumbnailFile; }

    public List<MultipartFile> getGalleryFiles() { return galleryFiles; }
    public void setGalleryFiles(List<MultipartFile> galleryFiles) { this.galleryFiles = galleryFiles; }

    public MultipartFile getBrochurePdfFile() { return brochurePdfFile; }
    public void setBrochurePdfFile(MultipartFile brochurePdfFile) { this.brochurePdfFile = brochurePdfFile; }
}


