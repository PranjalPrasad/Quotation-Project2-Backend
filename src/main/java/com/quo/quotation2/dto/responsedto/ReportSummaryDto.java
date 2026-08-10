package com.quo.quotation2.dto.responsedto;


public class ReportSummaryDto {

    private long totalQuotations;
    private double totalValue;
    private long accepted;
    private long pending;
    private long rejected;
    private int acceptedPct;
    private int pendingPct;
    private int rejectedPct;
    private String rangeLabel;

    public ReportSummaryDto() {}

    public ReportSummaryDto(long totalQuotations, double totalValue, long accepted, long pending, long rejected) {
        this.totalQuotations = totalQuotations;
        this.totalValue = totalValue;
        this.accepted = accepted;
        this.pending = pending;
        this.rejected = rejected;
        this.acceptedPct = pct(accepted, totalQuotations);
        this.pendingPct = pct(pending, totalQuotations);
        this.rejectedPct = pct(rejected, totalQuotations);
    }

    private static int pct(long part, long total) {
        if (total == 0) return 0;
        return (int) Math.round((part * 100.0) / total);
    }

    public long getTotalQuotations() { return totalQuotations; }
    public void setTotalQuotations(long totalQuotations) { this.totalQuotations = totalQuotations; }

    public double getTotalValue() { return totalValue; }
    public void setTotalValue(double totalValue) { this.totalValue = totalValue; }

    public long getAccepted() { return accepted; }
    public void setAccepted(long accepted) { this.accepted = accepted; }

    public long getPending() { return pending; }
    public void setPending(long pending) { this.pending = pending; }

    public long getRejected() { return rejected; }
    public void setRejected(long rejected) { this.rejected = rejected; }

    public int getAcceptedPct() { return acceptedPct; }
    public void setAcceptedPct(int acceptedPct) { this.acceptedPct = acceptedPct; }

    public int getPendingPct() { return pendingPct; }
    public void setPendingPct(int pendingPct) { this.pendingPct = pendingPct; }

    public int getRejectedPct() { return rejectedPct; }
    public void setRejectedPct(int rejectedPct) { this.rejectedPct = rejectedPct; }

    public String getRangeLabel() { return rangeLabel; }
    public void setRangeLabel(String rangeLabel) { this.rangeLabel = rangeLabel; }
}

