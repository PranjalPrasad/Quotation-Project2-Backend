package com.quo.quotation2.service.serviceImpl;

import com.fasterxml.jackson.databind.ObjectMapper;

import com.quo.quotation2.dto.CustomerDto;
import com.quo.quotation2.dto.requestdto.*;
import com.quo.quotation2.dto.responsedto.PageResponseDto;
import com.quo.quotation2.dto.responsedto.QuotationListResponseDto;
import com.quo.quotation2.dto.responsedto.QuotationResponseDto;
import com.quo.quotation2.entity.*;
import com.quo.quotation2.exception.QuotationException;
import com.quo.quotation2.repository.*;
import com.quo.quotation2.service.QuotationService;
import com.quo.quotation2.util.JwtUtil;
import com.quo.quotation2.util.NumberToWordsUtil;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional
public class QuotationServiceImpl implements QuotationService {

    private static final String COMPANY_STATE = "Uttar Pradesh";
    private static final String QUOTE_PREFIX = "SQ-";
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    private final QuotationRepository quotationRepository;
    private final QuotationItemRepository quotationItemRepository;
    private final QuotationPaymentTermsRepository paymentTermsRepository;
    private final CustomerRepository customerRepository;
    private final ProductRepository productRepository;
    private final QuotationStatusLogRepository statusLogRepository;
    private final JwtUtil jwtUtil;
    private final ObjectMapper objectMapper;

    public QuotationServiceImpl(QuotationRepository quotationRepository,
                                QuotationItemRepository quotationItemRepository,
                                QuotationPaymentTermsRepository paymentTermsRepository,
                                CustomerRepository customerRepository,
                                ProductRepository productRepository,
                                QuotationStatusLogRepository statusLogRepository,
                                JwtUtil jwtUtil,
                                ObjectMapper objectMapper) {
        this.quotationRepository = quotationRepository;
        this.quotationItemRepository = quotationItemRepository;
        this.paymentTermsRepository = paymentTermsRepository;
        this.customerRepository = customerRepository;
        this.productRepository = productRepository;
        this.statusLogRepository = statusLogRepository;
        this.jwtUtil = jwtUtil;
        this.objectMapper = objectMapper;
    }

