package com.quo.quotation2.dto.requestdto;

public class BankDetailsDto {
    private String accountName;
    private String bankName;
    private String accountNumber;
    private String ifscCode;
    private String branch;

    public BankDetailsDto() {}

    public BankDetailsDto(String accountName, String bankName, String accountNumber, String ifscCode, String branch) {
        this.accountName = accountName;
        this.bankName = bankName;
        this.accountNumber = accountNumber;
        this.ifscCode = ifscCode;
        this.branch = branch;
    }

    // Getters and Setters
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