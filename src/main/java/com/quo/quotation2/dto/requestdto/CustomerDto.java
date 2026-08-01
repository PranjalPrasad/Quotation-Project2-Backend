package com.quo.quotation2.dto;

public class CustomerDto {
    private Long id;
    private String customerCode;
    private String name;
    private String mobilePrimary;
    private String mobileSecondary;
    private String email;
    private String address;
    private String city;
    private String state;
    private String stateCode;
    private String pincode;
    private String gstin;

    public CustomerDto() {}

    public CustomerDto(Long id, String customerCode, String name, String mobilePrimary, String mobileSecondary,
                       String email, String address, String city, String state, String stateCode,
                       String pincode, String gstin) {
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
}