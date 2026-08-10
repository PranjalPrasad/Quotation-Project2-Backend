package com.quo.quotation2.dto.responsedto;

import java.time.LocalDate;
import java.util.List;

public class Gstr1BResponseDto {

    private String period;
    private String gstin;
    private Summary summary;
    private List<B2BInvoiceDto> b2bInvoices;
    private List<B2CInvoiceDto> b2cInvoices;
    private List<HsnSummaryDto> hsnSummary;
    private List<ExportInvoiceDto> exportInvoices;

    // Default constructor
    public Gstr1BResponseDto() {}

    // Parameterized constructor
    public Gstr1BResponseDto(String period, String gstin, Summary summary,
                             List<B2BInvoiceDto> b2bInvoices, List<B2CInvoiceDto> b2cInvoices,
                             List<HsnSummaryDto> hsnSummary, List<ExportInvoiceDto> exportInvoices) {
        this.period = period;
        this.gstin = gstin;
        this.summary = summary;
        this.b2bInvoices = b2bInvoices;
        this.b2cInvoices = b2cInvoices;
        this.hsnSummary = hsnSummary;
        this.exportInvoices = exportInvoices;
    }

    // Getters and Setters
    public String getPeriod() { return period; }
    public void setPeriod(String period) { this.period = period; }

    public String getGstin() { return gstin; }
    public void setGstin(String gstin) { this.gstin = gstin; }

    public Summary getSummary() { return summary; }
    public void setSummary(Summary summary) { this.summary = summary; }

    public List<B2BInvoiceDto> getB2bInvoices() { return b2bInvoices; }
    public void setB2bInvoices(List<B2BInvoiceDto> b2bInvoices) { this.b2bInvoices = b2bInvoices; }

    public List<B2CInvoiceDto> getB2cInvoices() { return b2cInvoices; }
    public void setB2cInvoices(List<B2CInvoiceDto> b2cInvoices) { this.b2cInvoices = b2cInvoices; }

    public List<HsnSummaryDto> getHsnSummary() { return hsnSummary; }
    public void setHsnSummary(List<HsnSummaryDto> hsnSummary) { this.hsnSummary = hsnSummary; }

    public List<ExportInvoiceDto> getExportInvoices() { return exportInvoices; }
    public void setExportInvoices(List<ExportInvoiceDto> exportInvoices) { this.exportInvoices = exportInvoices; }

    // ============================================================
    // Inner Classes
    // ============================================================

    public static class Summary {
        private Double totalSales;
        private Double totalB2BSales;
        private Double totalB2CSales;
        private Double totalTax;
        private Integer totalInvoices;

        public Summary() {}

        public Summary(Double totalSales, Double totalB2BSales, Double totalB2CSales,
                       Double totalTax, Integer totalInvoices) {
            this.totalSales = totalSales;
            this.totalB2BSales = totalB2BSales;
            this.totalB2CSales = totalB2CSales;
            this.totalTax = totalTax;
            this.totalInvoices = totalInvoices;
        }

        public Double getTotalSales() { return totalSales; }
        public void setTotalSales(Double totalSales) { this.totalSales = totalSales; }

        public Double getTotalB2BSales() { return totalB2BSales; }
        public void setTotalB2BSales(Double totalB2BSales) { this.totalB2BSales = totalB2BSales; }

        public Double getTotalB2CSales() { return totalB2CSales; }
        public void setTotalB2CSales(Double totalB2CSales) { this.totalB2CSales = totalB2CSales; }

        public Double getTotalTax() { return totalTax; }
        public void setTotalTax(Double totalTax) { this.totalTax = totalTax; }

        public Integer getTotalInvoices() { return totalInvoices; }
        public void setTotalInvoices(Integer totalInvoices) { this.totalInvoices = totalInvoices; }
    }

    public static class B2BInvoiceDto {
        private String invoiceNo;
        private LocalDate invoiceDate;
        private String customerName;
        private String customerGstin;
        private Double taxableValue;
        private Double cgst;
        private Double sgst;
        private Double igst;
        private Double total;

        public B2BInvoiceDto() {}

        public B2BInvoiceDto(String invoiceNo, LocalDate invoiceDate, String customerName, String customerGstin,
                             Double taxableValue, Double cgst, Double sgst, Double igst, Double total) {
            this.invoiceNo = invoiceNo;
            this.invoiceDate = invoiceDate;
            this.customerName = customerName;
            this.customerGstin = customerGstin;
            this.taxableValue = taxableValue;
            this.cgst = cgst;
            this.sgst = sgst;
            this.igst = igst;
            this.total = total;
        }

        public String getInvoiceNo() { return invoiceNo; }
        public void setInvoiceNo(String invoiceNo) { this.invoiceNo = invoiceNo; }

        public LocalDate getInvoiceDate() { return invoiceDate; }
        public void setInvoiceDate(LocalDate invoiceDate) { this.invoiceDate = invoiceDate; }

        public String getCustomerName() { return customerName; }
        public void setCustomerName(String customerName) { this.customerName = customerName; }

        public String getCustomerGstin() { return customerGstin; }
        public void setCustomerGstin(String customerGstin) { this.customerGstin = customerGstin; }

        public Double getTaxableValue() { return taxableValue; }
        public void setTaxableValue(Double taxableValue) { this.taxableValue = taxableValue; }

        public Double getCgst() { return cgst; }
        public void setCgst(Double cgst) { this.cgst = cgst; }

        public Double getSgst() { return sgst; }
        public void setSgst(Double sgst) { this.sgst = sgst; }

        public Double getIgst() { return igst; }
        public void setIgst(Double igst) { this.igst = igst; }

