package com.quo.quotation2.dto.requestdto;

import java.time.LocalDateTime;

public class MetaDto {
    private String createdBy;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Boolean convertedToInvoice;
    private String invoiceId;

    public MetaDto() {}

    public MetaDto(String createdBy, LocalDateTime createdAt, LocalDateTime updatedAt,
                   Boolean convertedToInvoice, String invoiceId) {
        this.createdBy = createdBy;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.convertedToInvoice = convertedToInvoice;
        this.invoiceId = invoiceId;
    }

    // Getters and Setters
    public String getCreatedBy() { return createdBy; }
    public void setCreatedBy(String createdBy) { this.createdBy = createdBy; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }

    public Boolean getConvertedToInvoice() { return convertedToInvoice; }
    public void setConvertedToInvoice(Boolean convertedToInvoice) { this.convertedToInvoice = convertedToInvoice; }

    public String getInvoiceId() { return invoiceId; }
    public void setInvoiceId(String invoiceId) { this.invoiceId = invoiceId; }
}