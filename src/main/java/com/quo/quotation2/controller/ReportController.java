package com.quo.quotation2.controller;
import com.quo.quotation2.dto.responsedto.ApiResponseDto;
import com.quo.quotation2.dto.responsedto.ReportPeriodDto;
import com.quo.quotation2.dto.responsedto.ReportQuotationRowDto;
import com.quo.quotation2.dto.responsedto.ReportSummaryDto;
import com.quo.quotation2.exception.BadRequestException;

import com.quo.quotation2.service.ReportService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;

@RestController
@RequestMapping("/api/reports")
public class ReportController {

    private final ReportService reportService;

    public ReportController(ReportService reportService) {
        this.reportService = reportService;
    }

    // GET /api/reports/summary?dateFrom=2026-08-01&dateTo=2026-08-10
    @GetMapping("/summary")
    public ResponseEntity<ApiResponseDto<ReportSummaryDto>> getSummary(
            @RequestParam(required = false) String dateFrom,
            @RequestParam(required = false) String dateTo) {
        LocalDate from = parseDate(dateFrom, "dateFrom");
        LocalDate to = parseDate(dateTo, "dateTo");
        ReportSummaryDto summary = reportService.getSummary(from, to);
        return ResponseEntity.ok(ApiResponseDto.success("Report summary retrieved successfully", summary));
    }

    // GET /api/reports/trend?granularity=weekly&dateFrom=2026-07-01&dateTo=2026-08-10
    @GetMapping("/trend")
    public ResponseEntity<ApiResponseDto<List<ReportPeriodDto>>> getTrend(
            @RequestParam(required = false) String dateFrom,
            @RequestParam(required = false) String dateTo,
            @RequestParam(defaultValue = "weekly") String granularity) {
        if (!granularity.equalsIgnoreCase("daily")
                && !granularity.equalsIgnoreCase("weekly")
                && !granularity.equalsIgnoreCase("monthly")) {
            throw new BadRequestException("granularity must be one of: daily, weekly, monthly");
        }
        LocalDate from = parseDate(dateFrom, "dateFrom");
        LocalDate to = parseDate(dateTo, "dateTo");
        List<ReportPeriodDto> trend = reportService.getTrend(from, to, granularity);
        return ResponseEntity.ok(ApiResponseDto.success("Report trend retrieved successfully", trend));
    }

    // GET /api/reports/quotations?dateFrom=2026-08-01&dateTo=2026-08-10
    // Returns the flat quotation list the "Quotation Details" table / CSV / Excel / PDF export use.
    @GetMapping("/quotations")
    public ResponseEntity<ApiResponseDto<List<ReportQuotationRowDto>>> getQuotationRows(
            @RequestParam(required = false) String dateFrom,
            @RequestParam(required = false) String dateTo) {
        LocalDate from = parseDate(dateFrom, "dateFrom");
        LocalDate to = parseDate(dateTo, "dateTo");
        List<ReportQuotationRowDto> rows = reportService.getQuotationRows(from, to);
        return ResponseEntity.ok(ApiResponseDto.success("Report quotations retrieved successfully", rows));
    }

    private LocalDate parseDate(String raw, String fieldName) {
        if (raw == null || raw.isBlank()) return null;
        try {
            return LocalDate.parse(raw);
        } catch (DateTimeParseException ex) {
            throw new BadRequestException(fieldName + " must be in yyyy-MM-dd format");
        }
    }
}