        public Double getTotal() { return total; }
        public void setTotal(Double total) { this.total = total; }
    }

    public static class B2CInvoiceDto {
        private String invoiceNo;
        private LocalDate invoiceDate;
        private String stateCode;
        private Double taxableValue;
        private Double cgst;
        private Double sgst;
        private Double igst;
        private Double total;

        public B2CInvoiceDto() {}

        public B2CInvoiceDto(String invoiceNo, LocalDate invoiceDate, String stateCode,
                             Double taxableValue, Double cgst, Double sgst, Double igst, Double total) {
            this.invoiceNo = invoiceNo;
            this.invoiceDate = invoiceDate;
            this.stateCode = stateCode;
            this.taxableValue = taxableValue;
            this.cgst = cgst;
            this.sgst = sgst;
            this.igst = igst;
            this.total = total;
        }

        public String getInvoiceNo() { return invoiceNo; }
        public void setInvoiceNo(String invoiceNo) { this.invoiceNo = invoiceNo; }

        public LocalDate getInvoiceDate() { return invoiceDate; }
        public void setInvoiceDate(LocalDate invoiceDate) { this.invoiceDate = invoiceDate; }

        public String getStateCode() { return stateCode; }
        public void setStateCode(String stateCode) { this.stateCode = stateCode; }

        public Double getTaxableValue() { return taxableValue; }
        public void setTaxableValue(Double taxableValue) { this.taxableValue = taxableValue; }

        public Double getCgst() { return cgst; }
        public void setCgst(Double cgst) { this.cgst = cgst; }

        public Double getSgst() { return sgst; }
        public void setSgst(Double sgst) { this.sgst = sgst; }

        public Double getIgst() { return igst; }
        public void setIgst(Double igst) { this.igst = igst; }

        public Double getTotal() { return total; }
        public void setTotal(Double total) { this.total = total; }
    }

    public static class HsnSummaryDto {
        private String hsnCode;
        private String description;
        private Double quantity;
        private Double totalValue;
        private Double taxableValue;
        private Double cgst;
        private Double sgst;
        private Double igst;

        public HsnSummaryDto() {}

        public HsnSummaryDto(String hsnCode, String description, Double quantity, Double totalValue,
                             Double taxableValue, Double cgst, Double sgst, Double igst) {
            this.hsnCode = hsnCode;
            this.description = description;
            this.quantity = quantity;
            this.totalValue = totalValue;
            this.taxableValue = taxableValue;
            this.cgst = cgst;
            this.sgst = sgst;
            this.igst = igst;
        }

        public String getHsnCode() { return hsnCode; }
        public void setHsnCode(String hsnCode) { this.hsnCode = hsnCode; }

        public String getDescription() { return description; }
        public void setDescription(String description) { this.description = description; }

        public Double getQuantity() { return quantity; }
        public void setQuantity(Double quantity) { this.quantity = quantity; }

        public Double getTotalValue() { return totalValue; }
        public void setTotalValue(Double totalValue) { this.totalValue = totalValue; }

        public Double getTaxableValue() { return taxableValue; }
        public void setTaxableValue(Double taxableValue) { this.taxableValue = taxableValue; }

        public Double getCgst() { return cgst; }
        public void setCgst(Double cgst) { this.cgst = cgst; }

        public Double getSgst() { return sgst; }
        public void setSgst(Double sgst) { this.sgst = sgst; }

        public Double getIgst() { return igst; }
        public void setIgst(Double igst) { this.igst = igst; }
    }

    public static class ExportInvoiceDto {
        private String invoiceNo;
        private LocalDate invoiceDate;
        private String customerName;
        private String shippingBillNo;
        private LocalDate shippingBillDate;
        private String portCode;
        private Double totalValue;
        private Double taxableValue;
        private Double igst;

        public ExportInvoiceDto() {}

        public ExportInvoiceDto(String invoiceNo, LocalDate invoiceDate, String customerName,
                                String shippingBillNo, LocalDate shippingBillDate, String portCode,
                                Double totalValue, Double taxableValue, Double igst) {
            this.invoiceNo = invoiceNo;
            this.invoiceDate = invoiceDate;
            this.customerName = customerName;
            this.shippingBillNo = shippingBillNo;
            this.shippingBillDate = shippingBillDate;
            this.portCode = portCode;
            this.totalValue = totalValue;
            this.taxableValue = taxableValue;
            this.igst = igst;
        }

        public String getInvoiceNo() { return invoiceNo; }
        public void setInvoiceNo(String invoiceNo) { this.invoiceNo = invoiceNo; }

        public LocalDate getInvoiceDate() { return invoiceDate; }
        public void setInvoiceDate(LocalDate invoiceDate) { this.invoiceDate = invoiceDate; }

        public String getCustomerName() { return customerName; }
        public void setCustomerName(String customerName) { this.customerName = customerName; }

        public String getShippingBillNo() { return shippingBillNo; }
        public void setShippingBillNo(String shippingBillNo) { this.shippingBillNo = shippingBillNo; }

        public LocalDate getShippingBillDate() { return shippingBillDate; }
        public void setShippingBillDate(LocalDate shippingBillDate) { this.shippingBillDate = shippingBillDate; }

        public String getPortCode() { return portCode; }
        public void setPortCode(String portCode) { this.portCode = portCode; }

        public Double getTotalValue() { return totalValue; }
        public void setTotalValue(Double totalValue) { this.totalValue = totalValue; }

        public Double getTaxableValue() { return taxableValue; }
        public void setTaxableValue(Double taxableValue) { this.taxableValue = taxableValue; }

        public Double getIgst() { return igst; }
        public void setIgst(Double igst) { this.igst = igst; }
    }
}
