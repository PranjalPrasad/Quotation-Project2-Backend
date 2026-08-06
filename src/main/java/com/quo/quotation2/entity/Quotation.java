package com.quo.quotation2.entity;

import jakarta.persistence.*;

import java.time.LocalDate;
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

    @Column(name = "date")
    private LocalDate date;

    @Column(name = "valid_until")
    private LocalDateTime validUntil;

    @Column(name = "status")
    private String status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @Column(name = "is_inter_state")
    private Boolean isInterState;

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

    // Payment Terms - stored as fields on Quotation
    @Column(name = "advance_percent")
    private Double advancePercent;

    @Column(name = "material_percent")
    private Double materialPercent;

    @Column(name = "installation_percent")
    private Double installationPercent;

    @Column(name = "balance_percent")
    private Double balancePercent;

    // Bank Details
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

    // Plant Overview fields
    @Column(name = "plant_model")
    private String plantModel;

    @Column(name = "plant_production_capacity")
    private String plantProductionCapacity;

    @Column(name = "plant_bricks_size")
    private String plantBricksSize;

    @Column(name = "plant_pallet_size")
    private String plantPalletSize;

    @Column(name = "plant_shed_area")
    private String plantShedArea;

    @Column(name = "plant_total_land")
    private String plantTotalLand;

    @Column(name = "plant_connected_power")
    private String plantConnectedPower;

    @Column(name = "plant_labour_requirement")
    private String plantLabourRequirement;

    // Terms & Conditions
    @Column(name = "terms_template_version")
    private String termsTemplateVersion;

    @Column(name = "terms_categories_applied", columnDefinition = "JSON")
    private String termsCategoriesApplied;

    // Additional Notes
    @Column(name = "additional_notes", columnDefinition = "TEXT")
    private String additionalNotes;

    // Approval
    @Column(name = "approved_by")
    private String approvedBy;

    @Column(name = "approval_date")
    private LocalDate approvalDate;

    @Column(name = "approval_notes")
    private String approvalNotes;

    // History as JSON
    @Column(name = "history", columnDefinition = "JSON")
    private String history;

    // Metadata
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

    public Quotation() {
        this.status = "Pending";
        this.convertedToInvoice = false;
        this.isInterState = false;
        this.transportCharge = 0.0;
        this.loadingCharge = 0.0;
        this.otherCharge = 0.0;
        this.discountValue = 0.0;
        this.discountAmount = 0.0;
        this.itemsSubtotal = 0.0;
        this.gstPercent = 18.0;
        this.grandTotal = 0.0;
        this.taxableAmount = 0.0;
        this.cgstAmount = 0.0;
        this.sgstAmount = 0.0;
        this.igstAmount = 0.0;
        this.cgstPercent = 0.0;
        this.sgstPercent = 0.0;
        this.igstPercent = 0.0;
    }

    // ======== GETTERS AND SETTERS ========

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getQuoteNo() { return quoteNo; }
    public void setQuoteNo(String quoteNo) { this.quoteNo = quoteNo; }

    public LocalDateTime getQuoteDate() { return quoteDate; }
    public void setQuoteDate(LocalDateTime quoteDate) { this.quoteDate = quoteDate; }

    public LocalDate getDate() { return date; }
    public void setDate(LocalDate date) { this.date = date; }

    public LocalDateTime getValidUntil() { return validUntil; }
    public void setValidUntil(LocalDateTime validUntil) { this.validUntil = validUntil; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public Customer getCustomer() { return customer; }
    public void setCustomer(Customer customer) { this.customer = customer; }

    public Boolean getIsInterState() { return isInterState; }
    public void setIsInterState(Boolean isInterState) { this.isInterState = isInterState; }

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

    public Double getAdvancePercent() { return advancePercent; }
    public void setAdvancePercent(Double advancePercent) { this.advancePercent = advancePercent; }

    public Double getMaterialPercent() { return materialPercent; }
    public void setMaterialPercent(Double materialPercent) { this.materialPercent = materialPercent; }

    public Double getInstallationPercent() { return installationPercent; }
    public void setInstallationPercent(Double installationPercent) { this.installationPercent = installationPercent; }

    public Double getBalancePercent() { return balancePercent; }
    public void setBalancePercent(Double balancePercent) { this.balancePercent = balancePercent; }

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

    public String getPlantModel() { return plantModel; }
    public void setPlantModel(String plantModel) { this.plantModel = plantModel; }

    public String getPlantProductionCapacity() { return plantProductionCapacity; }
    public void setPlantProductionCapacity(String plantProductionCapacity) { this.plantProductionCapacity = plantProductionCapacity; }

    public String getPlantBricksSize() { return plantBricksSize; }
    public void setPlantBricksSize(String plantBricksSize) { this.plantBricksSize = plantBricksSize; }

    public String getPlantPalletSize() { return plantPalletSize; }
    public void setPlantPalletSize(String plantPalletSize) { this.plantPalletSize = plantPalletSize; }

    public String getPlantShedArea() { return plantShedArea; }
    public void setPlantShedArea(String plantShedArea) { this.plantShedArea = plantShedArea; }

    public String getPlantTotalLand() { return plantTotalLand; }
    public void setPlantTotalLand(String plantTotalLand) { this.plantTotalLand = plantTotalLand; }

    public String getPlantConnectedPower() { return plantConnectedPower; }
    public void setPlantConnectedPower(String plantConnectedPower) { this.plantConnectedPower = plantConnectedPower; }

    public String getPlantLabourRequirement() { return plantLabourRequirement; }
    public void setPlantLabourRequirement(String plantLabourRequirement) { this.plantLabourRequirement = plantLabourRequirement; }

    public String getTermsTemplateVersion() { return termsTemplateVersion; }
    public void setTermsTemplateVersion(String termsTemplateVersion) { this.termsTemplateVersion = termsTemplateVersion; }

    public String getTermsCategoriesApplied() { return termsCategoriesApplied; }
    public void setTermsCategoriesApplied(String termsCategoriesApplied) { this.termsCategoriesApplied = termsCategoriesApplied; }

    public String getAdditionalNotes() { return additionalNotes; }
    public void setAdditionalNotes(String additionalNotes) { this.additionalNotes = additionalNotes; }

    public String getApprovedBy() { return approvedBy; }
    public void setApprovedBy(String approvedBy) { this.approvedBy = approvedBy; }

    public LocalDate getApprovalDate() { return approvalDate; }
    public void setApprovalDate(LocalDate approvalDate) { this.approvalDate = approvalDate; }

    public String getApprovalNotes() { return approvalNotes; }
    public void setApprovalNotes(String approvalNotes) { this.approvalNotes = approvalNotes; }

    public String getHistory() { return history; }
    public void setHistory(String history) { this.history = history; }

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
}