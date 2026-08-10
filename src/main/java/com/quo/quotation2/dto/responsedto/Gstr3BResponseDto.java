package com.quo.quotation2.dto.responsedto;

public class Gstr3BResponseDto {

    private String period;
    private String gstin;
    private String returnType; // Regular, Composition, etc.
    private OutwardSupplyDto outwardSupplies;
    private InwardSupplyDto inwardSupplies;
    private ItcClaimDto itcClaimed;
    private TaxLiabilityDto netTaxLiability;
    private PaymentDto paymentDetails;
    private LateFeeDto lateFee;

    // Default constructor
    public Gstr3BResponseDto() {}

    // Parameterized constructor
    public Gstr3BResponseDto(String period, String gstin, String returnType,
                             OutwardSupplyDto outwardSupplies, InwardSupplyDto inwardSupplies,
                             ItcClaimDto itcClaimed, TaxLiabilityDto netTaxLiability,
                             PaymentDto paymentDetails, LateFeeDto lateFee) {
        this.period = period;
        this.gstin = gstin;
        this.returnType = returnType;
        this.outwardSupplies = outwardSupplies;
        this.inwardSupplies = inwardSupplies;
        this.itcClaimed = itcClaimed;
        this.netTaxLiability = netTaxLiability;
        this.paymentDetails = paymentDetails;
        this.lateFee = lateFee;
    }

    // Getters and Setters
    public String getPeriod() { return period; }
    public void setPeriod(String period) { this.period = period; }

    public String getGstin() { return gstin; }
    public void setGstin(String gstin) { this.gstin = gstin; }

    public String getReturnType() { return returnType; }
    public void setReturnType(String returnType) { this.returnType = returnType; }

    public OutwardSupplyDto getOutwardSupplies() { return outwardSupplies; }
    public void setOutwardSupplies(OutwardSupplyDto outwardSupplies) { this.outwardSupplies = outwardSupplies; }

    public InwardSupplyDto getInwardSupplies() { return inwardSupplies; }
    public void setInwardSupplies(InwardSupplyDto inwardSupplies) { this.inwardSupplies = inwardSupplies; }

    public ItcClaimDto getItcClaimed() { return itcClaimed; }
    public void setItcClaimed(ItcClaimDto itcClaimed) { this.itcClaimed = itcClaimed; }

    public TaxLiabilityDto getNetTaxLiability() { return netTaxLiability; }
    public void setNetTaxLiability(TaxLiabilityDto netTaxLiability) { this.netTaxLiability = netTaxLiability; }

    public PaymentDto getPaymentDetails() { return paymentDetails; }
    public void setPaymentDetails(PaymentDto paymentDetails) { this.paymentDetails = paymentDetails; }

    public LateFeeDto getLateFee() { return lateFee; }
    public void setLateFee(LateFeeDto lateFee) { this.lateFee = lateFee; }

    // ============================================================
    // Inner Classes
    // ============================================================

    public static class OutwardSupplyDto {
        private Double totalTaxableValue;
        private Double totalCgst;
        private Double totalSgst;
        private Double totalIgst;
        private Double totalCess;
        private Integer totalInvoices;

        public OutwardSupplyDto() {}

        public OutwardSupplyDto(Double totalTaxableValue, Double totalCgst, Double totalSgst,
                                Double totalIgst, Double totalCess, Integer totalInvoices) {
            this.totalTaxableValue = totalTaxableValue;
            this.totalCgst = totalCgst;
            this.totalSgst = totalSgst;
            this.totalIgst = totalIgst;
            this.totalCess = totalCess;
            this.totalInvoices = totalInvoices;
        }

        public Double getTotalTaxableValue() { return totalTaxableValue; }
        public void setTotalTaxableValue(Double totalTaxableValue) { this.totalTaxableValue = totalTaxableValue; }

        public Double getTotalCgst() { return totalCgst; }
        public void setTotalCgst(Double totalCgst) { this.totalCgst = totalCgst; }

        public Double getTotalSgst() { return totalSgst; }
        public void setTotalSgst(Double totalSgst) { this.totalSgst = totalSgst; }

        public Double getTotalIgst() { return totalIgst; }
        public void setTotalIgst(Double totalIgst) { this.totalIgst = totalIgst; }

