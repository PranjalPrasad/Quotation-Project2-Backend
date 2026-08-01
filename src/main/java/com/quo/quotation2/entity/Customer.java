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

    @Column(name = "mobile_primary", nullable = false, length = 10)
    private String mobilePrimary;

    @Column(name = "mobile_secondary", length = 10)
    private String mobileSecondary;

    @Column(name = "email")
    private String email;

    @Column(name = "address")
    private String address;

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

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;

    // Constructors
    public Customer() {}

    public Customer(Long id, String customerCode, String name, String mobilePrimary, String mobileSecondary,
                    String email, String address, String city, String state, String stateCode,
                    String pincode, String gstin, LocalDateTime createdAt, LocalDateTime updatedAt,
                    LocalDateTime deletedAt) {
        this.id = id;
        this.customerCode = customerCode;
        this.name = name;
        this.mobilePrimary = mobilePrimary;
        this.mobileSecondary = mobileSecondary;
        this.email = email;
        this.address = address;
        this.city = city;
        this.state = state;
        this.stateCode = stateCode;
        this.pincode = pincode;
        this.gstin = gstin;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.deletedAt = deletedAt;
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getCustomerCode() { return customerCode; }
    public void setCustomerCode(String customerCode) { this.customerCode = customerCode; }

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

    public String getStateCode() { return stateCode; }
    public void setStateCode(String stateCode) { this.stateCode = stateCode; }

    public String getPincode() { return pincode; }
    public void setPincode(String pincode) { this.pincode = pincode; }

    public String getGstin() { return gstin; }
    public void setGstin(String gstin) { this.gstin = gstin; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }

    public LocalDateTime getDeletedAt() { return deletedAt; }
    public void setDeletedAt(LocalDateTime deletedAt) { this.deletedAt = deletedAt; }
}
