package com.quo.quotation2.dto.requestdto;

public class QuotationItemDto {
    private String id;
    private String productId;
    private String name;
    private String sectionCode;
    private String hsnCode;
    private Double gstRate;
    private Integer qty;
    private String unit;
    private Double rate;
    private Double amount;
    private Double powerHP;
    private Double powerKW;
    private Boolean inCustomerScope;
    private String shedSize;
    private Integer labor;
    private String production;
    private String power;
    private byte[] image; // ✅ Image as byte array

    public QuotationItemDto() {}

    // Getters and Setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getProductId() { return productId; }
    public void setProductId(String productId) { this.productId = productId; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getSectionCode() { return sectionCode; }
    public void setSectionCode(String sectionCode) { this.sectionCode = sectionCode; }

    public String getHsnCode() { return hsnCode; }
    public void setHsnCode(String hsnCode) { this.hsnCode = hsnCode; }

    public Double getGstRate() { return gstRate; }
    public void setGstRate(Double gstRate) { this.gstRate = gstRate; }

    public Integer getQty() { return qty; }
    public void setQty(Integer qty) { this.qty = qty; }

    public String getUnit() { return unit; }
    public void setUnit(String unit) { this.unit = unit; }

    public Double getRate() { return rate; }
    public void setRate(Double rate) { this.rate = rate; }

    public Double getAmount() { return amount; }
    public void setAmount(Double amount) { this.amount = amount; }

    public Double getPowerHP() { return powerHP; }
    public void setPowerHP(Double powerHP) { this.powerHP = powerHP; }

    public Double getPowerKW() { return powerKW; }
    public void setPowerKW(Double powerKW) { this.powerKW = powerKW; }

    public Boolean getInCustomerScope() { return inCustomerScope; }
    public void setInCustomerScope(Boolean inCustomerScope) { this.inCustomerScope = inCustomerScope; }

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
}