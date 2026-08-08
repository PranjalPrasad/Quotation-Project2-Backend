package com.quo.quotation2.dto.requestdto;
import java.time.LocalDate;
import java.util.List;

public class DashboardResponseDto {

    private SummaryMetrics summary;
    private List<TopMachine> topMachines;
    private List<RecentPayment> recentPayments;
    private List<QuotationSummary> recentQuotations;
    private List<MonthlyTrend> monthlyTrend;
    private List<CategoryRevenue> categoryRevenue;
    private QuickStats quickStats;

    // ============================================================
    // Constructors
    // ============================================================

    public DashboardResponseDto() {}

    public DashboardResponseDto(SummaryMetrics summary, List<TopMachine> topMachines,
                                List<RecentPayment> recentPayments, List<QuotationSummary> recentQuotations,
                                List<MonthlyTrend> monthlyTrend, List<CategoryRevenue> categoryRevenue,
                                QuickStats quickStats) {
        this.summary = summary;
        this.topMachines = topMachines;
        this.recentPayments = recentPayments;
        this.recentQuotations = recentQuotations;
        this.monthlyTrend = monthlyTrend;
        this.categoryRevenue = categoryRevenue;
        this.quickStats = quickStats;
    }

    // ============================================================
    // Getters and Setters
    // ============================================================

    public SummaryMetrics getSummary() { return summary; }
    public void setSummary(SummaryMetrics summary) { this.summary = summary; }

    public List<TopMachine> getTopMachines() { return topMachines; }
    public void setTopMachines(List<TopMachine> topMachines) { this.topMachines = topMachines; }

    public List<RecentPayment> getRecentPayments() { return recentPayments; }
    public void setRecentPayments(List<RecentPayment> recentPayments) { this.recentPayments = recentPayments; }

    public List<QuotationSummary> getRecentQuotations() { return recentQuotations; }
    public void setRecentQuotations(List<QuotationSummary> recentQuotations) { this.recentQuotations = recentQuotations; }

    public List<MonthlyTrend> getMonthlyTrend() { return monthlyTrend; }
    public void setMonthlyTrend(List<MonthlyTrend> monthlyTrend) { this.monthlyTrend = monthlyTrend; }

    public List<CategoryRevenue> getCategoryRevenue() { return categoryRevenue; }
    public void setCategoryRevenue(List<CategoryRevenue> categoryRevenue) { this.categoryRevenue = categoryRevenue; }

    public QuickStats getQuickStats() { return quickStats; }
    public void setQuickStats(QuickStats quickStats) { this.quickStats = quickStats; }

    // ============================================================
    // Inner Classes
    // ============================================================

    public static class SummaryMetrics {
        private Integer totalCustomers;
        private Integer totalQuotationsAllTime;
        private Integer totalQuotationsMonth;
        private Double totalQuotationValueAllTime;
        private Double totalQuotationValueMonth;
        private Integer pendingDecision;
        private Integer acceptedAllTime;
        private Integer machinesDispatchedMonth;
        private Integer totalInvoicesRaised;
        private Double totalOutstanding;
        private Integer overdueInvoiceCount;
        private Double conversionRate;
        private Double averageQuotationValue;

        public SummaryMetrics() {}

        public SummaryMetrics(Integer totalCustomers, Integer totalQuotationsAllTime, Integer totalQuotationsMonth,
                              Double totalQuotationValueAllTime, Double totalQuotationValueMonth, Integer pendingDecision,
                              Integer acceptedAllTime, Integer machinesDispatchedMonth, Integer totalInvoicesRaised,
                              Double totalOutstanding, Integer overdueInvoiceCount, Double conversionRate,
                              Double averageQuotationValue) {
            this.totalCustomers = totalCustomers;
            this.totalQuotationsAllTime = totalQuotationsAllTime;
            this.totalQuotationsMonth = totalQuotationsMonth;
            this.totalQuotationValueAllTime = totalQuotationValueAllTime;
            this.totalQuotationValueMonth = totalQuotationValueMonth;
            this.pendingDecision = pendingDecision;
            this.acceptedAllTime = acceptedAllTime;
            this.machinesDispatchedMonth = machinesDispatchedMonth;
            this.totalInvoicesRaised = totalInvoicesRaised;
            this.totalOutstanding = totalOutstanding;
            this.overdueInvoiceCount = overdueInvoiceCount;
            this.conversionRate = conversionRate;
            this.averageQuotationValue = averageQuotationValue;
        }

        // Getters and Setters
        public Integer getTotalCustomers() { return totalCustomers; }
        public void setTotalCustomers(Integer totalCustomers) { this.totalCustomers = totalCustomers; }

        public Integer getTotalQuotationsAllTime() { return totalQuotationsAllTime; }
        public void setTotalQuotationsAllTime(Integer totalQuotationsAllTime) { this.totalQuotationsAllTime = totalQuotationsAllTime; }

