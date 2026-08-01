package com.quo.quotation2.dto.requestdto;

public class PaymentTermsDto {
    private Double advancePercent;
    private Double beforeDispatchPercent;
    private Double onDeliveryPercent;
    private Double balancePercent;
    private Double totalPercent;

    public PaymentTermsDto() {}

    public PaymentTermsDto(Double advancePercent, Double beforeDispatchPercent, Double onDeliveryPercent,
                           Double balancePercent, Double totalPercent) {
        this.advancePercent = advancePercent;
        this.beforeDispatchPercent = beforeDispatchPercent;
        this.onDeliveryPercent = onDeliveryPercent;
        this.balancePercent = balancePercent;
        this.totalPercent = totalPercent;
    }

    // Getters and Setters
    public Double getAdvancePercent() { return advancePercent; }
    public void setAdvancePercent(Double advancePercent) { this.advancePercent = advancePercent; }

    public Double getBeforeDispatchPercent() { return beforeDispatchPercent; }
    public void setBeforeDispatchPercent(Double beforeDispatchPercent) { this.beforeDispatchPercent = beforeDispatchPercent; }

    public Double getOnDeliveryPercent() { return onDeliveryPercent; }
    public void setOnDeliveryPercent(Double onDeliveryPercent) { this.onDeliveryPercent = onDeliveryPercent; }

    public Double getBalancePercent() { return balancePercent; }
    public void setBalancePercent(Double balancePercent) { this.balancePercent = balancePercent; }

    public Double getTotalPercent() { return totalPercent; }
    public void setTotalPercent(Double totalPercent) { this.totalPercent = totalPercent; }
}