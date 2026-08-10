package com.quo.quotation2.service;
import com.quo.quotation2.dto.requestdto.PurchaseRequestDto;
import com.quo.quotation2.dto.requestdto.SupplierRequestDto;
import com.quo.quotation2.dto.responsedto.Gstr1BResponseDto;
import com.quo.quotation2.dto.responsedto.Gstr3BResponseDto;
import com.quo.quotation2.dto.responsedto.PurchaseResponseDto;
import com.quo.quotation2.dto.responsedto.SupplierResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.List;

public interface PurchaseService {

    // ============================================================
    // Purchase Order CRUD
    // ============================================================

    PurchaseResponseDto createPurchaseOrder(PurchaseRequestDto requestDto);

    PurchaseResponseDto getPurchaseOrderById(Long id);

    PurchaseResponseDto getPurchaseOrderByPoNo(String poNo);

    Page<PurchaseResponseDto> getAllPurchaseOrders(String status, Long supplierId,
                                                   LocalDate startDate, LocalDate endDate,
                                                   String search, Pageable pageable);

    PurchaseResponseDto updatePurchaseOrder(Long id, PurchaseRequestDto requestDto);

    PurchaseResponseDto updatePurchaseOrderStatus(Long id, String status);

    void deletePurchaseOrder(Long id);

    // ============================================================
    // Supplier CRUD
    // ============================================================

    SupplierResponseDto createSupplier(SupplierRequestDto requestDto);

    SupplierResponseDto getSupplierById(Long id);

    SupplierResponseDto getSupplierByGstin(String gstin);

    Page<SupplierResponseDto> getAllSuppliers(String search, String status, String state, Pageable pageable);

    List<SupplierResponseDto> getActiveSuppliers();

    SupplierResponseDto updateSupplier(Long id, SupplierRequestDto requestDto);

    void deleteSupplier(Long id);

    // ============================================================
    // Dashboard Stats
    // ============================================================

    Long getTotalPurchaseOrders();

    Long getPurchaseOrdersByStatus(String status);

    Double getTotalPurchaseValue();

    Double getPurchaseValueBetweenDates(LocalDate startDate, LocalDate endDate);

    // ============================================================
    // GST Reports
    // ============================================================

    Gstr1BResponseDto generateGstr1B(String month, String year);

    Gstr3BResponseDto generateGstr3B(String month, String year);
}
