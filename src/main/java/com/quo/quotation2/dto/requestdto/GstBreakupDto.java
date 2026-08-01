package com.quo.quotation2.dto.requestdto;

public class GstBreakupDto {
    private Double cgstPercent;
    private Double cgstAmount;
    private Double sgstPercent;
    private Double sgstAmount;
    private Double igstPercent;
    private Double igstAmount;

    public GstBreakupDto() {}

    public GstBreakupDto(Double cgstPercent, Double cgstAmount, Double sgstPercent, Double sgstAmount,
                         Double igstPercent, Double igstAmount) {
        this.cgstPercent = cgstPercent;
        this.cgstAmount = cgstAmount;
        this.sgstPercent = sgstPercent;
        this.sgstAmount = sgstAmount;
        this.igstPercent = igstPercent;
        this.igstAmount = igstAmount;
    }

    // Getters and Setters
    public Double getCgstPercent() { return cgstPercent; }
    public void setCgstPercent(Double cgstPercent) { this.cgstPercent = cgstPercent; }

    public Double getCgstAmount() { return cgstAmount; }
    public void setCgstAmount(Double cgstAmount) { this.cgstAmount = cgstAmount; }

    public Double getSgstPercent() { return sgstPercent; }
    public void setSgstPercent(Double sgstPercent) { this.sgstPercent = sgstPercent; }

    public Double getSgstAmount() { return sgstAmount; }
    public void setSgstAmount(Double sgstAmount) { this.sgstAmount = sgstAmount; }

    public Double getIgstPercent() { return igstPercent; }
    public void setIgstPercent(Double igstPercent) { this.igstPercent = igstPercent; }

    public Double getIgstAmount() { return igstAmount; }
    public void setIgstAmount(Double igstAmount) { this.igstAmount = igstAmount; }
}