    @Override
    public QuotationResponseDto createQuotation(QuotationRequestDto requestDto, String authHeader) {
        String createdBy = extractEmailFromToken(authHeader);

        Customer customer = getOrCreateCustomer(requestDto.getCustomer());
        String quoteNo = generateQuoteNumber();
        validatePaymentTerms(requestDto.getPaymentTerms());

        if (requestDto.getCustomer().getGstin() != null && !requestDto.getCustomer().getGstin().isEmpty()) {
            validateGstin(requestDto.getCustomer().getGstin());
        }

        QuotationCalculations calc = calculateTotals(requestDto);

        Quotation quotation = new Quotation();
        quotation.setQuoteNo(quoteNo);
        quotation.setQuoteDate(parseDate(requestDto.getQuoteDate()));
        quotation.setValidUntil(parseDate(requestDto.getValidUntil()));
        quotation.setStatus("Pending");
        quotation.setCustomer(customer);
        quotation.setIsInterState(calc.isInterState);
        quotation.setSiteType(requestDto.getSiteType());
        quotation.setDeliveryTimeline(requestDto.getDeliveryTimeline());
        quotation.setItemsSubtotal(calc.itemsSubtotal);
        quotation.setTotalPowerHP(calc.totalPowerHP);
        quotation.setTotalPowerKW(calc.totalPowerKW);
        quotation.setTransportCharge(calc.transport);
        quotation.setLoadingCharge(calc.loading);
        quotation.setOtherChargeLabel(calc.otherLabel);
        quotation.setOtherCharge(calc.other);
        quotation.setDiscountType(requestDto.getDiscountType() != null ? requestDto.getDiscountType() : "percent");
        quotation.setDiscountValue(requestDto.getDiscountValue() != null ? requestDto.getDiscountValue() : 0.0);
        quotation.setDiscountAmount(calc.discountAmount);
        quotation.setTaxableAmount(calc.taxableAmount);
        quotation.setGstPercent(calc.gstPercent);
        quotation.setCgstPercent(calc.cgstPercent);
        quotation.setCgstAmount(calc.cgstAmount);
        quotation.setSgstPercent(calc.sgstPercent);
        quotation.setSgstAmount(calc.sgstAmount);
        quotation.setIgstPercent(calc.igstPercent);
        quotation.setIgstAmount(calc.igstAmount);
        quotation.setGrandTotal(calc.grandTotal);
        quotation.setGrandTotalWords(NumberToWordsUtil.convert(calc.grandTotal));
        quotation.setPaymentType(requestDto.getPaymentType() != null ? requestDto.getPaymentType() : "installment");

        if (requestDto.getBankDetails() != null) {
            quotation.setBankAccountName(requestDto.getBankDetails().getAccountName());
            quotation.setBankName(requestDto.getBankDetails().getBankName());
            quotation.setBankAccountNumber(requestDto.getBankDetails().getAccountNumber());
            quotation.setBankIfsc(requestDto.getBankDetails().getIfscCode());
            quotation.setBankBranch(requestDto.getBankDetails().getBranch());
        }

        if (requestDto.getTermsAndConditions() != null) {
            quotation.setTermsTemplateVersion(requestDto.getTermsAndConditions().getTemplateVersion());
            try {
                quotation.setTermsCategoriesApplied(
                        objectMapper.writeValueAsString(requestDto.getTermsAndConditions().getCategoriesApplied())
                );
            } catch (Exception e) {
                quotation.setTermsCategoriesApplied("[]");
            }
        }

        quotation.setCreatedBy(createdBy);
        quotation.setCreatedAt(LocalDateTime.now());
        quotation.setUpdatedAt(LocalDateTime.now());
        quotation.setConvertedToInvoice(false);

        Quotation savedQuotation = quotationRepository.save(quotation);

        List<QuotationItem> items = new ArrayList<>();
        for (QuotationItemDto itemDto : requestDto.getItems()) {
            QuotationItem item = new QuotationItem();
            item.setQuotation(savedQuotation);
            item.setProductId(itemDto.getProductId());
            item.setName(itemDto.getName());
            item.setSectionCode(itemDto.getSectionCode());
            item.setHsnCode(itemDto.getHsnCode());
            item.setGstRate(itemDto.getGstRate() != null ? itemDto.getGstRate() : 18.0);
            item.setQty(itemDto.getQty());
            item.setUnit(itemDto.getUnit() != null ? itemDto.getUnit() : "Set");
            item.setRate(itemDto.getRate());
            item.setAmount(itemDto.getAmount());
            item.setPowerHP(itemDto.getPowerHP());
            item.setPowerKW(itemDto.getPowerKW());
            item.setInCustomerScope(itemDto.getInCustomerScope() != null ? itemDto.getInCustomerScope() : false);
            item.setShedSize(itemDto.getShedSize());
            item.setLabor(itemDto.getLabor());
            item.setProduction(itemDto.getProduction());
            item.setPower(itemDto.getPower());

            // ✅ FIX: product is looked up by SKU (ProductEntity.sku), not a "product code".
            // The old code referenced a non-existent `Product` type, `findByProductCode(...)`,
            // and `getImage()` — none of which exist on the current ProductEntity/ProductRepository.
            if (itemDto.getProductId() != null) {
                Optional<ProductEntity> product = productRepository.findBySku(itemDto.getProductId());
                if (product.isPresent() && product.get().getThumbnailImage() != null) {
                    item.setImage(product.get().getThumbnailImage());
                }
            }

            items.add(item);
        }
        quotationItemRepository.saveAll(items);

        QuotationPaymentTerms paymentTerms = new QuotationPaymentTerms();
        paymentTerms.setQuotation(savedQuotation);
        paymentTerms.setAdvancePercent(requestDto.getPaymentTerms().getAdvancePercent());
        paymentTerms.setBeforeDispatchPercent(requestDto.getPaymentTerms().getBeforeDispatchPercent());
        paymentTerms.setOnDeliveryPercent(requestDto.getPaymentTerms().getOnDeliveryPercent());
        paymentTerms.setBalancePercent(requestDto.getPaymentTerms().getBalancePercent());
        paymentTerms.setTotalPercent(100.0);
        paymentTermsRepository.save(paymentTerms);

        logStatusChange(savedQuotation.getId(), null, "Pending", createdBy);

        return buildResponse(savedQuotation);
    }

    @Override
    public QuotationResponseDto getQuotation(Long id) {
        Quotation quotation = findQuotationById(id);
        return buildResponse(quotation);
    }

    @Override
    public QuotationResponseDto getQuotationByQuoteNo(String quoteNo) {
        Quotation quotation = quotationRepository.findByQuoteNo(quoteNo)
                .orElseThrow(() -> new QuotationException("Quotation not found with number: " + quoteNo));
        return buildResponse(quotation);
    }

    @Override
    public PageResponseDto<QuotationListResponseDto> getQuotations(String status, String dateFrom, String dateTo,
                                                                   Long customerId, Pageable pageable) {
        LocalDateTime fromDate = dateFrom != null ? LocalDate.parse(dateFrom).atStartOfDay() : null;
        LocalDateTime toDate = dateTo != null ? LocalDate.parse(dateTo).atTime(23, 59, 59) : null;

        Page<Quotation> page = quotationRepository.findWithFilters(status, fromDate, toDate, customerId, pageable);

        List<QuotationListResponseDto> content = page.getContent().stream()
                .map(q -> {
                    QuotationListResponseDto dto = new QuotationListResponseDto();
                    dto.setId(q.getId());
                    dto.setQuoteNo(q.getQuoteNo());
                    dto.setQuoteDate(q.getQuoteDate());
                    dto.setValidUntil(q.getValidUntil());
                    dto.setStatus(q.getStatus());
                    dto.setCustomerName(q.getCustomer() != null ? q.getCustomer().getName() : null);
                    dto.setCustomerMobile(q.getCustomer() != null ? q.getCustomer().getMobilePrimary() : null);
                    dto.setGrandTotal(q.getGrandTotal());
                    dto.setConvertedToInvoice(q.getConvertedToInvoice());
                    return dto;
                })
                .collect(Collectors.toList());

        PageResponseDto<QuotationListResponseDto> response = new PageResponseDto<>();
        response.setContent(content);
        response.setPageNumber(page.getNumber());
        response.setPageSize(page.getSize());
        response.setTotalElements(page.getTotalElements());
        response.setTotalPages(page.getTotalPages());
        response.setFirst(page.isFirst());
        response.setLast(page.isLast());

        return response;
    }

