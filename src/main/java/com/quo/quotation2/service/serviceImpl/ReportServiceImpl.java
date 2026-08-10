package com.quo.quotation2.service.serviceImpl;

import com.quo.quotation2.dto.responsedto.ReportPeriodDto;
import com.quo.quotation2.dto.responsedto.ReportQuotationRowDto;
import com.quo.quotation2.dto.responsedto.ReportSummaryDto;
import com.quo.quotation2.entity.Quotation;
import com.quo.quotation2.entity.QuotationItem;
import com.quo.quotation2.repository.QuotationRepository;
import com.quo.quotation2.service.ReportService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.IsoFields;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ReportServiceImpl implements ReportService {

    private final QuotationRepository quotationRepository;

    private static final DateTimeFormatter MONTH_KEY = DateTimeFormatter.ofPattern("yyyy-MM");
    private static final DateTimeFormatter DAY_LABEL = DateTimeFormatter.ofPattern("dd MMM", Locale.ENGLISH);
    private static final DateTimeFormatter MONTH_LABEL = DateTimeFormatter.ofPattern("MMM yyyy", Locale.ENGLISH);

    public ReportServiceImpl(QuotationRepository quotationRepository) {
        this.quotationRepository = quotationRepository;
    }

    @Override
    public ReportSummaryDto getSummary(LocalDate dateFrom, LocalDate dateTo) {
        List<Quotation> quotations = quotationRepository.findForReport(dateFrom, dateTo);

        long total = quotations.size();
        double value = quotations.stream().mapToDouble(q -> nz(q.getGrandTotal())).sum();
        long accepted = quotations.stream().filter(q -> "Accepted".equalsIgnoreCase(q.getStatus())).count();
        long rejected = quotations.stream().filter(q -> "Rejected".equalsIgnoreCase(q.getStatus())).count();
        long pending = total - accepted - rejected;

        return new ReportSummaryDto(total, value, accepted, pending, rejected);
    }

    @Override
    public List<ReportPeriodDto> getTrend(LocalDate dateFrom, LocalDate dateTo, String granularity) {
        List<Quotation> quotations = quotationRepository.findForReport(dateFrom, dateTo);
        String gran = (granularity == null) ? "weekly" : granularity.toLowerCase();

        Map<String, ReportPeriodDto> buckets = new LinkedHashMap<>();
        // Sort by date first so buckets stream out in chronological order.
        quotations.stream()
                .filter(q -> q.getDate() != null)
                .sorted((a, b) -> a.getDate().compareTo(b.getDate()))
                .forEach(q -> {
                    LocalDate d = q.getDate();
                    String key = keyFor(d, gran);
                    ReportPeriodDto bucket = buckets.computeIfAbsent(key, k -> new ReportPeriodDto(k, labelFor(d, gran)));
                    bucket.addQuotation(nz(q.getGrandTotal()), q.getStatus());
                });

        return buckets.values().stream()
                .sorted((a, b) -> a.getKey().compareTo(b.getKey()))
                .collect(Collectors.toList());
    }

    @Override
    public List<ReportQuotationRowDto> getQuotationRows(LocalDate dateFrom, LocalDate dateTo) {
        List<Quotation> quotations = quotationRepository.findForReport(dateFrom, dateTo);
        return quotations.stream().map(this::toRow).collect(Collectors.toList());
    }

    // ---------------- helpers ----------------

    private ReportQuotationRowDto toRow(Quotation q) {
        String customerName = q.getCustomer() != null ? q.getCustomer().getName() : "";
        String machine = q.getItems() == null || q.getItems().isEmpty()
                ? "—"
                : q.getItems().stream().map(QuotationItem::getName).collect(Collectors.joining(", "));
        String date = q.getDate() != null ? q.getDate().toString() : "";
        return new ReportQuotationRowDto(q.getQuoteNo(), customerName, machine, nz(q.getGrandTotal()), q.getStatus(), date);
    }

    private double nz(Double v) {
        return v == null ? 0.0 : v;
    }

    private String keyFor(LocalDate d, String granularity) {
        switch (granularity) {
            case "daily":
                return d.toString(); // yyyy-MM-dd
            case "monthly":
                return d.format(MONTH_KEY); // yyyy-MM
            case "weekly":
            default:
                int weekYear = d.get(IsoFields.WEEK_BASED_YEAR);
                int weekNo = d.get(IsoFields.WEEK_OF_WEEK_BASED_YEAR);
                return String.format("%d-W%02d", weekYear, weekNo);
        }
    }

    private String labelFor(LocalDate d, String granularity) {
        switch (granularity) {
            case "daily":
                return d.format(DAY_LABEL); // "08 Aug"
            case "monthly":
                return d.format(MONTH_LABEL); // "Aug 2026"
            case "weekly":
            default:
                int weekYear = d.get(IsoFields.WEEK_BASED_YEAR);
                int weekNo = d.get(IsoFields.WEEK_OF_WEEK_BASED_YEAR);
                return String.format("%d · Wk %02d", weekYear, weekNo);
        }
    }
}