        public Double getTotalCess() { return totalCess; }
        public void setTotalCess(Double totalCess) { this.totalCess = totalCess; }

        public Integer getTotalInvoices() { return totalInvoices; }
        public void setTotalInvoices(Integer totalInvoices) { this.totalInvoices = totalInvoices; }
    }

    public static class InwardSupplyDto {
        private Double totalTaxableValue;
        private Double totalCgst;
        private Double totalSgst;
        private Double totalIgst;
        private Double totalCess;
        private Integer totalInvoices;

        public InwardSupplyDto() {}

        public InwardSupplyDto(Double totalTaxableValue, Double totalCgst, Double totalSgst,
                               Double totalIgst, Double totalCess, Integer totalInvoices) {
            this.totalTaxableValue = totalTaxableValue;
            this.totalCgst = totalCgst;
            this.totalSgst = totalSgst;
            this.totalIgst = totalIgst;
            this.totalCess = totalCess;
            this.totalInvoices = totalInvoices;
        }

        public Double getTotalTaxableValue() { return totalTaxableValue; }
        public void setTotalTaxableValue(Double totalTaxableValue) { this.totalTaxableValue = totalTaxableValue; }

        public Double getTotalCgst() { return totalCgst; }
        public void setTotalCgst(Double totalCgst) { this.totalCgst = totalCgst; }

        public Double getTotalSgst() { return totalSgst; }
        public void setTotalSgst(Double totalSgst) { this.totalSgst = totalSgst; }

        public Double getTotalIgst() { return totalIgst; }
        public void setTotalIgst(Double totalIgst) { this.totalIgst = totalIgst; }

        public Double getTotalCess() { return totalCess; }
        public void setTotalCess(Double totalCess) { this.totalCess = totalCess; }

        public Integer getTotalInvoices() { return totalInvoices; }
        public void setTotalInvoices(Integer totalInvoices) { this.totalInvoices = totalInvoices; }
    }

    public static class ItcClaimDto {
        private Double totalItcClaimed;
        private Double itcCgst;
        private Double itcSgst;
        private Double itcIgst;
        private Double itcCess;
        private Double itcOnImports;
        private Double itcOnDomestic;

        public ItcClaimDto() {}

        public ItcClaimDto(Double totalItcClaimed, Double itcCgst, Double itcSgst,
                           Double itcIgst, Double itcCess, Double itcOnImports, Double itcOnDomestic) {
            this.totalItcClaimed = totalItcClaimed;
            this.itcCgst = itcCgst;
            this.itcSgst = itcSgst;
            this.itcIgst = itcIgst;
            this.itcCess = itcCess;
            this.itcOnImports = itcOnImports;
            this.itcOnDomestic = itcOnDomestic;
        }

        public Double getTotalItcClaimed() { return totalItcClaimed; }
        public void setTotalItcClaimed(Double totalItcClaimed) { this.totalItcClaimed = totalItcClaimed; }

        public Double getItcCgst() { return itcCgst; }
        public void setItcCgst(Double itcCgst) { this.itcCgst = itcCgst; }

        public Double getItcSgst() { return itcSgst; }
        public void setItcSgst(Double itcSgst) { this.itcSgst = itcSgst; }

        public Double getItcIgst() { return itcIgst; }
        public void setItcIgst(Double itcIgst) { this.itcIgst = itcIgst; }

        public Double getItcCess() { return itcCess; }
        public void setItcCess(Double itcCess) { this.itcCess = itcCess; }

        public Double getItcOnImports() { return itcOnImports; }
        public void setItcOnImports(Double itcOnImports) { this.itcOnImports = itcOnImports; }

        public Double getItcOnDomestic() { return itcOnDomestic; }
        public void setItcOnDomestic(Double itcOnDomestic) { this.itcOnDomestic = itcOnDomestic; }
    }

    public static class TaxLiabilityDto {
        private Double cgstPayable;
        private Double sgstPayable;
        private Double igstPayable;
        private Double cessPayable;
        private Double totalTaxPayable;
        private Double interest;
        private Double lateFee;

        public TaxLiabilityDto() {}