    @Override
    @Transactional
    public QuotationResponseDto updateQuotation(Long id, QuotationRequestDto requestDto, String authHeader) {
        String updatedBy = extractEmailFromToken(authHeader);
        Quotation quotation = findQuotationById(id);

        // Only pending quotations can be edited
        if (!"Pending".equals(quotation.getStatus())) {
            throw new QuotationException("Only pending quotations can be edited. Current status: " + quotation.getStatus());
        }

        // Update customer if changed
        if (requestDto.getCustomer() != null) {
            Customer customer = getOrCreateCustomer(requestDto.getCustomer());
            quotation.setCustomer(customer);
        }

        // Update basic fields
        if (requestDto.getQuoteDate() != null) {
            quotation.setQuoteDate(parseDate(requestDto.getQuoteDate()));
        }
        if (requestDto.getValidUntil() != null) {
            quotation.setValidUntil(parseDate(requestDto.getValidUntil()));
        }
        if (requestDto.getSiteType() != null) {
            quotation.setSiteType(requestDto.getSiteType());
        }
        if (requestDto.getDeliveryTimeline() != null) {
            quotation.setDeliveryTimeline(requestDto.getDeliveryTimeline());
        }

        // Recalculate totals
        QuotationCalculations calc = calculateTotals(requestDto);
        quotation.setItemsSubtotal(calc.itemsSubtotal);
        quotation.setTotalPowerHP(calc.totalPowerHP);
        quotation.setTotalPowerKW(calc.totalPowerKW);
        quotation.setTransportCharge(calc.transport);
        quotation.setLoadingCharge(calc.loading);
        quotation.setOtherChargeLabel(calc.otherLabel);
        quotation.setOtherCharge(calc.other);
        quotation.setDiscountType(requestDto.getDiscountType() != null ? requestDto.getDiscountType() : "percent");
        quotation.setDiscountValue(requestDto.getDiscountValue() != null ? requestDto.getDiscountValue() : 0.0);
        quotation.setDiscountAmount(calc.discountAmount);
        quotation.setTaxableAmount(calc.taxableAmount);
        quotation.setGstPercent(calc.gstPercent);
        quotation.setCgstPercent(calc.cgstPercent);
        quotation.setCgstAmount(calc.cgstAmount);
        quotation.setSgstPercent(calc.sgstPercent);
        quotation.setSgstAmount(calc.sgstAmount);
        quotation.setIgstPercent(calc.igstPercent);
        quotation.setIgstAmount(calc.igstAmount);
        quotation.setGrandTotal(calc.grandTotal);
        quotation.setGrandTotalWords(NumberToWordsUtil.convert(calc.grandTotal));

        // Update bank details if provided
        if (requestDto.getBankDetails() != null) {
            quotation.setBankAccountName(requestDto.getBankDetails().getAccountName());
            quotation.setBankName(requestDto.getBankDetails().getBankName());
            quotation.setBankAccountNumber(requestDto.getBankDetails().getAccountNumber());
            quotation.setBankIfsc(requestDto.getBankDetails().getIfscCode());
            quotation.setBankBranch(requestDto.getBankDetails().getBranch());
        }

        // ✅ STEP 1: Delete old items
        quotationItemRepository.deleteByQuotationId(quotation.getId());

        // ✅ STEP 2: Save the quotation
        quotation.setUpdatedAt(LocalDateTime.now());
        quotation = quotationRepository.save(quotation);

        // ✅ STEP 5: Save new items
        List<QuotationItem> items = new ArrayList<>();
        for (QuotationItemDto itemDto : requestDto.getItems()) {
            QuotationItem item = new QuotationItem();
            item.setQuotation(quotation);
            item.setProductId(itemDto.getProductId());
            item.setName(itemDto.getName());
            item.setSectionCode(itemDto.getSectionCode());
            item.setHsnCode(itemDto.getHsnCode());
            item.setGstRate(itemDto.getGstRate() != null ? itemDto.getGstRate() : 18.0);
            item.setQty(itemDto.getQty());
            item.setUnit(itemDto.getUnit() != null ? itemDto.getUnit() : "Set");
            item.setRate(itemDto.getRate());
            item.setAmount(itemDto.getAmount());
            item.setPowerHP(itemDto.getPowerHP());
            item.setPowerKW(itemDto.getPowerKW());
            item.setInCustomerScope(itemDto.getInCustomerScope() != null ? itemDto.getInCustomerScope() : false);
            item.setShedSize(itemDto.getShedSize());
            item.setLabor(itemDto.getLabor());
            item.setProduction(itemDto.getProduction());
            item.setPower(itemDto.getPower());

            // ✅ FIX: same corrected SKU-based lookup as createQuotation()
            if (itemDto.getProductId() != null) {
                Optional<ProductEntity> product = productRepository.findBySku(itemDto.getProductId());
                if (product.isPresent() && product.get().getThumbnailImage() != null) {
                    item.setImage(product.get().getThumbnailImage());
                }
            }

            items.add(item);
        }
        quotationItemRepository.saveAll(items);

        // ✅ STEP 6: Upsert payment terms (update existing row if present, else create).
        // NOTE: We intentionally do NOT delete-then-recreate here. Deleting via a bulk
        // repository query while the parent `quotation.paymentTerms` association still
        // holds a managed/orphanRemoval-enabled reference causes Hibernate to issue a
        // second DELETE for the same row at flush time -> "Unexpected row count (expected 1 but was 0)".
        // Updating the existing row in place avoids that race entirely.
        if (requestDto.getPaymentTerms() != null) {
            Optional<QuotationPaymentTerms> existingPaymentTerms =
                    paymentTermsRepository.findByQuotationId(quotation.getId());
            QuotationPaymentTerms paymentTerms;
            if (existingPaymentTerms.isPresent()) {
                paymentTerms = existingPaymentTerms.get();
            } else {
                paymentTerms = new QuotationPaymentTerms();
                paymentTerms.setQuotation(quotation);
            }
            paymentTerms.setAdvancePercent(requestDto.getPaymentTerms().getAdvancePercent());
            paymentTerms.setBeforeDispatchPercent(requestDto.getPaymentTerms().getBeforeDispatchPercent());
            paymentTerms.setOnDeliveryPercent(requestDto.getPaymentTerms().getOnDeliveryPercent());
            paymentTerms.setBalancePercent(requestDto.getPaymentTerms().getBalancePercent());
            paymentTerms.setTotalPercent(100.0);
            paymentTerms = paymentTermsRepository.save(paymentTerms);
            quotation.setPaymentTerms(paymentTerms);
        }

        return buildResponse(quotation);
    }

