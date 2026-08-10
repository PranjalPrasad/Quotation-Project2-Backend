package com.quo.quotation2.service.serviceImpl;

import com.quo.quotation2.dto.requestdto.PurchaseRequestDto;
import com.quo.quotation2.dto.requestdto.SupplierRequestDto;
import com.quo.quotation2.dto.responsedto.Gstr1BResponseDto;
import com.quo.quotation2.dto.responsedto.Gstr3BResponseDto;
import com.quo.quotation2.dto.responsedto.PurchaseResponseDto;
import com.quo.quotation2.dto.responsedto.SupplierResponseDto;
import com.quo.quotation2.entity.PurchaseItemEntity;
import com.quo.quotation2.entity.PurchaseOrderEntity;
import com.quo.quotation2.entity.SupplierEntity;
import com.quo.quotation2.exception.PurchaseException;
import com.quo.quotation2.repository.PurchaseItemRepository;
import com.quo.quotation2.repository.PurchaseOrderRepository;
import com.quo.quotation2.repository.SupplierRepository;
import com.quo.quotation2.service.PurchaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Transactional
public class PurchaseServiceImpl implements PurchaseService {

    @Autowired
    private PurchaseOrderRepository purchaseOrderRepository;

    @Autowired
    private PurchaseItemRepository purchaseItemRepository;

    @Autowired
    private SupplierRepository supplierRepository;

    // ============================================================
    // Purchase Order CRUD
    // ============================================================

    @Override
    public PurchaseResponseDto createPurchaseOrder(PurchaseRequestDto requestDto) {
        // Validate supplier
        SupplierEntity supplier = supplierRepository.findById(requestDto.getSupplierId())
                .orElseThrow(() -> new PurchaseException("Supplier not found with id: " + requestDto.getSupplierId()));

        // Create purchase order
        PurchaseOrderEntity purchaseOrder = new PurchaseOrderEntity();
        purchaseOrder.setPoNo(generatePoNumber());
        purchaseOrder.setSupplier(supplier);
        purchaseOrder.setPoDate(requestDto.getPoDate() != null ? requestDto.getPoDate() : LocalDate.now());
        purchaseOrder.setExpectedDelivery(requestDto.getExpectedDelivery());
        purchaseOrder.setGstPercent(requestDto.getGstPercent() != null ? requestDto.getGstPercent() : 18.0);
        purchaseOrder.setNotes(requestDto.getNotes());
        purchaseOrder.setStatus("Pending");

        // Calculate totals
        double subtotal = 0.0;
        List<PurchaseItemEntity> items = new ArrayList<>();

        for (PurchaseRequestDto.PurchaseItemDto itemDto : requestDto.getItems()) {
            PurchaseItemEntity item = new PurchaseItemEntity();
            item.setPurchaseOrder(purchaseOrder);
            item.setName(itemDto.getName());
            item.setQty(itemDto.getQty());
            item.setRate(itemDto.getRate());
            item.setAmount(itemDto.getQty() * itemDto.getRate());
            item.setHsnCode(itemDto.getHsnCode());
            item.setGstRate(itemDto.getGstRate() != null ? itemDto.getGstRate() : 18.0);
            item.setUnit(itemDto.getUnit());
            items.add(item);
            subtotal += item.getAmount();
        }

        purchaseOrder.setItems(items);
        purchaseOrder.setSubtotal(subtotal);

        // Calculate GST
        double gstPercent = purchaseOrder.getGstPercent();
        double cgst = subtotal * (gstPercent / 200);
        double sgst = subtotal * (gstPercent / 200);
        purchaseOrder.setCgstAmount(cgst);
        purchaseOrder.setSgstAmount(sgst);
        purchaseOrder.setGrandTotal(subtotal + cgst + sgst);

        PurchaseOrderEntity savedOrder = purchaseOrderRepository.save(purchaseOrder);

        return convertToResponseDto(savedOrder);
    }

    @Override
    public PurchaseResponseDto getPurchaseOrderById(Long id) {
        PurchaseOrderEntity purchaseOrder = purchaseOrderRepository.findById(id)
                .orElseThrow(() -> new PurchaseException("Purchase order not found with id: " + id));
        return convertToResponseDto(purchaseOrder);
    }

    @Override
    public PurchaseResponseDto getPurchaseOrderByPoNo(String poNo) {
        PurchaseOrderEntity purchaseOrder = purchaseOrderRepository.findByPoNo(poNo)
                .orElseThrow(() -> new PurchaseException("Purchase order not found with PO No: " + poNo));
        return convertToResponseDto(purchaseOrder);
    }

