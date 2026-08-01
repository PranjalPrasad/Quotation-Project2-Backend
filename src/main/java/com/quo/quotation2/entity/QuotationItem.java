package com.quo.quotation2.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "quotation_items")
public class QuotationItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "quotation_id", nullable = false)
    private Quotation quotation;

    @Column(name = "product_id")
    private String productId;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "section_code")
    private String sectionCode;

    @Column(name = "hsn_code")
    private String hsnCode;

    @Column(name = "gst_rate")
    private Double gstRate;

    @Column(name = "qty", nullable = false)
    private Integer qty;

    @Column(name = "unit")
    private String unit;

    @Column(name = "rate", nullable = false)
    private Double rate;

    @Column(name = "amount", nullable = false)
    private Double amount;

    @Column(name = "power_hp")
    private Double powerHP;

    @Column(name = "power_kw")
    private Double powerKW;

    @Column(name = "in_customer_scope")
    private Boolean inCustomerScope;

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

    // Constructors
    public QuotationItem() {}

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Quotation getQuotation() { return quotation; }
    public void setQuotation(Quotation quotation) { this.quotation = quotation; }

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