        public TaxLiabilityDto(Double cgstPayable, Double sgstPayable, Double igstPayable,
                               Double cessPayable, Double totalTaxPayable, Double interest, Double lateFee) {
            this.cgstPayable = cgstPayable;
            this.sgstPayable = sgstPayable;
            this.igstPayable = igstPayable;
            this.cessPayable = cessPayable;
            this.totalTaxPayable = totalTaxPayable;
            this.interest = interest;
            this.lateFee = lateFee;
        }

        public Double getCgstPayable() { return cgstPayable; }
        public void setCgstPayable(Double cgstPayable) { this.cgstPayable = cgstPayable; }

        public Double getSgstPayable() { return sgstPayable; }
        public void setSgstPayable(Double sgstPayable) { this.sgstPayable = sgstPayable; }

        public Double getIgstPayable() { return igstPayable; }
        public void setIgstPayable(Double igstPayable) { this.igstPayable = igstPayable; }

        public Double getCessPayable() { return cessPayable; }
        public void setCessPayable(Double cessPayable) { this.cessPayable = cessPayable; }

        public Double getTotalTaxPayable() { return totalTaxPayable; }
        public void setTotalTaxPayable(Double totalTaxPayable) { this.totalTaxPayable = totalTaxPayable; }

        public Double getInterest() { return interest; }
        public void setInterest(Double interest) { this.interest = interest; }

        public Double getLateFee() { return lateFee; }
        public void setLateFee(Double lateFee) { this.lateFee = lateFee; }
    }

    public static class PaymentDto {
        private Double totalTaxPaid;
        private Double cgstPaid;
        private Double sgstPaid;
        private Double igstPaid;
        private Double cessPaid;
        private String paymentDate;
        private String paymentReference;

        public PaymentDto() {}

        public PaymentDto(Double totalTaxPaid, Double cgstPaid, Double sgstPaid,
                          Double igstPaid, Double cessPaid, String paymentDate, String paymentReference) {
            this.totalTaxPaid = totalTaxPaid;
            this.cgstPaid = cgstPaid;
            this.sgstPaid = sgstPaid;
            this.igstPaid = igstPaid;
            this.cessPaid = cessPaid;
            this.paymentDate = paymentDate;
            this.paymentReference = paymentReference;
        }

        public Double getTotalTaxPaid() { return totalTaxPaid; }
        public void setTotalTaxPaid(Double totalTaxPaid) { this.totalTaxPaid = totalTaxPaid; }

        public Double getCgstPaid() { return cgstPaid; }
        public void setCgstPaid(Double cgstPaid) { this.cgstPaid = cgstPaid; }

        public Double getSgstPaid() { return sgstPaid; }
        public void setSgstPaid(Double sgstPaid) { this.sgstPaid = sgstPaid; }

        public Double getIgstPaid() { return igstPaid; }
        public void setIgstPaid(Double igstPaid) { this.igstPaid = igstPaid; }

        public Double getCessPaid() { return cessPaid; }
        public void setCessPaid(Double cessPaid) { this.cessPaid = cessPaid; }

        public String getPaymentDate() { return paymentDate; }
        public void setPaymentDate(String paymentDate) { this.paymentDate = paymentDate; }

        public String getPaymentReference() { return paymentReference; }
        public void setPaymentReference(String paymentReference) { this.paymentReference = paymentReference; }
    }

    public static class LateFeeDto {
        private Double totalLateFee;
        private Double cgstLateFee;
        private Double sgstLateFee;
        private Integer delayDays;

        public LateFeeDto() {}

        public LateFeeDto(Double totalLateFee, Double cgstLateFee, Double sgstLateFee, Integer delayDays) {
            this.totalLateFee = totalLateFee;
            this.cgstLateFee = cgstLateFee;
            this.sgstLateFee = sgstLateFee;
            this.delayDays = delayDays;
        }

        public Double getTotalLateFee() { return totalLateFee; }
        public void setTotalLateFee(Double totalLateFee) { this.totalLateFee = totalLateFee; }

        public Double getCgstLateFee() { return cgstLateFee; }
        public void setCgstLateFee(Double cgstLateFee) { this.cgstLateFee = cgstLateFee; }

        public Double getSgstLateFee() { return sgstLateFee; }
        public void setSgstLateFee(Double sgstLateFee) { this.sgstLateFee = sgstLateFee; }

        public Integer getDelayDays() { return delayDays; }
        public void setDelayDays(Integer delayDays) { this.delayDays = delayDays; }
    }
}