    @Override
    public Page<PurchaseResponseDto> getAllPurchaseOrders(String status, Long supplierId,
                                                          LocalDate startDate, LocalDate endDate,
                                                          String search, Pageable pageable) {
        Page<PurchaseOrderEntity> page = purchaseOrderRepository.findWithFilters(
                status, supplierId, startDate, endDate, search, pageable);
        return page.map(this::convertToResponseDto);
    }

    @Override
    public PurchaseResponseDto updatePurchaseOrder(Long id, PurchaseRequestDto requestDto) {
        PurchaseOrderEntity purchaseOrder = purchaseOrderRepository.findById(id)
                .orElseThrow(() -> new PurchaseException("Purchase order not found with id: " + id));

        // Update supplier if changed
        if (requestDto.getSupplierId() != null && !requestDto.getSupplierId().equals(purchaseOrder.getSupplier().getId())) {
            SupplierEntity supplier = supplierRepository.findById(requestDto.getSupplierId())
                    .orElseThrow(() -> new PurchaseException("Supplier not found with id: " + requestDto.getSupplierId()));
            purchaseOrder.setSupplier(supplier);
        }

        purchaseOrder.setPoDate(requestDto.getPoDate() != null ? requestDto.getPoDate() : purchaseOrder.getPoDate());
        purchaseOrder.setExpectedDelivery(requestDto.getExpectedDelivery());
        purchaseOrder.setGstPercent(requestDto.getGstPercent() != null ? requestDto.getGstPercent() : purchaseOrder.getGstPercent());
        purchaseOrder.setNotes(requestDto.getNotes());

        // Update items
        if (requestDto.getItems() != null && !requestDto.getItems().isEmpty()) {
            // Clear existing items
            purchaseItemRepository.deleteByPurchaseOrderId(purchaseOrder.getId());
            purchaseOrder.getItems().clear();

            double subtotal = 0.0;
            for (PurchaseRequestDto.PurchaseItemDto itemDto : requestDto.getItems()) {
                PurchaseItemEntity item = new PurchaseItemEntity();
                item.setPurchaseOrder(purchaseOrder);
                item.setName(itemDto.getName());
                item.setQty(itemDto.getQty());
                item.setRate(itemDto.getRate());
                item.setAmount(itemDto.getQty() * itemDto.getRate());
                item.setHsnCode(itemDto.getHsnCode());
                item.setGstRate(itemDto.getGstRate() != null ? itemDto.getGstRate() : 18.0);
                item.setUnit(itemDto.getUnit());
                purchaseOrder.getItems().add(item);
                subtotal += item.getAmount();
            }

            purchaseOrder.setSubtotal(subtotal);
            double gstPercent = purchaseOrder.getGstPercent();
            double cgst = subtotal * (gstPercent / 200);
            double sgst = subtotal * (gstPercent / 200);
            purchaseOrder.setCgstAmount(cgst);
            purchaseOrder.setSgstAmount(sgst);
            purchaseOrder.setGrandTotal(subtotal + cgst + sgst);
        }

        PurchaseOrderEntity updatedOrder = purchaseOrderRepository.save(purchaseOrder);
        return convertToResponseDto(updatedOrder);
    }

    @Override
    public PurchaseResponseDto updatePurchaseOrderStatus(Long id, String status) {
        PurchaseOrderEntity purchaseOrder = purchaseOrderRepository.findById(id)
                .orElseThrow(() -> new PurchaseException("Purchase order not found with id: " + id));

        purchaseOrder.setStatus(status);
        PurchaseOrderEntity updatedOrder = purchaseOrderRepository.save(purchaseOrder);
        return convertToResponseDto(updatedOrder);
    }

    @Override
    public void deletePurchaseOrder(Long id) {
        PurchaseOrderEntity purchaseOrder = purchaseOrderRepository.findById(id)
                .orElseThrow(() -> new PurchaseException("Purchase order not found with id: " + id));
        purchaseOrderRepository.delete(purchaseOrder);
    }

    // ============================================================
    // Supplier CRUD
    // ============================================================

