package com.quo.quotation2.dto.responsedto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public class PurchaseResponseDto {

    private Long id;
    private String poNo;
    private Long supplierId;
    private String supplierName;
    private String supplierGstin;
    private LocalDate poDate;
    private LocalDate expectedDelivery;
    private Double subtotal;
    private Double gstPercent;
    private Double cgstAmount;
    private Double sgstAmount;
    private Double grandTotal;
    private String status;
    private String notes;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private List<PurchaseItemResponseDto> items;

    // Default constructor
    public PurchaseResponseDto() {}

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getPoNo() { return poNo; }
    public void setPoNo(String poNo) { this.poNo = poNo; }

    public Long getSupplierId() { return supplierId; }
    public void setSupplierId(Long supplierId) { this.supplierId = supplierId; }

    public String getSupplierName() { return supplierName; }
    public void setSupplierName(String supplierName) { this.supplierName = supplierName; }

    public String getSupplierGstin() { return supplierGstin; }
    public void setSupplierGstin(String supplierGstin) { this.supplierGstin = supplierGstin; }

    public LocalDate getPoDate() { return poDate; }
    public void setPoDate(LocalDate poDate) { this.poDate = poDate; }

    public LocalDate getExpectedDelivery() { return expectedDelivery; }
    public void setExpectedDelivery(LocalDate expectedDelivery) { this.expectedDelivery = expectedDelivery; }

    public Double getSubtotal() { return subtotal; }
    public void setSubtotal(Double subtotal) { this.subtotal = subtotal; }

    public Double getGstPercent() { return gstPercent; }
    public void setGstPercent(Double gstPercent) { this.gstPercent = gstPercent; }

    public Double getCgstAmount() { return cgstAmount; }
    public void setCgstAmount(Double cgstAmount) { this.cgstAmount = cgstAmount; }

    public Double getSgstAmount() { return sgstAmount; }
    public void setSgstAmount(Double sgstAmount) { this.sgstAmount = sgstAmount; }

    public Double getGrandTotal() { return grandTotal; }
    public void setGrandTotal(Double grandTotal) { this.grandTotal = grandTotal; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public String getNotes() { return notes; }
    public void setNotes(String notes) { this.notes = notes; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }

    public List<PurchaseItemResponseDto> getItems() { return items; }
    public void setItems(List<PurchaseItemResponseDto> items) { this.items = items; }

    // ============================================================
    // Inner Class: PurchaseItemResponseDto
    // ============================================================
    public static class PurchaseItemResponseDto {

        private Long id;
        private String name;
        private Integer qty;
        private Double rate;
        private Double amount;
        private String hsnCode;
        private Double gstRate;
        private String unit;

        // Default constructor
        public PurchaseItemResponseDto() {}

        // Parameterized constructor
        public PurchaseItemResponseDto(Long id, String name, Integer qty, Double rate, Double amount,
                                       String hsnCode, Double gstRate, String unit) {
            this.id = id;
            this.name = name;
            this.qty = qty;
            this.rate = rate;
            this.amount = amount;
            this.hsnCode = hsnCode;
            this.gstRate = gstRate;
            this.unit = unit;
        }

        // Getters and Setters
        public Long getId() { return id; }
        public void setId(Long id) { this.id = id; }

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
}
