package com.quo.quotation2.service;

import com.quo.quotation2.dto.responsedto.ReportPeriodDto;
import com.quo.quotation2.dto.responsedto.ReportQuotationRowDto;
import com.quo.quotation2.dto.responsedto.ReportSummaryDto;

import java.time.LocalDate;
import java.util.List;

public interface ReportService {

    ReportSummaryDto getSummary(LocalDate dateFrom, LocalDate dateTo);

    List<ReportPeriodDto> getTrend(LocalDate dateFrom, LocalDate dateTo, String granularity);

    List<ReportQuotationRowDto> getQuotationRows(LocalDate dateFrom, LocalDate dateTo);
}