    @Override
    public QuotationResponseDto updateQuotationStatus(Long id, StatusUpdateRequestDto requestDto, String authHeader) {
        String changedBy = extractEmailFromToken(authHeader);
        Quotation quotation = findQuotationById(id);

        String oldStatus = quotation.getStatus();
        String newStatus = requestDto.getStatus();

        validateStatusTransition(oldStatus, newStatus);

        if ("Expired".equals(newStatus)) {
            if (quotation.getValidUntil() != null && quotation.getValidUntil().isAfter(LocalDateTime.now())) {
                throw new QuotationException("Cannot expire a quotation that is still valid");
            }
        }

        quotation.setStatus(newStatus);
        quotation.setUpdatedAt(LocalDateTime.now());
        quotationRepository.save(quotation);

        logStatusChange(quotation.getId(), oldStatus, newStatus, changedBy);

        return buildResponse(quotation);
    }

    @Override
    public QuotationResponseDto convertToInvoice(Long id, ConvertToInvoiceRequestDto requestDto, String authHeader) {
        String changedBy = extractEmailFromToken(authHeader);
        Quotation quotation = findQuotationById(id);

        if (!"Accepted".equals(quotation.getStatus())) {
            throw new QuotationException("Only accepted quotations can be converted to invoice. Current status: " + quotation.getStatus());
        }

        if (Boolean.TRUE.equals(quotation.getConvertedToInvoice())) {
            throw new QuotationException("This quotation has already been converted to invoice");
        }

        quotation.setConvertedToInvoice(true);
        quotation.setInvoiceId(requestDto.getInvoiceId());
        quotation.setUpdatedAt(LocalDateTime.now());
        quotationRepository.save(quotation);

        logStatusChange(quotation.getId(), quotation.getStatus(), "Converted to Invoice", changedBy);

        return buildResponse(quotation);
    }

