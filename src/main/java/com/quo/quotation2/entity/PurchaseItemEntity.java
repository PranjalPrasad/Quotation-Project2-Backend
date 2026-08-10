package com.quo.quotation2.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "purchase_items")
public class PurchaseItemEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "purchase_order_id", nullable = false)
    private PurchaseOrderEntity purchaseOrder;

    @Column(name = "name", nullable = false, length = 200)
    private String name;

    @Column(name = "qty", nullable = false)
    private Integer qty;

    @Column(name = "rate", nullable = false)
    private Double rate;

    @Column(name = "amount", nullable = false)
    private Double amount;

    @Column(name = "hsn_code", length = 20)
    private String hsnCode;

    @Column(name = "gst_rate")
    private Double gstRate;

    @Column(name = "unit", length = 20)
    private String unit;

    // Constructors
    public PurchaseItemEntity() {}

    public PurchaseItemEntity(String name, Integer qty, Double rate, Double amount) {
        this.name = name;
        this.qty = qty;
        this.rate = rate;
        this.amount = amount;
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public PurchaseOrderEntity getPurchaseOrder() { return purchaseOrder; }
    public void setPurchaseOrder(PurchaseOrderEntity purchaseOrder) { this.purchaseOrder = purchaseOrder; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public Integer getQty() { return qty; }
    public void setQty(Integer qty) { this.qty = qty; }

    public Double getRate() { return rate; }
    public void setRate(Double rate) { this.rate = rate; }

    public Double getAmount() { return amount; }
    public void setAmount(Double amount) { this.amount = amount; }

    public String getHsnCode() { return hsnCode; }
    public void setHsnCode(String hsnCode) { this.hsnCode = hsnCode; }

    public Double getGstRate() { return gstRate; }
    public void setGstRate(Double gstRate) { this.gstRate = gstRate; }

    public String getUnit() { return unit; }
    public void setUnit(String unit) { this.unit = unit; }
}
