package com.quo.quotation2.dto.requestdto;

public class ConvertToInvoiceRequestDto {
    private String invoiceId;
    private String notes;

    public ConvertToInvoiceRequestDto() {}

    public ConvertToInvoiceRequestDto(String invoiceId) {
        this.invoiceId = invoiceId;
    }

    public ConvertToInvoiceRequestDto(String invoiceId, String notes) {
        this.invoiceId = invoiceId;
        this.notes = notes;
    }

    public String getInvoiceId() { return invoiceId; }
    public void setInvoiceId(String invoiceId) { this.invoiceId = invoiceId; }

    public String getNotes() { return notes; }
    public void setNotes(String notes) { this.notes = notes; }
}