        public Integer getTotalQuotationsMonth() { return totalQuotationsMonth; }
        public void setTotalQuotationsMonth(Integer totalQuotationsMonth) { this.totalQuotationsMonth = totalQuotationsMonth; }

        public Double getTotalQuotationValueAllTime() { return totalQuotationValueAllTime; }
        public void setTotalQuotationValueAllTime(Double totalQuotationValueAllTime) { this.totalQuotationValueAllTime = totalQuotationValueAllTime; }

        public Double getTotalQuotationValueMonth() { return totalQuotationValueMonth; }
        public void setTotalQuotationValueMonth(Double totalQuotationValueMonth) { this.totalQuotationValueMonth = totalQuotationValueMonth; }

        public Integer getPendingDecision() { return pendingDecision; }
        public void setPendingDecision(Integer pendingDecision) { this.pendingDecision = pendingDecision; }

        public Integer getAcceptedAllTime() { return acceptedAllTime; }
        public void setAcceptedAllTime(Integer acceptedAllTime) { this.acceptedAllTime = acceptedAllTime; }

        public Integer getMachinesDispatchedMonth() { return machinesDispatchedMonth; }
        public void setMachinesDispatchedMonth(Integer machinesDispatchedMonth) { this.machinesDispatchedMonth = machinesDispatchedMonth; }

        public Integer getTotalInvoicesRaised() { return totalInvoicesRaised; }
        public void setTotalInvoicesRaised(Integer totalInvoicesRaised) { this.totalInvoicesRaised = totalInvoicesRaised; }

        public Double getTotalOutstanding() { return totalOutstanding; }
        public void setTotalOutstanding(Double totalOutstanding) { this.totalOutstanding = totalOutstanding; }

        public Integer getOverdueInvoiceCount() { return overdueInvoiceCount; }
        public void setOverdueInvoiceCount(Integer overdueInvoiceCount) { this.overdueInvoiceCount = overdueInvoiceCount; }

        public Double getConversionRate() { return conversionRate; }
        public void setConversionRate(Double conversionRate) { this.conversionRate = conversionRate; }

        public Double getAverageQuotationValue() { return averageQuotationValue; }
        public void setAverageQuotationValue(Double averageQuotationValue) { this.averageQuotationValue = averageQuotationValue; }
    }

    public static class TopMachine {
        private String model;
        private Integer unitsMonth;
        private Integer unitsYtd;
        private Double revenue;
        private Integer rank;

        public TopMachine() {}

        public TopMachine(String model, Integer unitsMonth, Integer unitsYtd, Double revenue, Integer rank) {
            this.model = model;
            this.unitsMonth = unitsMonth;
            this.unitsYtd = unitsYtd;
            this.revenue = revenue;
            this.rank = rank;
        }

        public String getModel() { return model; }
        public void setModel(String model) { this.model = model; }

        public Integer getUnitsMonth() { return unitsMonth; }
        public void setUnitsMonth(Integer unitsMonth) { this.unitsMonth = unitsMonth; }

        public Integer getUnitsYtd() { return unitsYtd; }
        public void setUnitsYtd(Integer unitsYtd) { this.unitsYtd = unitsYtd; }

        public Double getRevenue() { return revenue; }
        public void setRevenue(Double revenue) { this.revenue = revenue; }

        public Integer getRank() { return rank; }
        public void setRank(Integer rank) { this.rank = rank; }
    }

    public static class RecentPayment {
        private String customer;
        private Double amount;
        private String status;
        private LocalDate paymentDate;
        private String paymentReference;
        private String invoiceNumber;

        public RecentPayment() {}

        public RecentPayment(String customer, Double amount, String status, LocalDate paymentDate,
                             String paymentReference, String invoiceNumber) {
            this.customer = customer;
            this.amount = amount;
            this.status = status;
            this.paymentDate = paymentDate;
            this.paymentReference = paymentReference;
            this.invoiceNumber = invoiceNumber;
        }

        public String getCustomer() { return customer; }
        public void setCustomer(String customer) { this.customer = customer; }

        public Double getAmount() { return amount; }
        public void setAmount(Double amount) { this.amount = amount; }

        public String getStatus() { return status; }
        public void setStatus(String status) { this.status = status; }

        public LocalDate getPaymentDate() { return paymentDate; }
        public void setPaymentDate(LocalDate paymentDate) { this.paymentDate = paymentDate; }

        public String getPaymentReference() { return paymentReference; }
        public void setPaymentReference(String paymentReference) { this.paymentReference = paymentReference; }

        public String getInvoiceNumber() { return invoiceNumber; }
        public void setInvoiceNumber(String invoiceNumber) { this.invoiceNumber = invoiceNumber; }
    }

