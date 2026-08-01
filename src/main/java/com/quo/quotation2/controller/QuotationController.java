package com.quo.quotation2.controller;
import com.quo.quotation2.dto.requestdto.ConvertToInvoiceRequestDto;
import com.quo.quotation2.dto.requestdto.QuotationRequestDto;
import com.quo.quotation2.dto.requestdto.StatusUpdateRequestDto;
import com.quo.quotation2.dto.responsedto.ApiResponseDto;
import com.quo.quotation2.dto.responsedto.PageResponseDto;
import com.quo.quotation2.dto.responsedto.QuotationListResponseDto;
import com.quo.quotation2.dto.responsedto.QuotationResponseDto;
import com.quo.quotation2.service.QuotationService;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/quotations")
public class QuotationController {

    private final QuotationService quotationService;

    public QuotationController(QuotationService quotationService) {
        this.quotationService = quotationService;
    }

    @PostMapping
    public ResponseEntity<ApiResponseDto<QuotationResponseDto>> createQuotation(
            @RequestBody QuotationRequestDto requestDto,
            @RequestHeader(value = "Authorization", required = false) String authHeader) {
        QuotationResponseDto response = quotationService.createQuotation(requestDto, authHeader);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponseDto.success("Quotation created successfully", response));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponseDto<QuotationResponseDto>> getQuotation(@PathVariable Long id) {
        QuotationResponseDto response = quotationService.getQuotation(id);
        return ResponseEntity.ok(ApiResponseDto.success("Quotation retrieved successfully", response));
    }

    @GetMapping("/by-quote-no/{quoteNo}")
    public ResponseEntity<ApiResponseDto<QuotationResponseDto>> getQuotationByQuoteNo(@PathVariable String quoteNo) {
        QuotationResponseDto response = quotationService.getQuotationByQuoteNo(quoteNo);
        return ResponseEntity.ok(ApiResponseDto.success("Quotation retrieved successfully", response));
    }

    @GetMapping
    public ResponseEntity<ApiResponseDto<PageResponseDto<QuotationListResponseDto>>> getQuotations(
            @RequestParam(required = false) String status,
            @RequestParam(required = false) String dateFrom,
            @RequestParam(required = false) String dateTo,
            @RequestParam(required = false) Long customerId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size,
            @RequestParam(defaultValue = "createdAt") String sortBy,
            @RequestParam(defaultValue = "desc") String sortDir) {

        Sort sort = sortDir.equalsIgnoreCase("asc") ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(page, size, sort);

        PageResponseDto<QuotationListResponseDto> response = quotationService.getQuotations(
                status, dateFrom, dateTo, customerId, pageable);

        return ResponseEntity.ok(ApiResponseDto.success("Quotations retrieved successfully", response));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponseDto<QuotationResponseDto>> updateQuotation(
            @PathVariable Long id,
            @RequestBody QuotationRequestDto requestDto,
            @RequestHeader(value = "Authorization", required = false) String authHeader) {
        QuotationResponseDto response = quotationService.updateQuotation(id, requestDto, authHeader);
        return ResponseEntity.ok(ApiResponseDto.success("Quotation updated successfully", response));
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<ApiResponseDto<QuotationResponseDto>> updateStatus(
            @PathVariable Long id,
            @RequestBody StatusUpdateRequestDto requestDto,
            @RequestHeader(value = "Authorization", required = false) String authHeader) {
        QuotationResponseDto response = quotationService.updateQuotationStatus(id, requestDto, authHeader);
        return ResponseEntity.ok(ApiResponseDto.success("Status updated successfully", response));
    }

    @PostMapping("/{id}/convert-to-invoice")
    public ResponseEntity<ApiResponseDto<QuotationResponseDto>> convertToInvoice(
            @PathVariable Long id,
            @RequestBody ConvertToInvoiceRequestDto requestDto,
            @RequestHeader(value = "Authorization", required = false) String authHeader) {
        QuotationResponseDto response = quotationService.convertToInvoice(id, requestDto, authHeader);
        return ResponseEntity.ok(ApiResponseDto.success("Quotation converted to invoice successfully", response));
    }

    @PostMapping("/{id}/duplicate")
    public ResponseEntity<ApiResponseDto<QuotationResponseDto>> duplicateQuotation(
            @PathVariable Long id,
            @RequestHeader(value = "Authorization", required = false) String authHeader) {
        QuotationResponseDto response = quotationService.duplicateQuotation(id, authHeader);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponseDto.success("Quotation duplicated successfully", response));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponseDto<Void>> deleteQuotation(
            @PathVariable Long id,
            @RequestHeader(value = "Authorization", required = false) String authHeader) {
        quotationService.deleteQuotation(id, authHeader);
        return ResponseEntity.ok(ApiResponseDto.success("Quotation deleted successfully", null));
    }
}