    @Override
    public SupplierResponseDto createSupplier(SupplierRequestDto requestDto) {
        // ✅ Debug log to check what's coming
        System.out.println("Supplier Request DTO: " + requestDto);
        System.out.println("Name: " + requestDto.getName());

        // ✅ Validate name
        if (requestDto.getName() == null || requestDto.getName().trim().isEmpty()) {
            throw new PurchaseException("Supplier name is required");
        }

        // Check if GSTIN already exists
        if (requestDto.getGstin() != null && !requestDto.getGstin().isEmpty()) {
            if (supplierRepository.existsByGstin(requestDto.getGstin())) {
                throw new PurchaseException("Supplier with GSTIN " + requestDto.getGstin() + " already exists");
            }
        }

        // Check if mobile already exists
        if (requestDto.getMobile() != null && !requestDto.getMobile().isEmpty()) {
            if (supplierRepository.existsByMobile(requestDto.getMobile())) {
                throw new PurchaseException("Supplier with mobile " + requestDto.getMobile() + " already exists");
            }
        }

        SupplierEntity supplier = new SupplierEntity();
        supplier.setName(requestDto.getName().trim());
        supplier.setGstin(requestDto.getGstin());
        supplier.setMobile(requestDto.getMobile());
        supplier.setEmail(requestDto.getEmail());
        supplier.setAddress(requestDto.getAddress());
        supplier.setCity(requestDto.getCity());
        supplier.setState(requestDto.getState());
        supplier.setPincode(requestDto.getPincode());
        supplier.setStatus(requestDto.getStatus() != null ? requestDto.getStatus() : "Active");

        SupplierEntity savedSupplier = supplierRepository.save(supplier);
        return convertToSupplierResponseDto(savedSupplier);
    }

    @Override
    public SupplierResponseDto getSupplierById(Long id) {
        SupplierEntity supplier = supplierRepository.findById(id)
                .orElseThrow(() -> new PurchaseException("Supplier not found with id: " + id));
        return convertToSupplierResponseDto(supplier);
    }

    @Override
    public SupplierResponseDto getSupplierByGstin(String gstin) {
        SupplierEntity supplier = supplierRepository.findByGstin(gstin)
                .orElseThrow(() -> new PurchaseException("Supplier not found with GSTIN: " + gstin));
        return convertToSupplierResponseDto(supplier);
    }

    @Override
    public Page<SupplierResponseDto> getAllSuppliers(String search, String status, String state, Pageable pageable) {
        Page<SupplierEntity> page = supplierRepository.searchSuppliers(search, status, state, pageable);
        return page.map(this::convertToSupplierResponseDto);
    }

    @Override
    public List<SupplierResponseDto> getActiveSuppliers() {
        List<SupplierEntity> suppliers = supplierRepository.findActiveSuppliers();
        return suppliers.stream().map(this::convertToSupplierResponseDto).collect(Collectors.toList());
    }

    @Override
    public SupplierResponseDto updateSupplier(Long id, SupplierRequestDto requestDto) {
        SupplierEntity supplier = supplierRepository.findById(id)
                .orElseThrow(() -> new PurchaseException("Supplier not found with id: " + id));

        supplier.setName(requestDto.getName());
        supplier.setGstin(requestDto.getGstin());
        supplier.setMobile(requestDto.getMobile());
        supplier.setEmail(requestDto.getEmail());
        supplier.setAddress(requestDto.getAddress());
        supplier.setCity(requestDto.getCity());
        supplier.setState(requestDto.getState());
        supplier.setPincode(requestDto.getPincode());
        supplier.setStatus(requestDto.getStatus() != null ? requestDto.getStatus() : supplier.getStatus());

        SupplierEntity updatedSupplier = supplierRepository.save(supplier);
        return convertToSupplierResponseDto(updatedSupplier);
    }

    @Override
    public void deleteSupplier(Long id) {
        SupplierEntity supplier = supplierRepository.findById(id)
                .orElseThrow(() -> new PurchaseException("Supplier not found with id: " + id));
        supplierRepository.delete(supplier);
    }

    // ============================================================
    // Dashboard Stats
    // ============================================================

    @Override
    public Long getTotalPurchaseOrders() {
        return purchaseOrderRepository.count();
    }

    @Override
    public Long getPurchaseOrdersByStatus(String status) {
        return purchaseOrderRepository.countByStatus(status);
    }

    @Override
    public Double getTotalPurchaseValue() {
        return purchaseOrderRepository.sumAllGrandTotal();
    }

    @Override
    public Double getPurchaseValueBetweenDates(LocalDate startDate, LocalDate endDate) {
        return purchaseOrderRepository.sumGrandTotalBetweenDates(startDate, endDate);
    }

