package com.quo.quotation2.dto.requestdto;


public class ProductDto {
    private Long id;
    private String productCode;
    private String name;
    private String category;
    private String sectionCode;
    private String hsnCode;
    private Double gstRate;
    private Double price;
    private Double powerHP;
    private Double powerKW;
    private String shedSize;
    private Integer labor;
    private String production;
    private String power;

    // Constructors
    public ProductDto() {}

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
}
