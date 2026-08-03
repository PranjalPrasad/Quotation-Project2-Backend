package com.quo.quotation2.dto.requestdto;

public class ConvertToInvoiceRequestDto {
    private String invoiceNo;
    private String notes;

    public String getInvoiceNo() { return invoiceNo; }
    public void setInvoiceNo(String invoiceNo) { this.invoiceNo = invoiceNo; }

    public String getNotes() { return notes; }
    public void setNotes(String notes) { this.notes = notes; }
}