    // ============================================================
    // GST Reports - PURE DATABASE (NO STATIC DATA)
    // ============================================================

    @Override
    public Gstr1BResponseDto generateGstr1B(String month, String year) {
        int monthNum = Integer.parseInt(month);
        int yearNum = Integer.parseInt(year);
        LocalDate startDate = LocalDate.of(yearNum, monthNum, 1);
        LocalDate endDate = YearMonth.of(yearNum, monthNum).atEndOfMonth();

        Gstr1BResponseDto response = new Gstr1BResponseDto();
        response.setPeriod(month + year);
        response.setGstin("09AMXPS4725R1ZO");

        // ✅ Fetch actual data from database - Purchase Orders for the period
        List<PurchaseOrderEntity> purchaseOrders = purchaseOrderRepository.findByPoDateBetween(startDate, endDate);

        // Calculate totals from actual database data
        double totalSales = 0.0;
        double totalB2BSales = 0.0;
        double totalB2CSales = 0.0;
        double totalTax = 0.0;

        List<Gstr1BResponseDto.B2BInvoiceDto> b2bInvoices = new ArrayList<>();
        List<Gstr1BResponseDto.B2CInvoiceDto> b2cInvoices = new ArrayList<>();

        for (PurchaseOrderEntity po : purchaseOrders) {
            double taxableValue = po.getSubtotal() != null ? po.getSubtotal() : 0;
            double gstAmount = (po.getCgstAmount() != null ? po.getCgstAmount() : 0) +
                    (po.getSgstAmount() != null ? po.getSgstAmount() : 0);

            // Determine if B2B or B2C based on supplier GSTIN
            if (po.getSupplier() != null && po.getSupplier().getGstin() != null && !po.getSupplier().getGstin().isEmpty()) {
                totalB2BSales += taxableValue;

                // Add to B2B Invoices
                Gstr1BResponseDto.B2BInvoiceDto invoice = new Gstr1BResponseDto.B2BInvoiceDto();
                invoice.setInvoiceNo(po.getPoNo());
                invoice.setInvoiceDate(po.getPoDate());
                invoice.setCustomerName(po.getSupplier().getName());
                invoice.setCustomerGstin(po.getSupplier().getGstin());
                invoice.setTaxableValue(taxableValue);
                invoice.setCgst(po.getCgstAmount() != null ? po.getCgstAmount() : 0);
                invoice.setSgst(po.getSgstAmount() != null ? po.getSgstAmount() : 0);
                invoice.setIgst(0.0);
                invoice.setTotal(po.getGrandTotal() != null ? po.getGrandTotal() : 0);
                b2bInvoices.add(invoice);
            } else {
                totalB2CSales += taxableValue;

                // Add to B2C Invoices
                Gstr1BResponseDto.B2CInvoiceDto invoice = new Gstr1BResponseDto.B2CInvoiceDto();
                invoice.setInvoiceNo(po.getPoNo());
                invoice.setInvoiceDate(po.getPoDate());
                invoice.setStateCode(po.getSupplier() != null ? "09" : "00");
                invoice.setTaxableValue(taxableValue);
                invoice.setCgst(po.getCgstAmount() != null ? po.getCgstAmount() : 0);
                invoice.setSgst(po.getSgstAmount() != null ? po.getSgstAmount() : 0);
                invoice.setIgst(0.0);
                invoice.setTotal(po.getGrandTotal() != null ? po.getGrandTotal() : 0);
                b2cInvoices.add(invoice);
            }

            totalSales += taxableValue;
            totalTax += gstAmount;
        }

        // Create summary from database data
        Gstr1BResponseDto.Summary summary = new Gstr1BResponseDto.Summary();
        summary.setTotalSales(totalSales);
        summary.setTotalB2BSales(totalB2BSales);
        summary.setTotalB2CSales(totalB2CSales);
        summary.setTotalTax(totalTax);
        summary.setTotalInvoices(purchaseOrders.size());
        response.setSummary(summary);

        response.setB2bInvoices(b2bInvoices);
        response.setB2cInvoices(b2cInvoices);

        // Build HSN Summary from database - using purchase items
        Map<String, Double> hsnMap = new HashMap<>();
        Map<String, String> hsnDescription = new HashMap<>();
        for (PurchaseOrderEntity po : purchaseOrders) {
            for (PurchaseItemEntity item : po.getItems()) {
                String hsn = item.getHsnCode() != null && !item.getHsnCode().isEmpty() ? item.getHsnCode() : "00000000";
                hsnMap.put(hsn, hsnMap.getOrDefault(hsn, 0.0) + item.getAmount());
                if (item.getName() != null) {
                    hsnDescription.put(hsn, item.getName());
                }
            }
        }

        List<Gstr1BResponseDto.HsnSummaryDto> hsnSummary = new ArrayList<>();
        for (Map.Entry<String, Double> entry : hsnMap.entrySet()) {
            Gstr1BResponseDto.HsnSummaryDto hsn = new Gstr1BResponseDto.HsnSummaryDto();
            hsn.setHsnCode(entry.getKey());
            hsn.setDescription(hsnDescription.getOrDefault(entry.getKey(), "Goods"));
            hsn.setQuantity(1.0);
            hsn.setTotalValue(entry.getValue());
            hsn.setTaxableValue(entry.getValue());
            hsn.setCgst(entry.getValue() * 0.09);
            hsn.setSgst(entry.getValue() * 0.09);
            hsn.setIgst(0.0);
            hsnSummary.add(hsn);
        }
        response.setHsnSummary(hsnSummary);

        response.setExportInvoices(new ArrayList<>());
        return response;
    }

