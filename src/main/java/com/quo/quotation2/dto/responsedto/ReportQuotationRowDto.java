package com.quo.quotation2.dto.responsedto;


public class ReportQuotationRowDto {

    private String no;
    private String customer;
    private String machine;
    private double amount;
    private String status;
    private String date; // yyyy-MM-dd

    public ReportQuotationRowDto() {}

    public ReportQuotationRowDto(String no, String customer, String machine, double amount, String status, String date) {
        this.no = no;
        this.customer = customer;
        this.machine = machine;
        this.amount = amount;
        this.status = status;
        this.date = date;
    }

    public String getNo() { return no; }
    public void setNo(String no) { this.no = no; }

    public String getCustomer() { return customer; }
    public void setCustomer(String customer) { this.customer = customer; }

    public String getMachine() { return machine; }
    public void setMachine(String machine) { this.machine = machine; }

    public double getAmount() { return amount; }
    public void setAmount(double amount) { this.amount = amount; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public String getDate() { return date; }
    public void setDate(String date) { this.date = date; }
}

