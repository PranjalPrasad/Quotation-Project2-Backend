package com.quo.quotation2.dto.requestdto;

import com.quo.quotation2.dto.CustomerDto;

import java.util.List;

public class QuotationRequestDto {
    private String quoteNo;
    private String quoteDate;
    private String validUntil;
    private String status;
    private CustomerDto customer;
    private Boolean isInterState;
    private String siteType;
    private String deliveryTimeline;
    private List<QuotationItemDto> items;
    private Double itemsSubtotal;
    private Double totalPowerHP;
    private Double totalPowerKW;
    private AdditionalChargesDto additionalCharges;
    private String discountType;
    private Double discountValue;
    private Double discountAmount;
    private Double taxableAmount;
    private Double gstPercent;
    private GstBreakupDto gstBreakup;
    private Double grandTotal;
    private String grandTotalWords;
    private String paymentType;
    private PaymentTermsDto paymentTerms;
    private BankDetailsDto bankDetails;
    private TermsAndConditionsDto termsAndConditions;
    private List<ProductImageDto> productImages;
    private MetaDto meta;

    public static class AdditionalChargesDto {
        private Double transport;
        private Double loading;
        private String otherLabel;
        private Double other;

        public AdditionalChargesDto() {}

        public Double getTransport() { return transport; }
        public void setTransport(Double transport) { this.transport = transport; }

        public Double getLoading() { return loading; }
        public void setLoading(Double loading) { this.loading = loading; }

        public String getOtherLabel() { return otherLabel; }
        public void setOtherLabel(String otherLabel) { this.otherLabel = otherLabel; }

        public Double getOther() { return other; }
        public void setOther(Double other) { this.other = other; }
    }

    // Getters and Setters
    public String getQuoteNo() { return quoteNo; }
    public void setQuoteNo(String quoteNo) { this.quoteNo = quoteNo; }

    public String getQuoteDate() { return quoteDate; }
    public void setQuoteDate(String quoteDate) { this.quoteDate = quoteDate; }

    public String getValidUntil() { return validUntil; }
    public void setValidUntil(String validUntil) { this.validUntil = validUntil; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public CustomerDto getCustomer() { return customer; }
    public void setCustomer(CustomerDto customer) { this.customer = customer; }

    public Boolean getIsInterState() { return isInterState; }
    public void setIsInterState(Boolean isInterState) { this.isInterState = isInterState; }

    public String getSiteType() { return siteType; }
    public void setSiteType(String siteType) { this.siteType = siteType; }

    public String getDeliveryTimeline() { return deliveryTimeline; }
    public void setDeliveryTimeline(String deliveryTimeline) { this.deliveryTimeline = deliveryTimeline; }

    public List<QuotationItemDto> getItems() { return items; }
    public void setItems(List<QuotationItemDto> items) { this.items = items; }

    public Double getItemsSubtotal() { return itemsSubtotal; }
    public void setItemsSubtotal(Double itemsSubtotal) { this.itemsSubtotal = itemsSubtotal; }

    public Double getTotalPowerHP() { return totalPowerHP; }
    public void setTotalPowerHP(Double totalPowerHP) { this.totalPowerHP = totalPowerHP; }

    public Double getTotalPowerKW() { return totalPowerKW; }
    public void setTotalPowerKW(Double totalPowerKW) { this.totalPowerKW = totalPowerKW; }

    public AdditionalChargesDto getAdditionalCharges() { return additionalCharges; }
    public void setAdditionalCharges(AdditionalChargesDto additionalCharges) { this.additionalCharges = additionalCharges; }

    public String getDiscountType() { return discountType; }
    public void setDiscountType(String discountType) { this.discountType = discountType; }

    public Double getDiscountValue() { return discountValue; }
    public void setDiscountValue(Double discountValue) { this.discountValue = discountValue; }

    public Double getDiscountAmount() { return discountAmount; }
    public void setDiscountAmount(Double discountAmount) { this.discountAmount = discountAmount; }

    public Double getTaxableAmount() { return taxableAmount; }
    public void setTaxableAmount(Double taxableAmount) { this.taxableAmount = taxableAmount; }

    public Double getGstPercent() { return gstPercent; }
    public void setGstPercent(Double gstPercent) { this.gstPercent = gstPercent; }

    public GstBreakupDto getGstBreakup() { return gstBreakup; }
    public void setGstBreakup(GstBreakupDto gstBreakup) { this.gstBreakup = gstBreakup; }

    public Double getGrandTotal() { return grandTotal; }
    public void setGrandTotal(Double grandTotal) { this.grandTotal = grandTotal; }

    public String getGrandTotalWords() { return grandTotalWords; }
    public void setGrandTotalWords(String grandTotalWords) { this.grandTotalWords = grandTotalWords; }

    public String getPaymentType() { return paymentType; }
    public void setPaymentType(String paymentType) { this.paymentType = paymentType; }

    public PaymentTermsDto getPaymentTerms() { return paymentTerms; }
    public void setPaymentTerms(PaymentTermsDto paymentTerms) { this.paymentTerms = paymentTerms; }

    public BankDetailsDto getBankDetails() { return bankDetails; }
    public void setBankDetails(BankDetailsDto bankDetails) { this.bankDetails = bankDetails; }

    public TermsAndConditionsDto getTermsAndConditions() { return termsAndConditions; }
    public void setTermsAndConditions(TermsAndConditionsDto termsAndConditions) { this.termsAndConditions = termsAndConditions; }

    public List<ProductImageDto> getProductImages() { return productImages; }
    public void setProductImages(List<ProductImageDto> productImages) { this.productImages = productImages; }

    public MetaDto getMeta() { return meta; }
    public void setMeta(MetaDto meta) { this.meta = meta; }
}