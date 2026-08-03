package com.quo.quotation2.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "customers")
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "customer_code", unique = true)
    private String customerCode;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "company")
    private String company;

    @Column(name = "mobile_primary", nullable = false, length = 10)
    private String mobilePrimary;

    @Column(name = "mobile_secondary", length = 10)
    private String mobileSecondary;

    @Column(name = "email")
    private String email;

    @Column(name = "address")
    private String address;

    @Column(name = "billing_address")
    private String billingAddress;

    @Column(name = "site_address")
    private String siteAddress;

    @Column(name = "city")
    private String city;

    @Column(name = "state")
    private String state;

    @Column(name = "state_code", length = 2)
    private String stateCode;

    @Column(name = "pincode", length = 6)
    private String pincode;

    @Column(name = "gstin", length = 15)
    private String gstin;

    @Column(name = "type")
    private String type;

    @Column(name = "lead_source")
    private String leadSource;

    @Column(name = "status")
    private String status;

    @Column(name = "requirement")
    private String requirement;

    @Column(name = "site_details")
    private String siteDetails;

    @Column(name = "notes")
    private String notes;

    @Column(name = "total_orders")
    private Integer totalOrders = 0;

    @Column(name = "total_business")
    private Double totalBusiness = 0.0;

    @Column(name = "last_activity")
    private String lastActivity;

    @Column(name = "created")
    private String created;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;

    public Customer() {}

    // Getters and Setters (ALL of them)
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

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }

    public LocalDateTime getDeletedAt() { return deletedAt; }
    public void setDeletedAt(LocalDateTime deletedAt) { this.deletedAt = deletedAt; }
}