    @Override
    public QuotationResponseDto duplicateQuotation(Long id, String authHeader) {
        String createdBy = extractEmailFromToken(authHeader);
        Quotation original = findQuotationById(id);

        Quotation duplicate = new Quotation();
        duplicate.setQuoteNo(generateQuoteNumber());
        duplicate.setQuoteDate(LocalDateTime.now());
        duplicate.setValidUntil(LocalDateTime.now().plusDays(30));
        duplicate.setStatus("Pending");
        duplicate.setCustomer(original.getCustomer());
        duplicate.setIsInterState(original.getIsInterState());
        duplicate.setSiteType(original.getSiteType());
        duplicate.setDeliveryTimeline(original.getDeliveryTimeline());
        duplicate.setItemsSubtotal(original.getItemsSubtotal());
        duplicate.setTotalPowerHP(original.getTotalPowerHP());
        duplicate.setTotalPowerKW(original.getTotalPowerKW());
        duplicate.setTransportCharge(original.getTransportCharge());
        duplicate.setLoadingCharge(original.getLoadingCharge());
        duplicate.setOtherChargeLabel(original.getOtherChargeLabel());
        duplicate.setOtherCharge(original.getOtherCharge());
        duplicate.setDiscountType(original.getDiscountType());
        duplicate.setDiscountValue(original.getDiscountValue());
        duplicate.setDiscountAmount(original.getDiscountAmount());
        duplicate.setTaxableAmount(original.getTaxableAmount());
        duplicate.setGstPercent(original.getGstPercent());
        duplicate.setCgstPercent(original.getCgstPercent());
        duplicate.setCgstAmount(original.getCgstAmount());
        duplicate.setSgstPercent(original.getSgstPercent());
        duplicate.setSgstAmount(original.getSgstAmount());
        duplicate.setIgstPercent(original.getIgstPercent());
        duplicate.setIgstAmount(original.getIgstAmount());
        duplicate.setGrandTotal(original.getGrandTotal());
        duplicate.setGrandTotalWords(original.getGrandTotalWords());
        duplicate.setPaymentType(original.getPaymentType());
        duplicate.setBankAccountName(original.getBankAccountName());
        duplicate.setBankName(original.getBankName());
        duplicate.setBankAccountNumber(original.getBankAccountNumber());
        duplicate.setBankIfsc(original.getBankIfsc());
        duplicate.setBankBranch(original.getBankBranch());
        duplicate.setTermsTemplateVersion(original.getTermsTemplateVersion());
        duplicate.setTermsCategoriesApplied(original.getTermsCategoriesApplied());
        duplicate.setCreatedBy(createdBy);
        duplicate.setCreatedAt(LocalDateTime.now());
        duplicate.setUpdatedAt(LocalDateTime.now());
        duplicate.setConvertedToInvoice(false);

        Quotation savedDuplicate = quotationRepository.save(duplicate);

        List<QuotationItem> items = new ArrayList<>();
        for (QuotationItem originalItem : original.getItems()) {
            QuotationItem item = new QuotationItem();
            item.setQuotation(savedDuplicate);
            item.setProductId(originalItem.getProductId());
            item.setName(originalItem.getName());
            item.setSectionCode(originalItem.getSectionCode());
            item.setHsnCode(originalItem.getHsnCode());
            item.setGstRate(originalItem.getGstRate());
            item.setQty(originalItem.getQty());
            item.setUnit(originalItem.getUnit());
            item.setRate(originalItem.getRate());
            item.setAmount(originalItem.getAmount());
            item.setPowerHP(originalItem.getPowerHP());
            item.setPowerKW(originalItem.getPowerKW());
            item.setInCustomerScope(originalItem.getInCustomerScope());
            item.setShedSize(originalItem.getShedSize());
            item.setLabor(originalItem.getLabor());
            item.setProduction(originalItem.getProduction());
            item.setPower(originalItem.getPower());
            item.setImage(originalItem.getImage());
            items.add(item);
        }
        quotationItemRepository.saveAll(items);

        if (original.getPaymentTerms() != null) {
            QuotationPaymentTerms paymentTerms = new QuotationPaymentTerms();
            paymentTerms.setQuotation(savedDuplicate);
            paymentTerms.setAdvancePercent(original.getPaymentTerms().getAdvancePercent());
            paymentTerms.setBeforeDispatchPercent(original.getPaymentTerms().getBeforeDispatchPercent());
            paymentTerms.setOnDeliveryPercent(original.getPaymentTerms().getOnDeliveryPercent());
            paymentTerms.setBalancePercent(original.getPaymentTerms().getBalancePercent());
            paymentTerms.setTotalPercent(100.0);
            paymentTermsRepository.save(paymentTerms);
        }

        logStatusChange(savedDuplicate.getId(), null, "Pending", createdBy);

        return buildResponse(savedDuplicate);
    }

    @Override
    public void deleteQuotation(Long id, String authHeader) {
        Quotation quotation = findQuotationById(id);
        quotation.setDeletedAt(LocalDateTime.now());
        quotation.setUpdatedAt(LocalDateTime.now());
        quotationRepository.save(quotation);
    }

    @Override
    @Transactional
    public void processExpiredQuotations() {
        List<Quotation> expiredQuotations = quotationRepository.findExpiredPendingQuotations(LocalDateTime.now());

        for (Quotation quotation : expiredQuotations) {
            String oldStatus = quotation.getStatus();
            quotation.setStatus("Expired");
            quotation.setUpdatedAt(LocalDateTime.now());
            quotationRepository.save(quotation);
            logStatusChange(quotation.getId(), oldStatus, "Expired", "SYSTEM");
        }
    }

    // Helper methods
    private Quotation findQuotationById(Long id) {
        return quotationRepository.findById(id)
                .orElseThrow(() -> new QuotationException("Quotation not found with ID: " + id));
    }

    private Customer getOrCreateCustomer(CustomerDto customerDto) {
        if (customerDto == null) {
            throw new QuotationException("Customer information is required");
        }

        if (customerDto.getMobilePrimary() == null || customerDto.getMobilePrimary().length() != 10) {
            throw new QuotationException("Primary mobile number must be 10 digits");
        }

        Optional<Customer> existing = customerRepository.findByMobilePrimary(customerDto.getMobilePrimary());

        if (existing.isPresent()) {
            Customer customer = existing.get();
            customer.setName(customerDto.getName());
            customer.setMobileSecondary(customerDto.getMobileSecondary());
            customer.setEmail(customerDto.getEmail());
            customer.setAddress(customerDto.getAddress());
            customer.setCity(customerDto.getCity());
            customer.setState(customerDto.getState());
            customer.setStateCode(customerDto.getStateCode());
            customer.setPincode(customerDto.getPincode());
            if (customerDto.getGstin() != null && !customerDto.getGstin().isEmpty()) {
                customer.setGstin(customerDto.getGstin());
            }
            customer.setUpdatedAt(LocalDateTime.now());
            return customerRepository.save(customer);
        } else {
            Customer customer = new Customer();
            customer.setCustomerCode(generateCustomerCode());
            customer.setName(customerDto.getName());
            customer.setMobilePrimary(customerDto.getMobilePrimary());
            customer.setMobileSecondary(customerDto.getMobileSecondary());
            customer.setEmail(customerDto.getEmail());
            customer.setAddress(customerDto.getAddress());
            customer.setCity(customerDto.getCity());
            customer.setState(customerDto.getState());
            customer.setStateCode(customerDto.getStateCode());
            customer.setPincode(customerDto.getPincode());
            customer.setGstin(customerDto.getGstin());
            customer.setCreatedAt(LocalDateTime.now());
            customer.setUpdatedAt(LocalDateTime.now());
            return customerRepository.save(customer);
        }
    }