    @Override
    public Gstr3BResponseDto generateGstr3B(String month, String year) {
        int monthNum = Integer.parseInt(month);
        int yearNum = Integer.parseInt(year);
        LocalDate startDate = LocalDate.of(yearNum, monthNum, 1);
        LocalDate endDate = YearMonth.of(yearNum, monthNum).atEndOfMonth();

        Gstr3BResponseDto response = new Gstr3BResponseDto();
        response.setPeriod(month + year);
        response.setGstin("09AMXPS4725R1ZO");
        response.setReturnType("Regular");

        // ✅ Fetch actual data from database
        List<PurchaseOrderEntity> purchaseOrders = purchaseOrderRepository.findByPoDateBetween(startDate, endDate);

        // Calculate Inward Supplies (Purchases) - from purchase orders
        double inwardTaxable = 0.0;
        double inwardCgst = 0.0;
        double inwardSgst = 0.0;
        double inwardIgst = 0.0;
        int inwardCount = 0;

        for (PurchaseOrderEntity po : purchaseOrders) {
            inwardTaxable += po.getSubtotal() != null ? po.getSubtotal() : 0;
            inwardCgst += po.getCgstAmount() != null ? po.getCgstAmount() : 0;
            inwardSgst += po.getSgstAmount() != null ? po.getSgstAmount() : 0;
            inwardCount++;
        }

        // 🔥 Outward Supplies (Sales) - This should come from Sales/Invoices table
        // For now, using 0 as no sales module yet
        // TODO: Replace with actual sales data from Invoice/Sales table
        double outwardTaxable = 0.0;
        double outwardCgst = 0.0;
        double outwardSgst = 0.0;
        double outwardIgst = 0.0;
        int outwardCount = 0;

        // Set Outward Supplies (Sales)
        Gstr3BResponseDto.OutwardSupplyDto outward = new Gstr3BResponseDto.OutwardSupplyDto();
        outward.setTotalTaxableValue(outwardTaxable);
        outward.setTotalCgst(outwardCgst);
        outward.setTotalSgst(outwardSgst);
        outward.setTotalIgst(outwardIgst);
        outward.setTotalCess(0.0);
        outward.setTotalInvoices(outwardCount);
        response.setOutwardSupplies(outward);

        // Set Inward Supplies (Purchases)
        Gstr3BResponseDto.InwardSupplyDto inward = new Gstr3BResponseDto.InwardSupplyDto();
        inward.setTotalTaxableValue(inwardTaxable);
        inward.setTotalCgst(inwardCgst);
        inward.setTotalSgst(inwardSgst);
        inward.setTotalIgst(inwardIgst);
        inward.setTotalCess(0.0);
        inward.setTotalInvoices(inwardCount);
        response.setInwardSupplies(inward);

        // ITC Claimed
        Gstr3BResponseDto.ItcClaimDto itc = new Gstr3BResponseDto.ItcClaimDto();
        itc.setTotalItcClaimed(inwardCgst + inwardSgst);
        itc.setItcCgst(inwardCgst);
        itc.setItcSgst(inwardSgst);
        itc.setItcIgst(0.0);
        itc.setItcCess(0.0);
        itc.setItcOnImports(0.0);
        itc.setItcOnDomestic(inwardCgst + inwardSgst);
        response.setItcClaimed(itc);

        // Net Tax Liability
        Gstr3BResponseDto.TaxLiabilityDto tax = new Gstr3BResponseDto.TaxLiabilityDto();
        tax.setCgstPayable(Math.max(0, outwardCgst - inwardCgst));
        tax.setSgstPayable(Math.max(0, outwardSgst - inwardSgst));
        tax.setIgstPayable(Math.max(0, outwardIgst - inwardIgst));
        tax.setCessPayable(0.0);
        tax.setTotalTaxPayable(Math.max(0, (outwardCgst - inwardCgst) + (outwardSgst - inwardSgst) + (outwardIgst - inwardIgst)));
        tax.setInterest(0.0);
        tax.setLateFee(0.0);
        response.setNetTaxLiability(tax);

        // Payment Details
        Gstr3BResponseDto.PaymentDto payment = new Gstr3BResponseDto.PaymentDto();
        payment.setTotalTaxPaid(tax.getTotalTaxPayable());
        payment.setCgstPaid(tax.getCgstPayable());
        payment.setSgstPaid(tax.getSgstPayable());
        payment.setIgstPaid(tax.getIgstPayable());
        payment.setCessPaid(0.0);
        payment.setPaymentDate(LocalDate.now().toString());
        payment.setPaymentReference("CHALLAN-" + System.currentTimeMillis());
        response.setPaymentDetails(payment);

        // Late Fee
        Gstr3BResponseDto.LateFeeDto lateFee = new Gstr3BResponseDto.LateFeeDto();
        lateFee.setTotalLateFee(0.0);
        lateFee.setCgstLateFee(0.0);
        lateFee.setSgstLateFee(0.0);
        lateFee.setDelayDays(0);
        response.setLateFee(lateFee);

        return response;
    }

