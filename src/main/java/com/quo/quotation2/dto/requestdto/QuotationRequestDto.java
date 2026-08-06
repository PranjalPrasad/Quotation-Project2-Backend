package com.quo.quotation2.dto.requestdto;

import java.util.List;
import java.util.Map;

public class QuotationRequestDto {
    private String quoteNo;
    private String date;
    private String status;
    private CustomerDto customer;
    private List<ItemDto> items;
    private CostsDto costs;
    private Double gstPercent;
    private String discountType;
    private Double discountValue;
    private Double subtotal;
    private Double discountAmount;
    private Double taxable;
    private Double total;
    private Double amount;
    private Double itemsTotal;
    private String deliveryTimeline;
    private String validUntil;
    private PaymentTermsDto paymentTerms;
    private String paymentType;
    private BankDto bank;
    private Map<String, Object> termsAndConditions;
    private String additionalNotes;
    private PlantOverviewDto plantOverview;

    // Getters and Setters
    public String getQuoteNo() { return quoteNo; }
    public void setQuoteNo(String quoteNo) { this.quoteNo = quoteNo; }

    public String getDate() { return date; }
    public void setDate(String date) { this.date = date; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public CustomerDto getCustomer() { return customer; }
    public void setCustomer(CustomerDto customer) { this.customer = customer; }

    public List<ItemDto> getItems() { return items; }
    public void setItems(List<ItemDto> items) { this.items = items; }

    public CostsDto getCosts() { return costs; }
    public void setCosts(CostsDto costs) { this.costs = costs; }

    public Double getGstPercent() { return gstPercent; }
    public void setGstPercent(Double gstPercent) { this.gstPercent = gstPercent; }

    public String getDiscountType() { return discountType; }
    public void setDiscountType(String discountType) { this.discountType = discountType; }

    public Double getDiscountValue() { return discountValue; }
    public void setDiscountValue(Double discountValue) { this.discountValue = discountValue; }

    public Double getSubtotal() { return subtotal; }
    public void setSubtotal(Double subtotal) { this.subtotal = subtotal; }

    public Double getDiscountAmount() { return discountAmount; }
    public void setDiscountAmount(Double discountAmount) { this.discountAmount = discountAmount; }

    public Double getTaxable() { return taxable; }
    public void setTaxable(Double taxable) { this.taxable = taxable; }

    public Double getTotal() { return total; }
    public void setTotal(Double total) { this.total = total; }

    public Double getAmount() { return amount; }
    public void setAmount(Double amount) { this.amount = amount; }

    public Double getItemsTotal() { return itemsTotal; }
    public void setItemsTotal(Double itemsTotal) { this.itemsTotal = itemsTotal; }

    public String getDeliveryTimeline() { return deliveryTimeline; }
    public void setDeliveryTimeline(String deliveryTimeline) { this.deliveryTimeline = deliveryTimeline; }

    public String getValidUntil() { return validUntil; }
    public void setValidUntil(String validUntil) { this.validUntil = validUntil; }

    public PaymentTermsDto getPaymentTerms() { return paymentTerms; }
    public void setPaymentTerms(PaymentTermsDto paymentTerms) { this.paymentTerms = paymentTerms; }

    public String getPaymentType() { return paymentType; }
    public void setPaymentType(String paymentType) { this.paymentType = paymentType; }

    public BankDto getBank() { return bank; }
    public void setBank(BankDto bank) { this.bank = bank; }

    public Map<String, Object> getTermsAndConditions() { return termsAndConditions; }
    public void setTermsAndConditions(Map<String, Object> termsAndConditions) { this.termsAndConditions = termsAndConditions; }

    public String getAdditionalNotes() { return additionalNotes; }
    public void setAdditionalNotes(String additionalNotes) { this.additionalNotes = additionalNotes; }

    public PlantOverviewDto getPlantOverview() { return plantOverview; }
    public void setPlantOverview(PlantOverviewDto plantOverview) { this.plantOverview = plantOverview; }

    // ======== INNER CLASSES ========

    public static class CustomerDto {
        private Long customerId;
        private String name;
        private String mobilePrimary;
        private String mobileSecondary;
        private String email;
        private String address;
        private String city;
        private String state;
        private String pincode;
        private String gst;

        public Long getCustomerId() { return customerId; }
        public void setCustomerId(Long customerId) { this.customerId = customerId; }
        public String getName() { return name; }
        public void setName(String name) { this.name = name; }
        public String getMobilePrimary() { return mobilePrimary; }
        public void setMobilePrimary(String mobilePrimary) { this.mobilePrimary = mobilePrimary; }
        public String getMobileSecondary() { return mobileSecondary; }
        public void setMobileSecondary(String mobileSecondary) { this.mobileSecondary = mobileSecondary; }
        public String getEmail() { return email; }
        public void setEmail(String email) { this.email = email; }
        public String getAddress() { return address; }
        public void setAddress(String address) { this.address = address; }
        public String getCity() { return city; }
        public void setCity(String city) { this.city = city; }
        public String getState() { return state; }
        public void setState(String state) { this.state = state; }
        public String getPincode() { return pincode; }
        public void setPincode(String pincode) { this.pincode = pincode; }
        public String getGst() { return gst; }
        public void setGst(String gst) { this.gst = gst; }
    }

    public static class ItemDto {
        private String productId;
        private String name;
        private String category;
        private String sectionCode;
        private Double qty;
        private Double rate;
        private Double amount;
        private String hsnCode;
        private Double gstRate;
        private Double powerHP;
        private Double powerKW;
        private Boolean inCustomerScope;
        private String shedSize;
        private Integer labor;
        private String production;
        private String power;
        private String imageUrl;

        // Getters and Setters
        public String getProductId() { return productId; }
        public void setProductId(String productId) { this.productId = productId; }
        public String getName() { return name; }
        public void setName(String name) { this.name = name; }
        public String getCategory() { return category; }
        public void setCategory(String category) { this.category = category; }
        public String getSectionCode() { return sectionCode; }
        public void setSectionCode(String sectionCode) { this.sectionCode = sectionCode; }
        public Double getQty() { return qty; }
        public void setQty(Double qty) { this.qty = qty; }
        public Double getRate() { return rate; }
        public void setRate(Double rate) { this.rate = rate; }
        public Double getAmount() { return amount; }
        public void setAmount(Double amount) { this.amount = amount; }
        public String getHsnCode() { return hsnCode; }
        public void setHsnCode(String hsnCode) { this.hsnCode = hsnCode; }
        public Double getGstRate() { return gstRate; }
        public void setGstRate(Double gstRate) { this.gstRate = gstRate; }
        public Double getPowerHP() { return powerHP; }
        public void setPowerHP(Double powerHP) { this.powerHP = powerHP; }
        public Double getPowerKW() { return powerKW; }
        public void setPowerKW(Double powerKW) { this.powerKW = powerKW; }
        public Boolean getInCustomerScope() { return inCustomerScope; }
        public void setInCustomerScope(Boolean inCustomerScope) { this.inCustomerScope = inCustomerScope; }
        public String getShedSize() { return shedSize; }
        public void setShedSize(String shedSize) { this.shedSize = shedSize; }
        public Integer getLabor() { return labor; }
        public void setLabor(Integer labor) { this.labor = labor; }
        public String getProduction() { return production; }
        public void setProduction(String production) { this.production = production; }
        public String getPower() { return power; }
        public void setPower(String power) { this.power = power; }
        public String getImageUrl() { return imageUrl; }
        public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }
    }