    private String generateCustomerCode() {
        Random random = new Random();
        int num = 1000 + random.nextInt(9000);
        return "CUST-" + num;
    }

    private String generateQuoteNumber() {
        Integer maxNumber = quotationRepository.findMaxQuoteNumber();
        int nextNumber = (maxNumber == null) ? 1 : maxNumber + 1;
        return QUOTE_PREFIX + String.format("%04d", nextNumber);
    }

    private LocalDateTime parseDate(String dateStr) {
        if (dateStr == null || dateStr.isEmpty()) {
            return LocalDateTime.now();
        }
        try {
            return LocalDate.parse(dateStr, DATE_FORMATTER).atStartOfDay();
        } catch (Exception e) {
            return LocalDateTime.now();
        }
    }

    private QuotationCalculations calculateTotals(QuotationRequestDto requestDto) {
        QuotationCalculations calc = new QuotationCalculations();

        double itemsSubtotal = 0;
        double totalHP = 0;
        double totalKW = 0;

        for (QuotationItemDto item : requestDto.getItems()) {
            double amount = item.getRate() * item.getQty();
            item.setAmount(amount);
            itemsSubtotal += amount;
            if (item.getPowerHP() != null) totalHP += item.getPowerHP();
            if (item.getPowerKW() != null) totalKW += item.getPowerKW();
        }

        calc.itemsSubtotal = itemsSubtotal;
        calc.totalPowerHP = totalHP;
        calc.totalPowerKW = totalKW;

        double transport = requestDto.getAdditionalCharges() != null && requestDto.getAdditionalCharges().getTransport() != null
                ? requestDto.getAdditionalCharges().getTransport() : 0.0;
        double loading = requestDto.getAdditionalCharges() != null && requestDto.getAdditionalCharges().getLoading() != null
                ? requestDto.getAdditionalCharges().getLoading() : 0.0;
        double other = requestDto.getAdditionalCharges() != null && requestDto.getAdditionalCharges().getOther() != null
                ? requestDto.getAdditionalCharges().getOther() : 0.0;
        String otherLabel = requestDto.getAdditionalCharges() != null && requestDto.getAdditionalCharges().getOtherLabel() != null
                ? requestDto.getAdditionalCharges().getOtherLabel() : "Other Charges";

        calc.transport = transport;
        calc.loading = loading;
        calc.other = other;
        calc.otherLabel = otherLabel;

        double subtotalWithCharges = itemsSubtotal + transport + loading + other;

        double discountValue = requestDto.getDiscountValue() != null ? requestDto.getDiscountValue() : 0.0;
        String discountType = requestDto.getDiscountType() != null ? requestDto.getDiscountType() : "percent";

        double discountAmount = 0;
        if ("percent".equals(discountType)) {
            discountAmount = subtotalWithCharges * discountValue / 100;
        } else {
            discountAmount = discountValue;
        }

        calc.discountAmount = discountAmount;

        double taxableAmount = subtotalWithCharges - discountAmount;
        calc.taxableAmount = taxableAmount;

        double gstPercent = requestDto.getGstPercent() != null ? requestDto.getGstPercent() : 18.0;
        calc.gstPercent = gstPercent;

        String customerState = requestDto.getCustomer() != null ? requestDto.getCustomer().getState() : "";
        boolean isInterState = customerState != null && !customerState.isEmpty() && !customerState.equals(COMPANY_STATE);
        calc.isInterState = isInterState;

        double cgstPercent = 0, sgstPercent = 0, igstPercent = 0;
        double cgstAmount = 0, sgstAmount = 0, igstAmount = 0;

        if (isInterState) {
            igstPercent = gstPercent;
            igstAmount = taxableAmount * gstPercent / 100;
        } else {
            cgstPercent = gstPercent / 2;
            sgstPercent = gstPercent / 2;
            cgstAmount = taxableAmount * cgstPercent / 100;
            sgstAmount = taxableAmount * sgstPercent / 100;
        }

        calc.cgstPercent = cgstPercent;
        calc.cgstAmount = cgstAmount;
        calc.sgstPercent = sgstPercent;
        calc.sgstAmount = sgstAmount;
        calc.igstPercent = igstPercent;
        calc.igstAmount = igstAmount;

        calc.grandTotal = taxableAmount + cgstAmount + sgstAmount + igstAmount;

        return calc;
    }

