package com.quo.quotation2.dto.requestdto;


public class ConvertToInvoiceRequestDto {
    private String invoiceId;

    public ConvertToInvoiceRequestDto() {}

    public ConvertToInvoiceRequestDto(String invoiceId) {
        this.invoiceId = invoiceId;
    }

    public String getInvoiceId() { return invoiceId; }
    public void setInvoiceId(String invoiceId) { this.invoiceId = invoiceId; }
}