    public static class CostsDto {
        private Double transport;
        private Double loading;
        private String otherLabel;
        private Double other;

        public Double getTransport() { return transport; }
        public void setTransport(Double transport) { this.transport = transport; }
        public Double getLoading() { return loading; }
        public void setLoading(Double loading) { this.loading = loading; }
        public String getOtherLabel() { return otherLabel; }
        public void setOtherLabel(String otherLabel) { this.otherLabel = otherLabel; }
        public Double getOther() { return other; }
        public void setOther(Double other) { this.other = other; }
    }

    public static class PaymentTermsDto {
        private Double advance;
        private Double material;
        private Double installation;
        private Double balance;

        public Double getAdvance() { return advance; }
        public void setAdvance(Double advance) { this.advance = advance; }
        public Double getMaterial() { return material; }
        public void setMaterial(Double material) { this.material = material; }
        public Double getInstallation() { return installation; }
        public void setInstallation(Double installation) { this.installation = installation; }
        public Double getBalance() { return balance; }
        public void setBalance(Double balance) { this.balance = balance; }
    }

    public static class BankDto {
        private String accountName;
        private String bankName;
        private String accountNumber;
        private String ifscCode;
        private String branch;

        public String getAccountName() { return accountName; }
        public void setAccountName(String accountName) { this.accountName = accountName; }
        public String getBankName() { return bankName; }
        public void setBankName(String bankName) { this.bankName = bankName; }
        public String getAccountNumber() { return accountNumber; }
        public void setAccountNumber(String accountNumber) { this.accountNumber = accountNumber; }
        public String getIfscCode() { return ifscCode; }
        public void setIfscCode(String ifscCode) { this.ifscCode = ifscCode; }
        public String getBranch() { return branch; }
        public void setBranch(String branch) { this.branch = branch; }
    }

    public static class PlantOverviewDto {
        private String model;
        private String productionCapacity;
        private String bricksSize;
        private String palletSize;
        private String requiredShedArea;
        private String totalLand;
        private String connectedPower;
        private String labourRequirement;

        public String getModel() { return model; }
        public void setModel(String model) { this.model = model; }
        public String getProductionCapacity() { return productionCapacity; }
        public void setProductionCapacity(String productionCapacity) { this.productionCapacity = productionCapacity; }
        public String getBricksSize() { return bricksSize; }
        public void setBricksSize(String bricksSize) { this.bricksSize = bricksSize; }
        public String getPalletSize() { return palletSize; }
        public void setPalletSize(String palletSize) { this.palletSize = palletSize; }
        public String getRequiredShedArea() { return requiredShedArea; }
        public void setRequiredShedArea(String requiredShedArea) { this.requiredShedArea = requiredShedArea; }
        public String getTotalLand() { return totalLand; }
        public void setTotalLand(String totalLand) { this.totalLand = totalLand; }
        public String getConnectedPower() { return connectedPower; }
        public void setConnectedPower(String connectedPower) { this.connectedPower = connectedPower; }
        public String getLabourRequirement() { return labourRequirement; }
        public void setLabourRequirement(String labourRequirement) { this.labourRequirement = labourRequirement; }
    }
}