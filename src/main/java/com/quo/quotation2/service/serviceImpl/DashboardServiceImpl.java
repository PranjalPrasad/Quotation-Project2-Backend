package com.quo.quotation2.service.serviceImpl;

import com.quo.quotation2.dto.requestdto.DashboardResponseDto;
import com.quo.quotation2.entity.Quotation;
import com.quo.quotation2.repository.DashboardRepository;
import com.quo.quotation2.service.DashboardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;


import java.time.LocalDateTime;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;

@Service
public class DashboardServiceImpl implements DashboardService {

    @Autowired
    private DashboardRepository dashboardRepository;

    @Value("${dashboard.top-machines.limit:5}")
    private int topMachinesLimit;

    @Value("${dashboard.recent-quotations.limit:10}")
    private int recentQuotationsLimit;

    @Override
    public DashboardResponseDto getDashboardData() {
        DashboardResponseDto response = new DashboardResponseDto();

        response.setSummary(getSummaryMetrics());
        response.setTopMachines(getTopMachines());
        response.setRecentPayments(getRecentPayments());
        response.setRecentQuotations(getRecentQuotations());
        response.setMonthlyTrend(getMonthlyTrend());
        response.setCategoryRevenue(getCategoryRevenue());
        response.setQuickStats(getQuickStats());

        return response;
    }

    // ============================================================
    // SUMMARY METRICS
    // ============================================================
    private DashboardResponseDto.SummaryMetrics getSummaryMetrics() {
        DashboardResponseDto.SummaryMetrics summary = new DashboardResponseDto.SummaryMetrics();

        LocalDateTime now = LocalDateTime.now();
        LocalDateTime monthStart = YearMonth.now().atDay(1).atStartOfDay();
        LocalDateTime monthEnd = now;

        try {
            summary.setTotalCustomers(dashboardRepository.countAllCustomers());
            summary.setTotalQuotationsAllTime(dashboardRepository.countAllQuotations());
            summary.setTotalQuotationValueAllTime(dashboardRepository.sumAllQuotationValues());
            summary.setTotalQuotationsMonth(dashboardRepository.countQuotationsBetweenDates(monthStart, monthEnd));
            summary.setTotalQuotationValueMonth(dashboardRepository.sumQuotationValuesBetweenDates(monthStart, monthEnd));
            summary.setPendingDecision(dashboardRepository.countByStatus("Pending"));
            summary.setAcceptedAllTime(dashboardRepository.countAcceptedQuotations());
        } catch (Exception e) {
            e.printStackTrace();
        }

        summary.setTotalInvoicesRaised(0);
        summary.setTotalOutstanding(0.0);
        summary.setOverdueInvoiceCount(0);
        summary.setMachinesDispatchedMonth(0);

        Integer totalQuotations = summary.getTotalQuotationsAllTime();
        if (totalQuotations != null && totalQuotations > 0) {
            summary.setConversionRate((summary.getAcceptedAllTime() * 100.0) / totalQuotations);
            summary.setAverageQuotationValue(summary.getTotalQuotationValueAllTime() / totalQuotations);
        } else {
            summary.setConversionRate(0.0);
            summary.setAverageQuotationValue(0.0);
        }

        return summary;
    }

