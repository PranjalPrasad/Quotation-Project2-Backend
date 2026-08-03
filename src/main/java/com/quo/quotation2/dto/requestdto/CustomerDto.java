package com.quo.quotation2.dto;

public class CustomerDto {
    private Long id;
    private String customerCode;
    private String name;
    private String company;
    private String mobilePrimary;
    private String mobileSecondary;
    private String email;
    private String address;
    private String billingAddress;
    private String siteAddress;
    private String city;
    private String state;
    private String stateCode;
    private String pincode;
    private String gstin;
    private String type;
    private String leadSource;
    private String status;
    private String requirement;
    private String siteDetails;
    private String notes;
    private Integer totalOrders;
    private Double totalBusiness;
    private String lastActivity;
    private String created;

    public CustomerDto() {}

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getCustomerCode() { return customerCode; }
    public void setCustomerCode(String customerCode) { this.customerCode = customerCode; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getCompany() { return company; }
    public void setCompany(String company) { this.company = company; }

    public String getMobilePrimary() { return mobilePrimary; }
    public void setMobilePrimary(String mobilePrimary) { this.mobilePrimary = mobilePrimary; }

    public String getMobileSecondary() { return mobileSecondary; }
    public void setMobileSecondary(String mobileSecondary) { this.mobileSecondary = mobileSecondary; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }

    public String getBillingAddress() { return billingAddress; }
    public void setBillingAddress(String billingAddress) { this.billingAddress = billingAddress; }

    public String getSiteAddress() { return siteAddress; }
    public void setSiteAddress(String siteAddress) { this.siteAddress = siteAddress; }

    public String getCity() { return city; }
    public void setCity(String city) { this.city = city; }

    public String getState() { return state; }
    public void setState(String state) { this.state = state; }

    public String getStateCode() { return stateCode; }
    public void setStateCode(String stateCode) { this.stateCode = stateCode; }

    public String getPincode() { return pincode; }
    public void setPincode(String pincode) { this.pincode = pincode; }

    public String getGstin() { return gstin; }
    public void setGstin(String gstin) { this.gstin = gstin; }

    public String getType() { return type; }
    public void setType(String type) { this.type = type; }

    public String getLeadSource() { return leadSource; }
    public void setLeadSource(String leadSource) { this.leadSource = leadSource; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public String getRequirement() { return requirement; }
    public void setRequirement(String requirement) { this.requirement = requirement; }

    public String getSiteDetails() { return siteDetails; }
    public void setSiteDetails(String siteDetails) { this.siteDetails = siteDetails; }

    public String getNotes() { return notes; }
    public void setNotes(String notes) { this.notes = notes; }

    public Integer getTotalOrders() { return totalOrders; }
    public void setTotalOrders(Integer totalOrders) { this.totalOrders = totalOrders; }

    public Double getTotalBusiness() { return totalBusiness; }
    public void setTotalBusiness(Double totalBusiness) { this.totalBusiness = totalBusiness; }

    public String getLastActivity() { return lastActivity; }
    public void setLastActivity(String lastActivity) { this.lastActivity = lastActivity; }

    public String getCreated() { return created; }
    public void setCreated(String created) { this.created = created; }
}