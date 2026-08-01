package com.quo.quotation2.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "quotations")
public class Quotation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "quote_no", unique = true, nullable = false)
    private String quoteNo;

    @Column(name = "quote_date")
    private LocalDateTime quoteDate;

    @Column(name = "valid_until")
    private LocalDateTime validUntil;

    @Column(name = "status")
    private String status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @Column(name = "is_inter_state")
    private Boolean isInterState;

    @Column(name = "site_type")
    private String siteType;

    @Column(name = "delivery_timeline")
    private String deliveryTimeline;

    @Column(name = "items_subtotal")
    private Double itemsSubtotal;

    @Column(name = "total_power_hp")
    private Double totalPowerHP;

    @Column(name = "total_power_kw")
    private Double totalPowerKW;

    @Column(name = "transport_charge")
    private Double transportCharge;

    @Column(name = "loading_charge")
    private Double loadingCharge;

    @Column(name = "other_charge_label")
    private String otherChargeLabel;

    @Column(name = "other_charge")
    private Double otherCharge;

    @Column(name = "discount_type")
    private String discountType;

    @Column(name = "discount_value")
    private Double discountValue;

    @Column(name = "discount_amount")
    private Double discountAmount;

    @Column(name = "taxable_amount")
    private Double taxableAmount;

    @Column(name = "gst_percent")
    private Double gstPercent;

    @Column(name = "cgst_percent")
    private Double cgstPercent;

    @Column(name = "cgst_amount")
    private Double cgstAmount;

    @Column(name = "sgst_percent")
    private Double sgstPercent;

    @Column(name = "sgst_amount")
    private Double sgstAmount;

    @Column(name = "igst_percent")
    private Double igstPercent;

    @Column(name = "igst_amount")
    private Double igstAmount;

    @Column(name = "grand_total")
    private Double grandTotal;

    @Column(name = "grand_total_words")
    private String grandTotalWords;

    @Column(name = "payment_type")
    private String paymentType;

    @Column(name = "bank_account_name")
    private String bankAccountName;

    @Column(name = "bank_name")
    private String bankName;

    @Column(name = "bank_account_number")
    private String bankAccountNumber;

    @Column(name = "bank_ifsc")
    private String bankIfsc;

    @Column(name = "bank_branch")
    private String bankBranch;

    @Column(name = "terms_template_version")
    private String termsTemplateVersion;

    @Column(name = "terms_categories_applied", columnDefinition = "json")
    private String termsCategoriesApplied;

    @Column(name = "created_by")
    private String createdBy;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;

    @Column(name = "converted_to_invoice")
    private Boolean convertedToInvoice;

    @Column(name = "invoice_id")
    private String invoiceId;

    @OneToMany(mappedBy = "quotation", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<QuotationItem> items = new ArrayList<>();

    @OneToOne(mappedBy = "quotation", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private QuotationPaymentTerms paymentTerms;

    // Constructors
    public Quotation() {
        this.status = "Pending";
        this.convertedToInvoice = false;
    }

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

    public Customer getCustomer() { return customer; }
    public void setCustomer(Customer customer) { this.customer = customer; }

    public Boolean getIsInterState() { return isInterState; }
    public void setIsInterState(Boolean isInterState) { this.isInterState = isInterState; }

    public String getSiteType() { return siteType; }
    public void setSiteType(String siteType) { this.siteType = siteType; }

    public String getDeliveryTimeline() { return deliveryTimeline; }
    public void setDeliveryTimeline(String deliveryTimeline) { this.deliveryTimeline = deliveryTimeline; }

    public Double getItemsSubtotal() { return itemsSubtotal; }
    public void setItemsSubtotal(Double itemsSubtotal) { this.itemsSubtotal = itemsSubtotal; }

    public Double getTotalPowerHP() { return totalPowerHP; }
    public void setTotalPowerHP(Double totalPowerHP) { this.totalPowerHP = totalPowerHP; }

    public Double getTotalPowerKW() { return totalPowerKW; }
    public void setTotalPowerKW(Double totalPowerKW) { this.totalPowerKW = totalPowerKW; }

    public Double getTransportCharge() { return transportCharge; }
    public void setTransportCharge(Double transportCharge) { this.transportCharge = transportCharge; }

    public Double getLoadingCharge() { return loadingCharge; }
    public void setLoadingCharge(Double loadingCharge) { this.loadingCharge = loadingCharge; }

    public String getOtherChargeLabel() { return otherChargeLabel; }
    public void setOtherChargeLabel(String otherChargeLabel) { this.otherChargeLabel = otherChargeLabel; }

    public Double getOtherCharge() { return otherCharge; }
    public void setOtherCharge(Double otherCharge) { this.otherCharge = otherCharge; }

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

    public Double getGrandTotal() { return grandTotal; }
    public void setGrandTotal(Double grandTotal) { this.grandTotal = grandTotal; }

    public String getGrandTotalWords() { return grandTotalWords; }
    public void setGrandTotalWords(String grandTotalWords) { this.grandTotalWords = grandTotalWords; }

    public String getPaymentType() { return paymentType; }
    public void setPaymentType(String paymentType) { this.paymentType = paymentType; }

    public String getBankAccountName() { return bankAccountName; }
    public void setBankAccountName(String bankAccountName) { this.bankAccountName = bankAccountName; }

    public String getBankName() { return bankName; }
    public void setBankName(String bankName) { this.bankName = bankName; }

    public String getBankAccountNumber() { return bankAccountNumber; }
    public void setBankAccountNumber(String bankAccountNumber) { this.bankAccountNumber = bankAccountNumber; }

    public String getBankIfsc() { return bankIfsc; }
    public void setBankIfsc(String bankIfsc) { this.bankIfsc = bankIfsc; }

    public String getBankBranch() { return bankBranch; }
    public void setBankBranch(String bankBranch) { this.bankBranch = bankBranch; }

    public String getTermsTemplateVersion() { return termsTemplateVersion; }
    public void setTermsTemplateVersion(String termsTemplateVersion) { this.termsTemplateVersion = termsTemplateVersion; }

    public String getTermsCategoriesApplied() { return termsCategoriesApplied; }
    public void setTermsCategoriesApplied(String termsCategoriesApplied) { this.termsCategoriesApplied = termsCategoriesApplied; }

    public String getCreatedBy() { return createdBy; }
    public void setCreatedBy(String createdBy) { this.createdBy = createdBy; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }

    public LocalDateTime getDeletedAt() { return deletedAt; }
    public void setDeletedAt(LocalDateTime deletedAt) { this.deletedAt = deletedAt; }

    public Boolean getConvertedToInvoice() { return convertedToInvoice; }
    public void setConvertedToInvoice(Boolean convertedToInvoice) { this.convertedToInvoice = convertedToInvoice; }

    public String getInvoiceId() { return invoiceId; }
    public void setInvoiceId(String invoiceId) { this.invoiceId = invoiceId; }

    public List<QuotationItem> getItems() { return items; }
    public void setItems(List<QuotationItem> items) { this.items = items; }

    public QuotationPaymentTerms getPaymentTerms() { return paymentTerms; }
    public void setPaymentTerms(QuotationPaymentTerms paymentTerms) { this.paymentTerms = paymentTerms; }
}