    // ============================================================
    // TOP MACHINES
    // ============================================================
    private List<DashboardResponseDto.TopMachine> getTopMachines() {
        try {
            LocalDateTime now = LocalDateTime.now();
            LocalDateTime monthStart = YearMonth.now().atDay(1).atStartOfDay();

            List<Object[]> results = dashboardRepository.findTopMachinesByMonth(monthStart, now);

            if (results == null || results.isEmpty()) {
                return new ArrayList<>();
            }

            List<DashboardResponseDto.TopMachine> machines = new ArrayList<>();
            int rank = 1;
            for (Object[] row : results) {
                if (rank > topMachinesLimit) break;
                DashboardResponseDto.TopMachine machine = new DashboardResponseDto.TopMachine();
                machine.setModel((String) row[0]);
                machine.setUnitsMonth(((Number) row[1]).intValue());
                machine.setUnitsYtd(((Number) row[1]).intValue() * 5);
                machine.setRevenue(((Number) row[2]).doubleValue());
                machine.setRank(rank++);
                machines.add(machine);
            }
            return machines;
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    // ============================================================
    // RECENT PAYMENTS
    // ============================================================
    private List<DashboardResponseDto.RecentPayment> getRecentPayments() {
        return new ArrayList<>();
    }

    // ============================================================
    // RECENT QUOTATIONS
    // ============================================================
    private List<DashboardResponseDto.QuotationSummary> getRecentQuotations() {
        try {
            List<Quotation> quotations = dashboardRepository.findRecentQuotationsWithLimit(recentQuotationsLimit);

            if (quotations == null || quotations.isEmpty()) {
                return new ArrayList<>();
            }

            List<DashboardResponseDto.QuotationSummary> result = new ArrayList<>();
            for (Quotation q : quotations) {
                DashboardResponseDto.QuotationSummary dto = new DashboardResponseDto.QuotationSummary();
                dto.setQuotationNo(q.getQuoteNo());
                dto.setCustomer(q.getCustomer() != null ? q.getCustomer().getName() : "N/A");

                String machineName = "N/A";
                if (q.getItems() != null && !q.getItems().isEmpty()) {
                    machineName = q.getItems().get(0).getName();
                }
                dto.setMachine(machineName);
                dto.setAmount(q.getGrandTotal());
                dto.setStatus(q.getStatus());
                if (q.getQuoteDate() != null) {
                    dto.setDate(q.getQuoteDate().toLocalDate());
                }
                dto.setCustomerId(q.getCustomer() != null ? q.getCustomer().getId() : null);
                dto.setQuotationId(q.getId());
                result.add(dto);
            }
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    // ============================================================
    // MONTHLY TREND
    // ============================================================
    private List<DashboardResponseDto.MonthlyTrend> getMonthlyTrend() {
        try {
            LocalDateTime sixMonthsAgo = LocalDateTime.now().minusMonths(6);
            List<Object[]> results = dashboardRepository.getMonthlyQuotationTrend(sixMonthsAgo);

            if (results == null || results.isEmpty()) {
                return new ArrayList<>();
            }

            List<DashboardResponseDto.MonthlyTrend> trends = new ArrayList<>();
            for (Object[] row : results) {
                DashboardResponseDto.MonthlyTrend trend = new DashboardResponseDto.MonthlyTrend();
                int month = ((Number) row[0]).intValue();
                int year = ((Number) row[1]).intValue();
                trend.setMonth(getMonthName(month));
                trend.setYear(year);
                trend.setTotalValue(((Number) row[2]).doubleValue());
                trend.setQuotationCount(((Number) row[3]).intValue());
                trends.add(trend);
            }
            return trends;
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    // ============================================================
    // CATEGORY REVENUE
    // ============================================================
    private List<DashboardResponseDto.CategoryRevenue> getCategoryRevenue() {
        try {
            List<Object[]> results = dashboardRepository.getCategoryRevenue();

            if (results == null || results.isEmpty()) {
                return new ArrayList<>();
            }

            double totalRevenue = 0;
            for (Object[] row : results) {
                totalRevenue += ((Number) row[1]).doubleValue();
            }

            List<DashboardResponseDto.CategoryRevenue> categories = new ArrayList<>();
            for (Object[] row : results) {
                DashboardResponseDto.CategoryRevenue category = new DashboardResponseDto.CategoryRevenue();
                category.setCategory((String) row[0]);
                double revenue = ((Number) row[1]).doubleValue();
                category.setTotalRevenue(revenue);
                category.setQuotationCount(((Number) row[2]).intValue());
                category.setPercentage(totalRevenue > 0 ? (revenue * 100.0) / totalRevenue : 0);
                categories.add(category);
            }
            return categories;
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    // ============================================================
    // QUICK STATS
    // ============================================================
    private DashboardResponseDto.QuickStats getQuickStats() {
        DashboardResponseDto.QuickStats stats = new DashboardResponseDto.QuickStats();

        stats.setMachinesInProduction(0);
        stats.setProductionBreakdown(new ArrayList<>());

        try {
            Integer total = dashboardRepository.countAllQuotations();
            Integer accepted = dashboardRepository.countAcceptedQuotations();
            if (total != null && total > 0) {
                stats.setConversionRate((accepted * 100.0) / total);
                stats.setConversionSubText(accepted + " of " + total + " quotations");
            } else {
                stats.setConversionRate(0.0);
                stats.setConversionSubText("0 of 0 quotations");
            }
        } catch (Exception e) {
            stats.setConversionRate(0.0);
            stats.setConversionSubText("0 of 0 quotations");
        }

        return stats;
    }

    // ============================================================
    // HELPER METHOD
    // ============================================================
    private String getMonthName(int month) {
        String[] months = {"Jan", "Feb", "Mar", "Apr", "May", "Jun",
                "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"};
        return months[month - 1];
    }
}








