package com.quo.quotation2.controller;

import com.quo.quotation2.dto.requestdto.PurchaseRequestDto;
import com.quo.quotation2.dto.requestdto.SupplierRequestDto;
import com.quo.quotation2.dto.responsedto.ApiResponseDto;
import com.quo.quotation2.dto.responsedto.Gstr1BResponseDto;
import com.quo.quotation2.dto.responsedto.Gstr3BResponseDto;
import com.quo.quotation2.dto.responsedto.PurchaseResponseDto;
import com.quo.quotation2.dto.responsedto.SupplierResponseDto;
import com.quo.quotation2.service.PurchaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/purchase")
public class PurchaseController {

    @Autowired
    private PurchaseService purchaseService;

    // ============================================================
    // Purchase Order Endpoints
    // ============================================================

    @PostMapping("/orders")
    public ResponseEntity<ApiResponseDto<PurchaseResponseDto>> createPurchaseOrder(
            @RequestBody PurchaseRequestDto requestDto) {
        PurchaseResponseDto response = purchaseService.createPurchaseOrder(requestDto);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponseDto.success("Purchase order created successfully", response));
    }

    @GetMapping("/orders/{id}")
    public ResponseEntity<ApiResponseDto<PurchaseResponseDto>> getPurchaseOrderById(@PathVariable Long id) {
        PurchaseResponseDto response = purchaseService.getPurchaseOrderById(id);
        return ResponseEntity.ok(ApiResponseDto.success("Purchase order fetched successfully", response));
    }

    @GetMapping("/orders/by-po-no/{poNo}")
    public ResponseEntity<ApiResponseDto<PurchaseResponseDto>> getPurchaseOrderByPoNo(@PathVariable String poNo) {
        PurchaseResponseDto response = purchaseService.getPurchaseOrderByPoNo(poNo);
        return ResponseEntity.ok(ApiResponseDto.success("Purchase order fetched successfully", response));
    }

    @GetMapping("/orders")
    public ResponseEntity<ApiResponseDto<Page<PurchaseResponseDto>>> getAllPurchaseOrders(
            @RequestParam(required = false) String status,
            @RequestParam(required = false) Long supplierId,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate,
            @RequestParam(required = false) String search,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "poDate") String sortBy,
            @RequestParam(defaultValue = "desc") String sortDir) {

        Sort sort = sortDir.equalsIgnoreCase("asc") ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(page, size, sort);

        Page<PurchaseResponseDto> response = purchaseService.getAllPurchaseOrders(
                status, supplierId, startDate, endDate, search, pageable);

        return ResponseEntity.ok(ApiResponseDto.success("Purchase orders fetched successfully", response));
    }

    @PutMapping("/orders/{id}")
    public ResponseEntity<ApiResponseDto<PurchaseResponseDto>> updatePurchaseOrder(
            @PathVariable Long id,
            @RequestBody PurchaseRequestDto requestDto) {
        PurchaseResponseDto response = purchaseService.updatePurchaseOrder(id, requestDto);
        return ResponseEntity.ok(ApiResponseDto.success("Purchase order updated successfully", response));
    }

    @PatchMapping("/orders/{id}/status")
    public ResponseEntity<ApiResponseDto<PurchaseResponseDto>> updatePurchaseOrderStatus(
            @PathVariable Long id,
            @RequestParam String status) {
        PurchaseResponseDto response = purchaseService.updatePurchaseOrderStatus(id, status);
        return ResponseEntity.ok(ApiResponseDto.success("Purchase order status updated successfully", response));
    }

    @DeleteMapping("/orders/{id}")
    public ResponseEntity<ApiResponseDto<Void>> deletePurchaseOrder(@PathVariable Long id) {
        purchaseService.deletePurchaseOrder(id);
        return ResponseEntity.ok(ApiResponseDto.success("Purchase order deleted successfully", null));
    }

    // ============================================================
    // Supplier Endpoints
    // ============================================================

    @PostMapping("/suppliers")
    public ResponseEntity<ApiResponseDto<SupplierResponseDto>> createSupplier(
            @RequestBody SupplierRequestDto requestDto) {
        SupplierResponseDto response = purchaseService.createSupplier(requestDto);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponseDto.success("Supplier created successfully", response));
    }

    @GetMapping("/suppliers/{id}")
    public ResponseEntity<ApiResponseDto<SupplierResponseDto>> getSupplierById(@PathVariable Long id) {
        SupplierResponseDto response = purchaseService.getSupplierById(id);
        return ResponseEntity.ok(ApiResponseDto.success("Supplier fetched successfully", response));
    }

    @GetMapping("/suppliers/gstin/{gstin}")
    public ResponseEntity<ApiResponseDto<SupplierResponseDto>> getSupplierByGstin(@PathVariable String gstin) {
        SupplierResponseDto response = purchaseService.getSupplierByGstin(gstin);
        return ResponseEntity.ok(ApiResponseDto.success("Supplier fetched successfully", response));
    }

    @GetMapping("/suppliers")
    public ResponseEntity<ApiResponseDto<Page<SupplierResponseDto>>> getAllSuppliers(
            @RequestParam(required = false) String search,
            @RequestParam(required = false) String status,
            @RequestParam(required = false) String state,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "name") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDir) {

        Sort sort = sortDir.equalsIgnoreCase("desc") ? Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();
        Pageable pageable = PageRequest.of(page, size, sort);

        Page<SupplierResponseDto> response = purchaseService.getAllSuppliers(search, status, state, pageable);

        return ResponseEntity.ok(ApiResponseDto.success("Suppliers fetched successfully", response));
    }

    @GetMapping("/suppliers/active")
    public ResponseEntity<ApiResponseDto<List<SupplierResponseDto>>> getActiveSuppliers() {
        List<SupplierResponseDto> response = purchaseService.getActiveSuppliers();
        return ResponseEntity.ok(ApiResponseDto.success("Active suppliers fetched successfully", response));
    }

    @PutMapping("/suppliers/{id}")
    public ResponseEntity<ApiResponseDto<SupplierResponseDto>> updateSupplier(
            @PathVariable Long id,
            @RequestBody SupplierRequestDto requestDto) {
        SupplierResponseDto response = purchaseService.updateSupplier(id, requestDto);
        return ResponseEntity.ok(ApiResponseDto.success("Supplier updated successfully", response));
    }

    @DeleteMapping("/suppliers/{id}")
    public ResponseEntity<ApiResponseDto<Void>> deleteSupplier(@PathVariable Long id) {
        purchaseService.deleteSupplier(id);
        return ResponseEntity.ok(ApiResponseDto.success("Supplier deleted successfully", null));
    }

    // ============================================================
    // Dashboard Stats Endpoints
    // ============================================================

    @GetMapping("/stats/total")
    public ResponseEntity<ApiResponseDto<Long>> getTotalPurchaseOrders() {
        Long total = purchaseService.getTotalPurchaseOrders();
        return ResponseEntity.ok(ApiResponseDto.success("Total purchase orders fetched successfully", total));
    }

    @GetMapping("/stats/by-status/{status}")
    public ResponseEntity<ApiResponseDto<Long>> getPurchaseOrdersByStatus(@PathVariable String status) {
        Long count = purchaseService.getPurchaseOrdersByStatus(status);
        return ResponseEntity.ok(ApiResponseDto.success("Purchase orders count by status fetched successfully", count));
    }

    @GetMapping("/stats/total-value")
    public ResponseEntity<ApiResponseDto<Double>> getTotalPurchaseValue() {
        Double total = purchaseService.getTotalPurchaseValue();
        return ResponseEntity.ok(ApiResponseDto.success("Total purchase value fetched successfully", total));
    }

    @GetMapping("/stats/value-between")
    public ResponseEntity<ApiResponseDto<Double>> getPurchaseValueBetweenDates(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        Double total = purchaseService.getPurchaseValueBetweenDates(startDate, endDate);
        return ResponseEntity.ok(ApiResponseDto.success("Purchase value between dates fetched successfully", total));
    }

    // ============================================================
    // GST Report Endpoints
    // ============================================================

    @GetMapping("/reports/gstr-1b")
    public ResponseEntity<ApiResponseDto<Gstr1BResponseDto>> generateGstr1B(
            @RequestParam String month,
            @RequestParam String year) {
        Gstr1BResponseDto response = purchaseService.generateGstr1B(month, year);
        return ResponseEntity.ok(ApiResponseDto.success("GSTR-1B report generated successfully", response));
    }

    @GetMapping("/reports/gstr-3b")
    public ResponseEntity<ApiResponseDto<Gstr3BResponseDto>> generateGstr3B(
            @RequestParam String month,
            @RequestParam String year) {
        Gstr3BResponseDto response = purchaseService.generateGstr3B(month, year);
        return ResponseEntity.ok(ApiResponseDto.success("GSTR-3B report generated successfully", response));
    }
}
