package com.quo.quotation2.dto.requestdto;


import java.time.LocalDate;
import java.util.List;

public class PurchaseRequestDto {

    private Long supplierId;
    private LocalDate poDate;
    private LocalDate expectedDelivery;
    private Double gstPercent;
    private String notes;
    private List<PurchaseItemDto> items;

    // Default constructor
    public PurchaseRequestDto() {}

    // Parameterized constructor
    public PurchaseRequestDto(Long supplierId, LocalDate poDate, LocalDate expectedDelivery,
                              Double gstPercent, String notes, List<PurchaseItemDto> items) {
        this.supplierId = supplierId;
        this.poDate = poDate;
        this.expectedDelivery = expectedDelivery;
        this.gstPercent = gstPercent;
        this.notes = notes;
        this.items = items;
    }

    // Getters and Setters
    public Long getSupplierId() { return supplierId; }
    public void setSupplierId(Long supplierId) { this.supplierId = supplierId; }

    public LocalDate getPoDate() { return poDate; }
    public void setPoDate(LocalDate poDate) { this.poDate = poDate; }

    public LocalDate getExpectedDelivery() { return expectedDelivery; }
    public void setExpectedDelivery(LocalDate expectedDelivery) { this.expectedDelivery = expectedDelivery; }

    public Double getGstPercent() { return gstPercent; }
    public void setGstPercent(Double gstPercent) { this.gstPercent = gstPercent; }

    public String getNotes() { return notes; }
    public void setNotes(String notes) { this.notes = notes; }

    public List<PurchaseItemDto> getItems() { return items; }
    public void setItems(List<PurchaseItemDto> items) { this.items = items; }

    // ============================================================
    // Inner Class: PurchaseItemDto
    // ============================================================
    public static class PurchaseItemDto {

        private String name;
        private Integer qty;
        private Double rate;
        private String hsnCode;
        private Double gstRate;
        private String unit;

        // Default constructor
        public PurchaseItemDto() {}

        // Parameterized constructor
        public PurchaseItemDto(String name, Integer qty, Double rate, String hsnCode, Double gstRate, String unit) {
            this.name = name;
            this.qty = qty;
            this.rate = rate;
            this.hsnCode = hsnCode;
            this.gstRate = gstRate;
            this.unit = unit;
        }

        // Getters and Setters
        public String getName() { return name; }
        public void setName(String name) { this.name = name; }

        public Integer getQty() { return qty; }
        public void setQty(Integer qty) { this.qty = qty; }

        public Double getRate() { return rate; }
        public void setRate(Double rate) { this.rate = rate; }

        public String getHsnCode() { return hsnCode; }
        public void setHsnCode(String hsnCode) { this.hsnCode = hsnCode; }

        public Double getGstRate() { return gstRate; }
        public void setGstRate(Double gstRate) { this.gstRate = gstRate; }

        public String getUnit() { return unit; }
        public void setUnit(String unit) { this.unit = unit; }
    }
}
