package com.quo.quotation2.dto.responsedto;

public class ReportPeriodDto {

    private String key;     // sortable key, e.g. 2026-08-04 / 2026-W32 / 2026-08
    private String label;   // display label, e.g. "04 Aug" / "2026 · Wk 32" / "Aug 2026"
    private long count;
    private double value;
    private long accepted;
    private long pending;
    private long rejected;

    public ReportPeriodDto() {}

    public ReportPeriodDto(String key, String label) {
        this.key = key;
        this.label = label;
    }

    public void addQuotation(double amount, String status) {
        this.count += 1;
        this.value += amount;
        if ("Accepted".equalsIgnoreCase(status)) this.accepted += 1;
        else if ("Rejected".equalsIgnoreCase(status)) this.rejected += 1;
        else this.pending += 1;
    }

    public String getKey() { return key; }
    public void setKey(String key) { this.key = key; }

    public String getLabel() { return label; }
    public void setLabel(String label) { this.label = label; }

    public long getCount() { return count; }
    public void setCount(long count) { this.count = count; }

    public double getValue() { return value; }
    public void setValue(double value) { this.value = value; }

    public long getAccepted() { return accepted; }
    public void setAccepted(long accepted) { this.accepted = accepted; }

    public long getPending() { return pending; }
    public void setPending(long pending) { this.pending = pending; }

    public long getRejected() { return rejected; }
    public void setRejected(long rejected) { this.rejected = rejected; }
}

