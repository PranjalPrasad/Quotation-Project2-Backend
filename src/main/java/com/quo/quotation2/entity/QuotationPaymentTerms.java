package com.quo.quotation2.entity;

import jakarta.persistence.*;


@Entity
@Table(name = "quotation_payment_terms")
public class QuotationPaymentTerms {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "quotation_id", nullable = false, unique = true)
    private Quotation quotation;

    @Column(name = "advance_percent")
    private Double advancePercent;

    @Column(name = "before_dispatch_percent")
    private Double beforeDispatchPercent;

    @Column(name = "on_delivery_percent")
    private Double onDeliveryPercent;

    @Column(name = "balance_percent")
    private Double balancePercent;

    @Column(name = "total_percent")
    private Double totalPercent;

    // Constructors
    public QuotationPaymentTerms() {}

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Quotation getQuotation() { return quotation; }
    public void setQuotation(Quotation quotation) { this.quotation = quotation; }

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
