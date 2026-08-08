package com.quo.quotation2.controller;

import com.quo.quotation2.dto.requestdto.DashboardResponseDto;
import com.quo.quotation2.dto.responsedto.ApiResponseDto;
import com.quo.quotation2.service.DashboardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/dashboard")
public class DashboardController {

    @Autowired
    private DashboardService dashboardService;

    @GetMapping("/data")
    public ResponseEntity<ApiResponseDto<DashboardResponseDto>> getDashboardData() {
        DashboardResponseDto dashboardData = dashboardService.getDashboardData();
        return ResponseEntity.ok(ApiResponseDto.success("Dashboard data fetched successfully", dashboardData));
    }

    @GetMapping("/summary")
    public ResponseEntity<ApiResponseDto<DashboardResponseDto.SummaryMetrics>> getDashboardSummary() {
        DashboardResponseDto dashboardData = dashboardService.getDashboardData();
        return ResponseEntity.ok(ApiResponseDto.success("Dashboard summary fetched successfully", dashboardData.getSummary()));
    }

    @GetMapping("/top-machines")
    public ResponseEntity<ApiResponseDto<java.util.List<DashboardResponseDto.TopMachine>>> getTopMachines() {
        DashboardResponseDto dashboardData = dashboardService.getDashboardData();
        return ResponseEntity.ok(ApiResponseDto.success("Top machines fetched successfully", dashboardData.getTopMachines()));
    }

    @GetMapping("/recent-payments")
    public ResponseEntity<ApiResponseDto<java.util.List<DashboardResponseDto.RecentPayment>>> getRecentPayments() {
        DashboardResponseDto dashboardData = dashboardService.getDashboardData();
        return ResponseEntity.ok(ApiResponseDto.success("Recent payments fetched successfully", dashboardData.getRecentPayments()));
    }

    @GetMapping("/recent-quotations")
    public ResponseEntity<ApiResponseDto<java.util.List<DashboardResponseDto.QuotationSummary>>> getRecentQuotations() {
        DashboardResponseDto dashboardData = dashboardService.getDashboardData();
        return ResponseEntity.ok(ApiResponseDto.success("Recent quotations fetched successfully", dashboardData.getRecentQuotations()));
    }

    @GetMapping("/monthly-trend")
    public ResponseEntity<ApiResponseDto<java.util.List<DashboardResponseDto.MonthlyTrend>>> getMonthlyTrend() {
        DashboardResponseDto dashboardData = dashboardService.getDashboardData();
        return ResponseEntity.ok(ApiResponseDto.success("Monthly trend fetched successfully", dashboardData.getMonthlyTrend()));
    }

    @GetMapping("/category-revenue")
    public ResponseEntity<ApiResponseDto<java.util.List<DashboardResponseDto.CategoryRevenue>>> getCategoryRevenue() {
        DashboardResponseDto dashboardData = dashboardService.getDashboardData();
        return ResponseEntity.ok(ApiResponseDto.success("Category revenue fetched successfully", dashboardData.getCategoryRevenue()));
    }
}