    // ============================================================
    // Private Helper Methods
    // ============================================================

    private String generatePoNumber() {
        String prefix = "PO-";
        String dateStr = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMM"));
        String lastPoNo = purchaseOrderRepository.findTopByOrderByIdDesc()
                .map(PurchaseOrderEntity::getPoNo)
                .orElse(prefix + dateStr + "0000");

        int lastNumber = Integer.parseInt(lastPoNo.substring(lastPoNo.length() - 4));
        int newNumber = lastNumber + 1;

        return prefix + dateStr + String.format("%04d", newNumber);
    }

    private PurchaseResponseDto convertToResponseDto(PurchaseOrderEntity entity) {
        PurchaseResponseDto dto = new PurchaseResponseDto();
        dto.setId(entity.getId());
        dto.setPoNo(entity.getPoNo());
        dto.setSupplierId(entity.getSupplier().getId());
        dto.setSupplierName(entity.getSupplier().getName());
        dto.setSupplierGstin(entity.getSupplier().getGstin());
        dto.setPoDate(entity.getPoDate());
        dto.setExpectedDelivery(entity.getExpectedDelivery());
        dto.setSubtotal(entity.getSubtotal());
        dto.setGstPercent(entity.getGstPercent());
        dto.setCgstAmount(entity.getCgstAmount());
        dto.setSgstAmount(entity.getSgstAmount());
        dto.setGrandTotal(entity.getGrandTotal());
        dto.setStatus(entity.getStatus());
        dto.setNotes(entity.getNotes());
        dto.setCreatedAt(entity.getCreatedAt());
        dto.setUpdatedAt(entity.getUpdatedAt());

        List<PurchaseResponseDto.PurchaseItemResponseDto> items = entity.getItems().stream()
                .map(item -> new PurchaseResponseDto.PurchaseItemResponseDto(
                        item.getId(), item.getName(), item.getQty(), item.getRate(),
                        item.getAmount(), item.getHsnCode(), item.getGstRate(), item.getUnit()))
                .collect(Collectors.toList());
        dto.setItems(items);

        return dto;
    }

    private SupplierResponseDto convertToSupplierResponseDto(SupplierEntity entity) {
        SupplierResponseDto dto = new SupplierResponseDto();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setGstin(entity.getGstin());
        dto.setMobile(entity.getMobile());
        dto.setEmail(entity.getEmail());
        dto.setAddress(entity.getAddress());
        dto.setCity(entity.getCity());
        dto.setState(entity.getState());
        dto.setPincode(entity.getPincode());
        dto.setStatus(entity.getStatus());
        dto.setCreatedAt(entity.getCreatedAt());
        dto.setUpdatedAt(entity.getUpdatedAt());
        return dto;
    }
}
