package com.quo.quotation2.dto.responsedto;

import java.time.LocalDateTime;

public class QuotationListResponseDto {
    private Long id;
    private String quoteNo;
    private LocalDateTime quoteDate;
    private LocalDateTime validUntil;
    private String status;
    private String customerName;
    private String customerMobile;
    private Double grandTotal;
    private Boolean convertedToInvoice;

    public QuotationListResponseDto() {}

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getQuoteNo() { return quoteNo; }
    public void setQuoteNo(String quoteNo) { this.quoteNo = quoteNo; }

    public LocalDateTime getQuoteDate() { return quoteDate; }
    public void setQuoteDate(LocalDateTime quoteDate) { this.quoteDate = quoteDate; }

    public LocalDateTime getValidUntil() { return validUntil; }
    public void setValidUntil(LocalDateTime validUntil) { this.validUntil = validUntil; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public String getCustomerName() { return customerName; }
    public void setCustomerName(String customerName) { this.customerName = customerName; }

    public String getCustomerMobile() { return customerMobile; }
    public void setCustomerMobile(String customerMobile) { this.customerMobile = customerMobile; }

    public Double getGrandTotal() { return grandTotal; }
    public void setGrandTotal(Double grandTotal) { this.grandTotal = grandTotal; }

    public Boolean getConvertedToInvoice() { return convertedToInvoice; }
    public void setConvertedToInvoice(Boolean convertedToInvoice) { this.convertedToInvoice = convertedToInvoice; }
}