    private void validatePaymentTerms(PaymentTermsDto paymentTerms) {
        if (paymentTerms == null) {
            throw new QuotationException("Payment terms are required");
        }

        double total = (paymentTerms.getAdvancePercent() != null ? paymentTerms.getAdvancePercent() : 0) +
                (paymentTerms.getBeforeDispatchPercent() != null ? paymentTerms.getBeforeDispatchPercent() : 0) +
                (paymentTerms.getOnDeliveryPercent() != null ? paymentTerms.getOnDeliveryPercent() : 0) +
                (paymentTerms.getBalancePercent() != null ? paymentTerms.getBalancePercent() : 0);

        if (Math.abs(total - 100.0) > 0.001) {
            throw new QuotationException("Payment terms percentages must sum to 100. Current sum: " + total);
        }
    }

    private void validateGstin(String gstin) {
        if (gstin == null || gstin.isEmpty()) {
            return;
        }
        String pattern = "^[0-9]{2}[A-Z]{5}[0-9]{4}[A-Z]{1}[0-9A-Z]{1}Z[0-9A-Z]{1}$";
        if (!gstin.matches(pattern)) {
            throw new QuotationException("Invalid GSTIN format. Expected format: 2 digits, 5 letters, 4 digits, 1 letter, 1 alphanumeric, Z, 1 alphanumeric");
        }
    }

    private void validateStatusTransition(String oldStatus, String newStatus) {
        if (oldStatus.equals(newStatus)) {
            return;
        }

        switch (oldStatus) {
            case "Pending":
                if (!Arrays.asList("Accepted", "Rejected", "Expired").contains(newStatus)) {
                    throw new QuotationException("Invalid status transition from Pending to " + newStatus);
                }
                break;
            case "Accepted":
                if (!"Converted to Invoice".equals(newStatus)) {
                    throw new QuotationException("Invalid status transition from Accepted to " + newStatus);
                }
                break;
            case "Rejected":
            case "Expired":
            case "Converted to Invoice":
                throw new QuotationException("Cannot change status from " + oldStatus);
            default:
                throw new QuotationException("Unknown status: " + oldStatus);
        }
    }

    private void logStatusChange(Long quotationId, String oldStatus, String newStatus, String changedBy) {
        QuotationStatusLog log = new QuotationStatusLog();
        log.setQuotationId(quotationId);
        log.setOldStatus(oldStatus);
        log.setNewStatus(newStatus);
        log.setChangedBy(changedBy);
        log.setChangedAt(LocalDateTime.now());
        statusLogRepository.save(log);
    }

    private String extractEmailFromToken(String authHeader) {
        if (authHeader == null || authHeader.isEmpty()) {
            return "SYSTEM";
        }
        try {
            String token = authHeader.startsWith("Bearer ") ? authHeader.substring(7) : authHeader;
            return jwtUtil.extractEmail(token);
        } catch (Exception e) {
            return "SYSTEM";
        }
    }

