package com.quo.quotation2.service;

import com.quo.quotation2.dto.requestdto.ConvertToInvoiceRequestDto;
import com.quo.quotation2.dto.requestdto.QuotationRequestDto;
import com.quo.quotation2.dto.requestdto.StatusUpdateRequestDto;
import com.quo.quotation2.dto.responsedto.PageResponseDto;
import com.quo.quotation2.dto.responsedto.QuotationListResponseDto;
import com.quo.quotation2.dto.responsedto.QuotationResponseDto;
import org.springframework.data.domain.Pageable;

public interface QuotationService {
    QuotationResponseDto createQuotation(QuotationRequestDto requestDto, String authHeader);
    QuotationResponseDto getQuotation(Long id);
    QuotationResponseDto getQuotationByQuoteNo(String quoteNo);
    PageResponseDto<QuotationListResponseDto> getQuotations(String status, String dateFrom, String dateTo, Long customerId, Pageable pageable);
    QuotationResponseDto updateQuotation(Long id, QuotationRequestDto requestDto, String authHeader);
    QuotationResponseDto updateQuotationStatus(Long id, StatusUpdateRequestDto requestDto, String authHeader);
    QuotationResponseDto convertToInvoice(Long id, ConvertToInvoiceRequestDto requestDto, String authHeader);
    QuotationResponseDto duplicateQuotation(Long id, String authHeader);
    void deleteQuotation(Long id, String authHeader);
    void processExpiredQuotations();
}