    public static class QuotationSummary {
        private String quotationNo;
        private String customer;
        private String machine;
        private Double amount;
        private String status;
        private LocalDate date;
        private Long customerId;
        private Long quotationId;

        public QuotationSummary() {}

        public QuotationSummary(String quotationNo, String customer, String machine, Double amount,
                                String status, LocalDate date, Long customerId, Long quotationId) {
            this.quotationNo = quotationNo;
            this.customer = customer;
            this.machine = machine;
            this.amount = amount;
            this.status = status;
            this.date = date;
            this.customerId = customerId;
            this.quotationId = quotationId;
        }

        public String getQuotationNo() { return quotationNo; }
        public void setQuotationNo(String quotationNo) { this.quotationNo = quotationNo; }

        public String getCustomer() { return customer; }
        public void setCustomer(String customer) { this.customer = customer; }

        public String getMachine() { return machine; }
        public void setMachine(String machine) { this.machine = machine; }

        public Double getAmount() { return amount; }
        public void setAmount(Double amount) { this.amount = amount; }

        public String getStatus() { return status; }
        public void setStatus(String status) { this.status = status; }

        public LocalDate getDate() { return date; }
        public void setDate(LocalDate date) { this.date = date; }

        public Long getCustomerId() { return customerId; }
        public void setCustomerId(Long customerId) { this.customerId = customerId; }

        public Long getQuotationId() { return quotationId; }
        public void setQuotationId(Long quotationId) { this.quotationId = quotationId; }
    }

    public static class MonthlyTrend {
        private String month;
        private Integer year;
        private Double totalValue;
        private Integer quotationCount;

        public MonthlyTrend() {}

        public MonthlyTrend(String month, Integer year, Double totalValue, Integer quotationCount) {
            this.month = month;
            this.year = year;
            this.totalValue = totalValue;
            this.quotationCount = quotationCount;
        }

        public String getMonth() { return month; }
        public void setMonth(String month) { this.month = month; }

        public Integer getYear() { return year; }
        public void setYear(Integer year) { this.year = year; }

        public Double getTotalValue() { return totalValue; }
        public void setTotalValue(Double totalValue) { this.totalValue = totalValue; }

        public Integer getQuotationCount() { return quotationCount; }
        public void setQuotationCount(Integer quotationCount) { this.quotationCount = quotationCount; }
    }

    public static class CategoryRevenue {
        private String category;
        private Double totalRevenue;
        private Integer quotationCount;
        private Double percentage;

        public CategoryRevenue() {}

        public CategoryRevenue(String category, Double totalRevenue, Integer quotationCount, Double percentage) {
            this.category = category;
            this.totalRevenue = totalRevenue;
            this.quotationCount = quotationCount;
            this.percentage = percentage;
        }

        public String getCategory() { return category; }
        public void setCategory(String category) { this.category = category; }

        public Double getTotalRevenue() { return totalRevenue; }
        public void setTotalRevenue(Double totalRevenue) { this.totalRevenue = totalRevenue; }

        public Integer getQuotationCount() { return quotationCount; }
        public void setQuotationCount(Integer quotationCount) { this.quotationCount = quotationCount; }

        public Double getPercentage() { return percentage; }
        public void setPercentage(Double percentage) { this.percentage = percentage; }
    }

    public static class QuickStats {
        private Integer machinesInProduction;
        private List<ProductionItem> productionBreakdown;
        private Double conversionRate;
        private String conversionSubText;

        public QuickStats() {}

        public QuickStats(Integer machinesInProduction, List<ProductionItem> productionBreakdown,
                          Double conversionRate, String conversionSubText) {
            this.machinesInProduction = machinesInProduction;
            this.productionBreakdown = productionBreakdown;
            this.conversionRate = conversionRate;
            this.conversionSubText = conversionSubText;
        }

        public Integer getMachinesInProduction() { return machinesInProduction; }
        public void setMachinesInProduction(Integer machinesInProduction) { this.machinesInProduction = machinesInProduction; }

        public List<ProductionItem> getProductionBreakdown() { return productionBreakdown; }
        public void setProductionBreakdown(List<ProductionItem> productionBreakdown) { this.productionBreakdown = productionBreakdown; }

        public Double getConversionRate() { return conversionRate; }
        public void setConversionRate(Double conversionRate) { this.conversionRate = conversionRate; }

        public String getConversionSubText() { return conversionSubText; }
        public void setConversionSubText(String conversionSubText) { this.conversionSubText = conversionSubText; }
    }

    public static class ProductionItem {
        private String label;
        private Integer count;

        public ProductionItem() {}

        public ProductionItem(String label, Integer count) {
            this.label = label;
            this.count = count;
        }

        public String getLabel() { return label; }
        public void setLabel(String label) { this.label = label; }

        public Integer getCount() { return count; }
        public void setCount(Integer count) { this.count = count; }
    }
}