    private QuotationResponseDto buildResponse(Quotation quotation) {
        QuotationResponseDto response = new QuotationResponseDto();

        response.setQuoteNo(quotation.getQuoteNo());
        response.setQuoteDate(quotation.getQuoteDate() != null ? quotation.getQuoteDate().format(DATE_FORMATTER) : null);
        response.setValidUntil(quotation.getValidUntil() != null ? quotation.getValidUntil().format(DATE_FORMATTER) : null);
        response.setStatus(quotation.getStatus());

        if (quotation.getCustomer() != null) {
            CustomerDto customerDto = new CustomerDto();
            customerDto.setId(quotation.getCustomer().getId());
            customerDto.setCustomerCode(quotation.getCustomer().getCustomerCode());
            customerDto.setName(quotation.getCustomer().getName());
            customerDto.setMobilePrimary(quotation.getCustomer().getMobilePrimary());
            customerDto.setMobileSecondary(quotation.getCustomer().getMobileSecondary());
            customerDto.setEmail(quotation.getCustomer().getEmail());
            customerDto.setAddress(quotation.getCustomer().getAddress());
            customerDto.setCity(quotation.getCustomer().getCity());
            customerDto.setState(quotation.getCustomer().getState());
            customerDto.setStateCode(quotation.getCustomer().getStateCode());
            customerDto.setPincode(quotation.getCustomer().getPincode());
            customerDto.setGstin(quotation.getCustomer().getGstin());
            response.setCustomer(customerDto);
        }

        response.setIsInterState(quotation.getIsInterState());
        response.setSiteType(quotation.getSiteType());
        response.setDeliveryTimeline(quotation.getDeliveryTimeline());

        List<QuotationItemDto> itemDtos = new ArrayList<>();
        for (QuotationItem item : quotation.getItems()) {
            QuotationItemDto itemDto = new QuotationItemDto();
            itemDto.setId("it" + item.getId());
            itemDto.setProductId(item.getProductId());
            itemDto.setName(item.getName());
            itemDto.setSectionCode(item.getSectionCode());
            itemDto.setHsnCode(item.getHsnCode());
            itemDto.setGstRate(item.getGstRate());
            itemDto.setQty(item.getQty());
            itemDto.setUnit(item.getUnit());
            itemDto.setRate(item.getRate());
            itemDto.setAmount(item.getAmount());
            itemDto.setPowerHP(item.getPowerHP());
            itemDto.setPowerKW(item.getPowerKW());
            itemDto.setInCustomerScope(item.getInCustomerScope());
            itemDto.setShedSize(item.getShedSize());
            itemDto.setLabor(item.getLabor());
            itemDto.setProduction(item.getProduction());
            itemDto.setPower(item.getPower());
            itemDto.setImage(item.getImage());
            itemDtos.add(itemDto);
        }
        response.setItems(itemDtos);

        response.setItemsSubtotal(quotation.getItemsSubtotal());
        response.setTotalPowerHP(quotation.getTotalPowerHP());
        response.setTotalPowerKW(quotation.getTotalPowerKW());

        QuotationResponseDto.AdditionalChargesDto charges = new QuotationResponseDto.AdditionalChargesDto();
        charges.setTransport(quotation.getTransportCharge());
        charges.setLoading(quotation.getLoadingCharge());
        charges.setOtherLabel(quotation.getOtherChargeLabel());
        charges.setOther(quotation.getOtherCharge());
        response.setAdditionalCharges(charges);

        response.setDiscountType(quotation.getDiscountType());
        response.setDiscountValue(quotation.getDiscountValue());
        response.setDiscountAmount(quotation.getDiscountAmount());
        response.setTaxableAmount(quotation.getTaxableAmount());
        response.setGstPercent(quotation.getGstPercent());

        GstBreakupDto gstBreakup = new GstBreakupDto();
        gstBreakup.setCgstPercent(quotation.getCgstPercent());
        gstBreakup.setCgstAmount(quotation.getCgstAmount());
        gstBreakup.setSgstPercent(quotation.getSgstPercent());
        gstBreakup.setSgstAmount(quotation.getSgstAmount());
        gstBreakup.setIgstPercent(quotation.getIgstPercent());
        gstBreakup.setIgstAmount(quotation.getIgstAmount());
        response.setGstBreakup(gstBreakup);

        response.setGrandTotal(quotation.getGrandTotal());
        response.setGrandTotalWords(quotation.getGrandTotalWords());
        response.setPaymentType(quotation.getPaymentType());

        if (quotation.getPaymentTerms() != null) {
            PaymentTermsDto paymentTerms = new PaymentTermsDto();
            paymentTerms.setAdvancePercent(quotation.getPaymentTerms().getAdvancePercent());
            paymentTerms.setBeforeDispatchPercent(quotation.getPaymentTerms().getBeforeDispatchPercent());
            paymentTerms.setOnDeliveryPercent(quotation.getPaymentTerms().getOnDeliveryPercent());
            paymentTerms.setBalancePercent(quotation.getPaymentTerms().getBalancePercent());
            paymentTerms.setTotalPercent(quotation.getPaymentTerms().getTotalPercent());
            response.setPaymentTerms(paymentTerms);
        }

        BankDetailsDto bankDetails = new BankDetailsDto();
        bankDetails.setAccountName(quotation.getBankAccountName());
        bankDetails.setBankName(quotation.getBankName());
        bankDetails.setAccountNumber(quotation.getBankAccountNumber());
        bankDetails.setIfscCode(quotation.getBankIfsc());
        bankDetails.setBranch(quotation.getBankBranch());
        response.setBankDetails(bankDetails);

        TermsAndConditionsDto terms = new TermsAndConditionsDto();
        terms.setTemplateVersion(quotation.getTermsTemplateVersion());
        try {
            if (quotation.getTermsCategoriesApplied() != null && !quotation.getTermsCategoriesApplied().isEmpty()) {
                List<String> categories = objectMapper.readValue(
                        quotation.getTermsCategoriesApplied(),
                        objectMapper.getTypeFactory().constructCollectionType(List.class, String.class)
                );
                terms.setCategoriesApplied(categories);
            } else {
                terms.setCategoriesApplied(new ArrayList<>());
            }
        } catch (Exception e) {
            terms.setCategoriesApplied(new ArrayList<>());
        }
        response.setTermsAndConditions(terms);

        List<ProductImageDto> productImages = new ArrayList<>();
        for (QuotationItem item : quotation.getItems()) {
            if (item.getProductId() != null && item.getImage() != null) {
                ProductImageDto image = new ProductImageDto();
                image.setProductId(item.getProductId());
                image.setProductName(item.getName());
                image.setImage(item.getImage());
                productImages.add(image);
            }
        }
        response.setProductImages(productImages);

        MetaDto meta = new MetaDto();
        meta.setCreatedBy(quotation.getCreatedBy());
        meta.setCreatedAt(quotation.getCreatedAt());
        meta.setUpdatedAt(quotation.getUpdatedAt());
        meta.setConvertedToInvoice(quotation.getConvertedToInvoice());
        meta.setInvoiceId(quotation.getInvoiceId());
        response.setMeta(meta);

        return response;
    }

    private static class QuotationCalculations {
        double itemsSubtotal;
        double totalPowerHP;
        double totalPowerKW;
        double transport;
        double loading;
        double other;
        String otherLabel;
        double discountAmount;
        double taxableAmount;
        double gstPercent;
        boolean isInterState;
        double cgstPercent;
        double cgstAmount;
        double sgstPercent;
        double sgstAmount;
        double igstPercent;
        double igstAmount;
        double grandTotal;
    }
}