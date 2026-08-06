package com.quo.quotation2.service.serviceImpl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.quo.quotation2.dto.requestdto.ConvertToInvoiceRequestDto;
import com.quo.quotation2.dto.requestdto.QuotationRequestDto;
import com.quo.quotation2.dto.requestdto.StatusUpdateRequestDto;
import com.quo.quotation2.dto.responsedto.PageResponseDto;
import com.quo.quotation2.dto.responsedto.QuotationListResponseDto;
import com.quo.quotation2.dto.responsedto.QuotationResponseDto;
import com.quo.quotation2.entity.*;
import com.quo.quotation2.exception.QuotationException;
import com.quo.quotation2.repository.CustomerRepository;
import com.quo.quotation2.repository.ProductRepository;
import com.quo.quotation2.repository.QuotationItemRepository;
import com.quo.quotation2.repository.QuotationRepository;
import com.quo.quotation2.repository.QuotationStatusLogRepository;
import com.quo.quotation2.service.QuotationService;
import com.quo.quotation2.util.QuoteNumberGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional
public class QuotationServiceImpl implements QuotationService {

    private static final Logger logger = LoggerFactory.getLogger(QuotationServiceImpl.class);

    @Autowired
    private QuotationRepository quotationRepository;

    @Autowired
    private QuotationItemRepository quotationItemRepository;

    @Autowired
    private QuotationStatusLogRepository statusLogRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private QuoteNumberGenerator quoteNumberGenerator;

    @Autowired
    private ObjectMapper objectMapper;

    private static final String ADMIN = "Admin";
    private static final String COMPANY_STATE = "Uttar Pradesh";

    @Override
    public QuotationResponseDto createQuotation(QuotationRequestDto requestDto, String authHeader) {
        logger.info("Creating quotation for customer: {}", requestDto.getCustomer().getName());

        validateQuotationRequest(requestDto);
        Customer customer = getOrCreateCustomer(requestDto.getCustomer());

        Quotation quotation = new Quotation();
        quotation.setQuoteNo(quoteNumberGenerator.generateNext());
        quotation.setQuoteDate(LocalDateTime.now());
        quotation.setDate(LocalDate.now());
        quotation.setStatus("Pending");
        quotation.setCustomer(customer);
        quotation.setCreatedBy(ADMIN);
        quotation.setCreatedAt(LocalDateTime.now());
        quotation.setUpdatedAt(LocalDateTime.now());
        quotation.setConvertedToInvoice(false);

        quotation.setDeliveryTimeline(requestDto.getDeliveryTimeline() != null ? requestDto.getDeliveryTimeline() : "45 days from advance payment");
        quotation.setGstPercent(requestDto.getGstPercent() != null ? requestDto.getGstPercent() : 18.0);
        quotation.setDiscountType(requestDto.getDiscountType() != null ? requestDto.getDiscountType() : "percent");
        quotation.setDiscountValue(requestDto.getDiscountValue() != null ? requestDto.getDiscountValue() : 0.0);
        quotation.setPaymentType(requestDto.getPaymentType() != null ? requestDto.getPaymentType() : "full");
        quotation.setAdditionalNotes(requestDto.getAdditionalNotes());
        applyTermsAndConditions(quotation, requestDto.getTermsAndConditions());

        if (requestDto.getValidUntil() != null) {
            quotation.setValidUntil(LocalDate.parse(requestDto.getValidUntil()).atStartOfDay());
        } else {
            quotation.setValidUntil(LocalDateTime.now().plusDays(30));
        }

        String customerState = customer.getState() != null ? customer.getState().trim().toLowerCase() : "";
        boolean isInterState = !customerState.isEmpty() && !customerState.equals(COMPANY_STATE.toLowerCase());
        quotation.setIsInterState(isInterState);

        if (requestDto.getCosts() != null) {
            quotation.setTransportCharge(requestDto.getCosts().getTransport() != null ? requestDto.getCosts().getTransport() : 0.0);
            quotation.setLoadingCharge(requestDto.getCosts().getLoading() != null ? requestDto.getCosts().getLoading() : 0.0);
            quotation.setOtherChargeLabel(requestDto.getCosts().getOtherLabel() != null ? requestDto.getCosts().getOtherLabel() : "Other Charges");
            quotation.setOtherCharge(requestDto.getCosts().getOther() != null ? requestDto.getCosts().getOther() : 0.0);
        }

        if (requestDto.getBank() != null) {
            quotation.setBankAccountName(requestDto.getBank().getAccountName());
            quotation.setBankName(requestDto.getBank().getBankName());
            quotation.setBankAccountNumber(requestDto.getBank().getAccountNumber());
            quotation.setBankIfsc(requestDto.getBank().getIfscCode());
            quotation.setBankBranch(requestDto.getBank().getBranch());
        }

        if (requestDto.getPaymentTerms() != null) {
            quotation.setAdvancePercent(requestDto.getPaymentTerms().getAdvance() != null ? requestDto.getPaymentTerms().getAdvance() : 0.0);
            quotation.setMaterialPercent(requestDto.getPaymentTerms().getMaterial() != null ? requestDto.getPaymentTerms().getMaterial() : 0.0);
            quotation.setInstallationPercent(requestDto.getPaymentTerms().getInstallation() != null ? requestDto.getPaymentTerms().getInstallation() : 0.0);
            quotation.setBalancePercent(requestDto.getPaymentTerms().getBalance() != null ? requestDto.getPaymentTerms().getBalance() : 0.0);
        }

        if (requestDto.getPlantOverview() != null) {
            quotation.setPlantModel(requestDto.getPlantOverview().getModel());
            quotation.setPlantProductionCapacity(requestDto.getPlantOverview().getProductionCapacity());
            quotation.setPlantBricksSize(requestDto.getPlantOverview().getBricksSize());
            quotation.setPlantPalletSize(requestDto.getPlantOverview().getPalletSize());
            quotation.setPlantShedArea(requestDto.getPlantOverview().getRequiredShedArea());
            quotation.setPlantTotalLand(requestDto.getPlantOverview().getTotalLand());
            quotation.setPlantConnectedPower(requestDto.getPlantOverview().getConnectedPower());
            quotation.setPlantLabourRequirement(requestDto.getPlantOverview().getLabourRequirement());
        }

        calculateTotals(quotation, requestDto);
        Quotation savedQuotation = quotationRepository.save(quotation);

        List<QuotationItem> items = createQuotationItems(savedQuotation, requestDto.getItems());
        savedQuotation.getItems().clear();
        savedQuotation.getItems().addAll(items);

        addHistoryEntry(savedQuotation, "Created", "Quotation generated for " + customer.getName());

        logger.info("Quotation created successfully: {}", savedQuotation.getQuoteNo());
        return toResponseDto(savedQuotation);
    }

    @Override
    public QuotationResponseDto getQuotation(Long id) {
        Quotation quotation = quotationRepository.findById(id)
                .orElseThrow(() -> new QuotationException("Quotation not found with id: " + id));
        return toResponseDto(quotation);
    }

    @Override
    public QuotationResponseDto getQuotationByQuoteNo(String quoteNo) {
        Quotation quotation = quotationRepository.findByQuoteNo(quoteNo)
                .orElseThrow(() -> new QuotationException("Quotation not found with number: " + quoteNo));
        return toResponseDto(quotation);
    }

    @Override
    public PageResponseDto<QuotationListResponseDto> getQuotations(String status, String dateFrom, String dateTo, Long customerId, Pageable pageable) {
        LocalDateTime fromDate = dateFrom != null ? LocalDate.parse(dateFrom).atStartOfDay() : null;
        LocalDateTime toDate = dateTo != null ? LocalDate.parse(dateTo).atTime(23, 59, 59) : null;

        Page<Quotation> page = quotationRepository.findWithFilters(status, fromDate, toDate, customerId, pageable);

        List<QuotationListResponseDto> content = page.getContent().stream()
                .map(this::toListResponseDto)
                .collect(Collectors.toList());

        return new PageResponseDto<>(content, page.getNumber(), page.getSize(), page.getTotalElements());
    }

    @Override
    public QuotationResponseDto updateQuotation(Long id, QuotationRequestDto requestDto, String authHeader) {
        logger.info("Updating quotation with ID: {}", id);

        Quotation quotation = quotationRepository.findById(id)
                .orElseThrow(() -> new QuotationException("Quotation not found with id: " + id));

        Customer customer = getOrCreateCustomer(requestDto.getCustomer());
        quotation.setCustomer(customer);

        // BUGFIX: previously isInterState was never recomputed here, so editing a
        // quotation and changing the customer's state left GST (CGST/SGST vs IGST) stale.
        String customerState = customer.getState() != null ? customer.getState().trim().toLowerCase() : "";
        boolean isInterState = !customerState.isEmpty() && !customerState.equals(COMPANY_STATE.toLowerCase());
        quotation.setIsInterState(isInterState);

        quotation.setDeliveryTimeline(requestDto.getDeliveryTimeline());
        quotation.setGstPercent(requestDto.getGstPercent());
        quotation.setDiscountType(requestDto.getDiscountType());
        quotation.setDiscountValue(requestDto.getDiscountValue());
        quotation.setPaymentType(requestDto.getPaymentType());
        quotation.setAdditionalNotes(requestDto.getAdditionalNotes());
        applyTermsAndConditions(quotation, requestDto.getTermsAndConditions());
        quotation.setUpdatedAt(LocalDateTime.now());

        if (requestDto.getValidUntil() != null) {
            quotation.setValidUntil(LocalDate.parse(requestDto.getValidUntil()).atStartOfDay());
        }

        if (requestDto.getCosts() != null) {
            quotation.setTransportCharge(requestDto.getCosts().getTransport());
            quotation.setLoadingCharge(requestDto.getCosts().getLoading());
            quotation.setOtherChargeLabel(requestDto.getCosts().getOtherLabel());
            quotation.setOtherCharge(requestDto.getCosts().getOther());
        }

        if (requestDto.getBank() != null) {
            quotation.setBankAccountName(requestDto.getBank().getAccountName());
            quotation.setBankName(requestDto.getBank().getBankName());
            quotation.setBankAccountNumber(requestDto.getBank().getAccountNumber());
            quotation.setBankIfsc(requestDto.getBank().getIfscCode());
            quotation.setBankBranch(requestDto.getBank().getBranch());
        }

        if (requestDto.getPaymentTerms() != null) {
            quotation.setAdvancePercent(requestDto.getPaymentTerms().getAdvance());
            quotation.setMaterialPercent(requestDto.getPaymentTerms().getMaterial());
            quotation.setInstallationPercent(requestDto.getPaymentTerms().getInstallation());
            quotation.setBalancePercent(requestDto.getPaymentTerms().getBalance());
        }

        if (requestDto.getPlantOverview() != null) {
            quotation.setPlantModel(requestDto.getPlantOverview().getModel());
            quotation.setPlantProductionCapacity(requestDto.getPlantOverview().getProductionCapacity());
            quotation.setPlantBricksSize(requestDto.getPlantOverview().getBricksSize());
            quotation.setPlantPalletSize(requestDto.getPlantOverview().getPalletSize());
            quotation.setPlantShedArea(requestDto.getPlantOverview().getRequiredShedArea());
            quotation.setPlantTotalLand(requestDto.getPlantOverview().getTotalLand());
            quotation.setPlantConnectedPower(requestDto.getPlantOverview().getConnectedPower());
            quotation.setPlantLabourRequirement(requestDto.getPlantOverview().getLabourRequirement());
        }

        // BUGFIX: Do NOT replace the managed collection with quotation.setItems(newList) —
        // Hibernate tracks quotation.items as a PersistentBag once the entity is loaded,
        // and swapping the reference confuses orphanRemoval, causing:
        // "A collection with orphan deletion was no longer referenced by the owning entity instance".
        // Also removed the raw deleteByQuotationId() query — it bypassed the persistence
        // context, leaving Hibernate's already-loaded items collection out of sync.
        // Instead: clear the existing managed collection, then refill it in place.
        List<QuotationItem> newItems = createQuotationItems(quotation, requestDto.getItems());
        quotation.getItems().clear();
        quotation.getItems().addAll(newItems);

        calculateTotals(quotation, requestDto);

        Quotation savedQuotation = quotationRepository.save(quotation);
        addHistoryEntry(savedQuotation, "Edited", "Quotation updated");

        return toResponseDto(savedQuotation);
    }

    @Override
    public QuotationResponseDto updateQuotationStatus(Long id, StatusUpdateRequestDto requestDto, String authHeader) {
        Quotation quotation = quotationRepository.findById(id)
                .orElseThrow(() -> new QuotationException("Quotation not found with id: " + id));

        String oldStatus = quotation.getStatus();
        String newStatus = requestDto.getStatus();

        if (oldStatus.equals(newStatus)) {
            return toResponseDto(quotation);
        }

        quotation.setStatus(newStatus);
        quotation.setUpdatedAt(LocalDateTime.now());

        if ("Accepted".equals(newStatus) || "Rejected".equals(newStatus)) {
            quotation.setApprovedBy(ADMIN);
            quotation.setApprovalDate(LocalDate.now());
            quotation.setApprovalNotes(requestDto.getNotes());
        } else if ("Pending".equals(newStatus)) {
            quotation.setApprovedBy(null);
            quotation.setApprovalDate(null);
            quotation.setApprovalNotes(null);
        }

        Quotation savedQuotation = quotationRepository.save(quotation);
        addHistoryEntry(savedQuotation, newStatus, "Status changed from " + oldStatus + " to " + newStatus);
        logStatusChange(savedQuotation, oldStatus, newStatus);

        return toResponseDto(savedQuotation);
    }

    @Override
    public QuotationResponseDto convertToInvoice(Long id, ConvertToInvoiceRequestDto requestDto, String authHeader) {
        Quotation quotation = quotationRepository.findById(id)
                .orElseThrow(() -> new QuotationException("Quotation not found with id: " + id));

        quotation.setConvertedToInvoice(true);
        quotation.setInvoiceId(requestDto.getInvoiceId());
        quotation.setStatus("INVOICED");
        quotation.setUpdatedAt(LocalDateTime.now());

        Quotation savedQuotation = quotationRepository.save(quotation);
        addHistoryEntry(savedQuotation, "Converted to Invoice", "Invoice ID: " + requestDto.getInvoiceId());

        return toResponseDto(savedQuotation);
    }

    @Override
    public QuotationResponseDto duplicateQuotation(Long id, String authHeader) {
        Quotation original = quotationRepository.findById(id)
                .orElseThrow(() -> new QuotationException("Quotation not found with id: " + id));

        Quotation duplicate = new Quotation();
        duplicate.setQuoteNo(quoteNumberGenerator.generateNext());
        duplicate.setQuoteDate(LocalDateTime.now());
        duplicate.setDate(LocalDate.now());
        duplicate.setStatus("Pending");
        duplicate.setCustomer(original.getCustomer());
        duplicate.setDeliveryTimeline(original.getDeliveryTimeline());
        duplicate.setGstPercent(original.getGstPercent());
        duplicate.setDiscountType(original.getDiscountType());
        duplicate.setDiscountValue(original.getDiscountValue());
        duplicate.setPaymentType(original.getPaymentType());
        duplicate.setIsInterState(original.getIsInterState());
        duplicate.setValidUntil(LocalDateTime.now().plusDays(30));
        duplicate.setCreatedBy(ADMIN);
        duplicate.setCreatedAt(LocalDateTime.now());
        duplicate.setUpdatedAt(LocalDateTime.now());

        duplicate.setTransportCharge(original.getTransportCharge());
        duplicate.setLoadingCharge(original.getLoadingCharge());
        duplicate.setOtherChargeLabel(original.getOtherChargeLabel());
        duplicate.setOtherCharge(original.getOtherCharge());

        duplicate.setBankAccountName(original.getBankAccountName());
        duplicate.setBankName(original.getBankName());
        duplicate.setBankAccountNumber(original.getBankAccountNumber());
        duplicate.setBankIfsc(original.getBankIfsc());
        duplicate.setBankBranch(original.getBankBranch());

        duplicate.setAdvancePercent(original.getAdvancePercent());
        duplicate.setMaterialPercent(original.getMaterialPercent());
        duplicate.setInstallationPercent(original.getInstallationPercent());
        duplicate.setBalancePercent(original.getBalancePercent());

        duplicate.setPlantModel(original.getPlantModel());
        duplicate.setPlantProductionCapacity(original.getPlantProductionCapacity());
        duplicate.setPlantBricksSize(original.getPlantBricksSize());
        duplicate.setPlantPalletSize(original.getPlantPalletSize());
        duplicate.setPlantShedArea(original.getPlantShedArea());
        duplicate.setPlantTotalLand(original.getPlantTotalLand());
        duplicate.setPlantConnectedPower(original.getPlantConnectedPower());
        duplicate.setPlantLabourRequirement(original.getPlantLabourRequirement());

        duplicate.setAdditionalNotes(original.getAdditionalNotes());
        duplicate.setTermsTemplateVersion(original.getTermsTemplateVersion());
        duplicate.setTermsCategoriesApplied(original.getTermsCategoriesApplied());

        duplicate.setItemsSubtotal(original.getItemsSubtotal());
        duplicate.setTaxableAmount(original.getTaxableAmount());
        duplicate.setGstPercent(original.getGstPercent());
        duplicate.setCgstPercent(original.getCgstPercent());
        duplicate.setCgstAmount(original.getCgstAmount());
        duplicate.setSgstPercent(original.getSgstPercent());
        duplicate.setSgstAmount(original.getSgstAmount());
        duplicate.setIgstPercent(original.getIgstPercent());
        duplicate.setIgstAmount(original.getIgstAmount());
        duplicate.setDiscountAmount(original.getDiscountAmount());
        duplicate.setGrandTotal(original.getGrandTotal());
        duplicate.setGrandTotalWords(original.getGrandTotalWords());

        Quotation savedDuplicate = quotationRepository.save(duplicate);

        for (QuotationItem originalItem : original.getItems()) {
            QuotationItem newItem = new QuotationItem();
            newItem.setQuotation(savedDuplicate);
            newItem.setProductId(originalItem.getProductId());
            newItem.setProduct(originalItem.getProduct());
            newItem.setName(originalItem.getName());
            newItem.setCategory(originalItem.getCategory());
            newItem.setSectionCode(originalItem.getSectionCode());
            newItem.setHsnCode(originalItem.getHsnCode());
            newItem.setGstRate(originalItem.getGstRate());
            newItem.setQty(originalItem.getQty());
            newItem.setUnit(originalItem.getUnit());
            newItem.setRate(originalItem.getRate());
            newItem.setAmount(originalItem.getAmount());
            newItem.setPowerHP(originalItem.getPowerHP());
            newItem.setPowerKW(originalItem.getPowerKW());
            newItem.setInCustomerScope(originalItem.getInCustomerScope());
            newItem.setShedSize(originalItem.getShedSize());
            newItem.setLabor(originalItem.getLabor());
            newItem.setProduction(originalItem.getProduction());
            newItem.setPower(originalItem.getPower());
            newItem.setImageUrl(originalItem.getImageUrl());
            newItem.setImage(originalItem.getImage());
            quotationItemRepository.save(newItem);
        }

        addHistoryEntry(savedDuplicate, "Created", "Duplicated from " + original.getQuoteNo());

        return toResponseDto(savedDuplicate);
    }

    @Override
    public void deleteQuotation(Long id, String authHeader) {
        Quotation quotation = quotationRepository.findById(id)
                .orElseThrow(() -> new QuotationException("Quotation not found with id: " + id));
        quotation.setDeletedAt(LocalDateTime.now());
        quotationRepository.save(quotation);
        logger.info("Quotation {} soft-deleted", quotation.getQuoteNo());
    }

    @Override
    public void processExpiredQuotations() {
        LocalDateTime now = LocalDateTime.now();
        List<Quotation> expiredQuotations = quotationRepository.findExpiredPendingQuotations(now);

        for (Quotation q : expiredQuotations) {
            q.setStatus("Expired");
            q.setUpdatedAt(LocalDateTime.now());
            quotationRepository.save(q);
            addHistoryEntry(q, "Expired", "Quotation expired automatically");
            logger.info("Quotation {} marked as expired", q.getQuoteNo());
        }
    }

    @Override
    public Map<String, Object> getDashboardStats() {
        Map<String, Object> stats = new HashMap<>();
        stats.put("total", quotationRepository.count());
        stats.put("today", quotationRepository.countByDate(LocalDate.now()));
        stats.put("value", quotationRepository.sumGrandTotal() != null ? quotationRepository.sumGrandTotal() : 0.0);
        stats.put("accepted", quotationRepository.countByStatus("Accepted"));
        stats.put("rejected", quotationRepository.countByStatus("Rejected"));
        stats.put("pending", quotationRepository.countByStatus("Pending"));
        return stats;
    }

    // ============================================================
    // PRIVATE HELPER METHODS
    // ============================================================

    private void validateQuotationRequest(QuotationRequestDto requestDto) {
        if (requestDto.getCustomer() == null) {
            throw new QuotationException("Customer details are required");
        }
        if (requestDto.getCustomer().getName() == null || requestDto.getCustomer().getName().isBlank()) {
            throw new QuotationException("Customer name is required");
        }
        if (requestDto.getCustomer().getMobilePrimary() == null || requestDto.getCustomer().getMobilePrimary().isBlank()) {
            throw new QuotationException("Customer mobile number is required");
        }
        if (requestDto.getItems() == null || requestDto.getItems().isEmpty()) {
            throw new QuotationException("At least one item is required");
        }
        for (QuotationRequestDto.ItemDto item : requestDto.getItems()) {
            if (item.getName() == null || item.getName().isBlank()) {
                throw new QuotationException("Item name is required");
            }
            if (item.getQty() == null || item.getQty() <= 0) {
                throw new QuotationException("Item quantity must be greater than 0");
            }
            if (item.getRate() == null || item.getRate() < 0) {
                throw new QuotationException("Item rate must be >= 0");
            }
        }
    }

    @SuppressWarnings("unchecked")
    private void applyTermsAndConditions(Quotation quotation, Map<String, Object> termsAndConditions) {
        if (termsAndConditions == null) return;
        Object version = termsAndConditions.get("templateVersion");
        if (version != null) {
            quotation.setTermsTemplateVersion(String.valueOf(version));
        }
        Object categories = termsAndConditions.get("categoriesApplied");
        if (categories != null) {
            try {
                quotation.setTermsCategoriesApplied(objectMapper.writeValueAsString(categories));
            } catch (JsonProcessingException e) {
                logger.warn("Could not serialize termsAndConditions.categoriesApplied: {}", e.getMessage());
            }
        }
    }

    private Map<String, Object> buildTermsAndConditionsMap(Quotation quotation) {
        Map<String, Object> tc = new LinkedHashMap<>();
        tc.put("templateVersion", quotation.getTermsTemplateVersion());
        List<Object> categories = new ArrayList<>();
        if (quotation.getTermsCategoriesApplied() != null && !quotation.getTermsCategoriesApplied().isBlank()) {
            try {
                categories = objectMapper.readValue(quotation.getTermsCategoriesApplied(),
                        new com.fasterxml.jackson.core.type.TypeReference<List<Object>>() {});
            } catch (Exception e) {
                categories = new ArrayList<>();
            }
        }
        tc.put("categoriesApplied", categories);
        return tc;
    }

    private Customer getOrCreateCustomer(QuotationRequestDto.CustomerDto customerDto) {
        if (customerDto == null || customerDto.getMobilePrimary() == null || customerDto.getMobilePrimary().isBlank()) {
            throw new QuotationException("Customer mobile number is required");
        }

        // Prefer an explicit customerId (sent when picked from Customer Management) —
        // this avoids mismatches if the mobile number was edited on the form.
        Optional<Customer> existing = (customerDto.getCustomerId() != null)
                ? customerRepository.findById(customerDto.getCustomerId())
                : Optional.empty();

        if (existing.isEmpty()) {
            existing = customerRepository.findByMobilePrimary(customerDto.getMobilePrimary());
        }

        if (existing.isPresent()) {
            Customer customer = existing.get();
            if (customerDto.getName() != null && !customerDto.getName().isBlank()) {
                customer.setName(customerDto.getName());
            }
            if (customerDto.getEmail() != null && !customerDto.getEmail().isBlank()) {
                customer.setEmail(customerDto.getEmail());
            }
            if (customerDto.getAddress() != null && !customerDto.getAddress().isBlank()) {
                customer.setAddress(customerDto.getAddress());
            }
            if (customerDto.getCity() != null && !customerDto.getCity().isBlank()) {
                customer.setCity(customerDto.getCity());
            }
            if (customerDto.getState() != null && !customerDto.getState().isBlank()) {
                customer.setState(customerDto.getState());
            }
            if (customerDto.getPincode() != null && !customerDto.getPincode().isBlank()) {
                customer.setPincode(customerDto.getPincode());
            }
            if (customerDto.getGst() != null && !customerDto.getGst().isBlank()) {
                customer.setGstin(customerDto.getGst());
            }
            customer.setUpdatedAt(LocalDateTime.now());
            return customerRepository.save(customer);
        }

        Customer newCustomer = new Customer();
        newCustomer.setCustomerCode("CUST-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase());
        newCustomer.setName(customerDto.getName());
        newCustomer.setMobilePrimary(customerDto.getMobilePrimary());
        newCustomer.setMobileSecondary(customerDto.getMobileSecondary());
        newCustomer.setEmail(customerDto.getEmail());
        newCustomer.setAddress(customerDto.getAddress());
        newCustomer.setCity(customerDto.getCity());
        newCustomer.setState(customerDto.getState());
        newCustomer.setPincode(customerDto.getPincode());
        newCustomer.setGstin(customerDto.getGst());
        newCustomer.setStatus("Active");
        newCustomer.setType("Quotation Customer");
        newCustomer.setCreatedAt(LocalDateTime.now());
        newCustomer.setUpdatedAt(LocalDateTime.now());
        newCustomer.setTotalOrders(0);
        newCustomer.setTotalBusiness(0.0);
        newCustomer.setCreated(LocalDate.now().toString());

        return customerRepository.save(newCustomer);
    }

    private List<QuotationItem> createQuotationItems(Quotation quotation, List<QuotationRequestDto.ItemDto> itemDtos) {
        List<QuotationItem> items = new ArrayList<>();
        if (itemDtos == null) return items;

        for (QuotationRequestDto.ItemDto dto : itemDtos) {
            QuotationItem item = new QuotationItem();
            item.setQuotation(quotation);
            item.setProductId(dto.getProductId());

            // Link to the actual catalog product when the id refers to a real product
            // (custom/manual items sent by the frontend won't have a numeric catalog id).
            if (dto.getProductId() != null && !dto.getProductId().isBlank()) {
                try {
                    Long pid = Long.parseLong(dto.getProductId());
                    productRepository.findById(pid).ifPresent(item::setProduct);
                } catch (NumberFormatException ignored) {
                    // not a catalog product — leave item.product null, productId string kept as-is
                }
            }

            item.setName(dto.getName());
            item.setCategory(dto.getCategory());
            item.setSectionCode(dto.getSectionCode());
            item.setHsnCode(dto.getHsnCode());
            item.setGstRate(dto.getGstRate() != null ? dto.getGstRate() : 18.0);
            item.setQty(dto.getQty() != null ? dto.getQty().intValue() : 1);
            item.setRate(dto.getRate() != null ? dto.getRate() : 0.0);
            item.setAmount(item.getQty() * item.getRate());
            item.setPowerHP(dto.getPowerHP());
            item.setPowerKW(dto.getPowerKW());
            item.setInCustomerScope(dto.getInCustomerScope() != null ? dto.getInCustomerScope() : false);
            item.setShedSize(dto.getShedSize());
            item.setLabor(dto.getLabor());
            item.setProduction(dto.getProduction());
            item.setPower(dto.getPower());
            item.setImageUrl(dto.getImageUrl());
            items.add(item);
        }
        return items;
    }

    private void calculateTotals(Quotation quotation, QuotationRequestDto requestDto) {
        double itemsSubtotal = 0.0;
        double totalPowerHP = 0.0;
        double totalPowerKW = 0.0;

        for (QuotationRequestDto.ItemDto item : requestDto.getItems()) {
            double qty = item.getQty() != null ? item.getQty() : 0;
            double rate = item.getRate() != null ? item.getRate() : 0;
            boolean inCustomerScope = item.getInCustomerScope() != null && item.getInCustomerScope();
            if (!inCustomerScope) {
                itemsSubtotal += qty * rate;
            }
            if (item.getPowerHP() != null) {
                totalPowerHP += item.getPowerHP() * qty;
            }
            if (item.getPowerKW() != null) {
                totalPowerKW += item.getPowerKW() * qty;
            }
        }

        quotation.setItemsSubtotal(itemsSubtotal);
        quotation.setTotalPowerHP(totalPowerHP);
        quotation.setTotalPowerKW(totalPowerKW);

        double transport = quotation.getTransportCharge() != null ? quotation.getTransportCharge() : 0.0;
        double loading = quotation.getLoadingCharge() != null ? quotation.getLoadingCharge() : 0.0;
        double other = quotation.getOtherCharge() != null ? quotation.getOtherCharge() : 0.0;

        double subtotal = itemsSubtotal + transport + loading + other;

        double discountValue = quotation.getDiscountValue() != null ? quotation.getDiscountValue() : 0.0;
        String discountType = quotation.getDiscountType() != null ? quotation.getDiscountType() : "percent";
        double discountAmount = 0.0;

        if ("percent".equals(discountType)) {
            discountAmount = subtotal * Math.min(Math.max(discountValue, 0), 100) / 100;
        } else {
            discountAmount = Math.min(Math.max(discountValue, 0), subtotal);
        }
        quotation.setDiscountAmount(discountAmount);

        double taxable = Math.max(0, subtotal - discountAmount);
        quotation.setTaxableAmount(taxable);

        double gstPercent = quotation.getGstPercent() != null ? quotation.getGstPercent() : 18.0;
        boolean isInterState = quotation.getIsInterState() != null && quotation.getIsInterState();

        if (isInterState) {
            double igstAmount = taxable * (gstPercent / 100);
            quotation.setIgstPercent(gstPercent);
            quotation.setIgstAmount(igstAmount);
            quotation.setCgstPercent(0.0);
            quotation.setCgstAmount(0.0);
            quotation.setSgstPercent(0.0);
            quotation.setSgstAmount(0.0);
        } else {
            double cgstPercent = gstPercent / 2;
            double sgstPercent = gstPercent / 2;
            quotation.setCgstPercent(cgstPercent);
            quotation.setCgstAmount(taxable * (cgstPercent / 100));
            quotation.setSgstPercent(sgstPercent);
            quotation.setSgstAmount(taxable * (sgstPercent / 100));
            quotation.setIgstPercent(0.0);
            quotation.setIgstAmount(0.0);
        }

        double totalTax = (quotation.getCgstAmount() != null ? quotation.getCgstAmount() : 0.0) +
                (quotation.getSgstAmount() != null ? quotation.getSgstAmount() : 0.0) +
                (quotation.getIgstAmount() != null ? quotation.getIgstAmount() : 0.0);

        double grandTotal = taxable + totalTax;
        quotation.setGrandTotal(grandTotal);
        quotation.setGrandTotalWords(numberToWordsIndian(grandTotal));
    }

    private String numberToWordsIndian(double num) {
        long whole = Math.round(num);
        if (whole == 0) return "Zero Rupees Only";

        String[] ones = {"", "One", "Two", "Three", "Four", "Five", "Six", "Seven", "Eight", "Nine", "Ten",
                "Eleven", "Twelve", "Thirteen", "Fourteen", "Fifteen", "Sixteen", "Seventeen", "Eighteen", "Nineteen"};
        String[] tens = {"", "", "Twenty", "Thirty", "Forty", "Fifty", "Sixty", "Seventy", "Eighty", "Ninety"};

        String result = "";
        long crore = whole / 10000000;
        whole %= 10000000;
        long lakh = whole / 100000;
        whole %= 100000;
        long thousand = whole / 1000;
        whole %= 1000;
        long rest = whole;

        if (crore > 0) result += convertToWords(crore, ones, tens) + " Crore ";
        if (lakh > 0) result += convertToWords(lakh, ones, tens) + " Lakh ";
        if (thousand > 0) result += convertToWords(thousand, ones, tens) + " Thousand ";
        if (rest > 0) result += convertToWords(rest, ones, tens);

        return result.trim() + " Rupees Only";
    }

    private String convertToWords(long n, String[] ones, String[] tens) {
        if (n < 20) return ones[(int) n];
        if (n < 100) return tens[(int) (n / 10)] + (n % 10 > 0 ? " " + ones[(int) (n % 10)] : "");
        if (n < 1000) return ones[(int) (n / 100)] + " Hundred" + (n % 100 > 0 ? " " + convertToWords(n % 100, ones, tens) : "");
        return "";
    }

    private void logStatusChange(Quotation quotation, String oldStatus, String newStatus) {
        QuotationStatusLog log = new QuotationStatusLog();
        log.setQuotationId(quotation.getId());
        log.setOldStatus(oldStatus);
        log.setNewStatus(newStatus);
        log.setChangedBy(ADMIN);
        log.setChangedAt(LocalDateTime.now());
        statusLogRepository.save(log);
    }

    private QuotationResponseDto toResponseDto(Quotation quotation) {
        QuotationResponseDto response = new QuotationResponseDto();
        response.setId(quotation.getId());
        response.setQuoteNo(quotation.getQuoteNo());
        response.setDate(quotation.getDate() != null ? quotation.getDate().toString() : null);
        response.setStatus(quotation.getStatus());
        response.setAmount(quotation.getGrandTotal());
        response.setTotal(quotation.getGrandTotal());
        response.setItemsTotal(quotation.getItemsSubtotal());
        response.setGstPercent(quotation.getGstPercent());
        response.setDiscountType(quotation.getDiscountType());
        response.setDiscountValue(quotation.getDiscountValue());
        response.setDiscountAmount(quotation.getDiscountAmount());
        response.setTaxable(quotation.getTaxableAmount());
        response.setSubtotal(quotation.getItemsSubtotal());
        response.setDeliveryTimeline(quotation.getDeliveryTimeline());
        response.setValidUntil(quotation.getValidUntil() != null ? quotation.getValidUntil().toLocalDate().toString() : null);
        response.setPaymentType(quotation.getPaymentType());
        response.setIsInterState(quotation.getIsInterState());
        response.setCreatedAt(quotation.getCreatedAt());
        response.setUpdatedAt(quotation.getUpdatedAt());
        response.setAdditionalNotes(quotation.getAdditionalNotes());
        response.setTermsAndConditions(buildTermsAndConditionsMap(quotation));

        if (quotation.getCustomer() != null) {
            QuotationResponseDto.CustomerDto customer = new QuotationResponseDto.CustomerDto();
            customer.setName(quotation.getCustomer().getName());
            customer.setMobilePrimary(quotation.getCustomer().getMobilePrimary());
            customer.setMobileSecondary(quotation.getCustomer().getMobileSecondary());
            customer.setEmail(quotation.getCustomer().getEmail());
            customer.setAddress(quotation.getCustomer().getAddress());
            customer.setCity(quotation.getCustomer().getCity());
            customer.setState(quotation.getCustomer().getState());
            customer.setPincode(quotation.getCustomer().getPincode());
            customer.setGst(quotation.getCustomer().getGstin());
            response.setCustomer(customer);
        }

        QuotationResponseDto.GstBreakupDto gstBreakup = new QuotationResponseDto.GstBreakupDto();
        gstBreakup.setCgstPercent(quotation.getCgstPercent());
        gstBreakup.setCgstAmount(quotation.getCgstAmount());
        gstBreakup.setSgstPercent(quotation.getSgstPercent());
        gstBreakup.setSgstAmount(quotation.getSgstAmount());
        gstBreakup.setIgstPercent(quotation.getIgstPercent());
        gstBreakup.setIgstAmount(quotation.getIgstAmount());
        response.setGstBreakup(gstBreakup);

        QuotationResponseDto.PaymentTermsDto paymentTerms = new QuotationResponseDto.PaymentTermsDto();
        paymentTerms.setAdvance(quotation.getAdvancePercent());
        paymentTerms.setMaterial(quotation.getMaterialPercent());
        paymentTerms.setInstallation(quotation.getInstallationPercent());
        paymentTerms.setBalance(quotation.getBalancePercent());
        response.setPaymentTerms(paymentTerms);

        QuotationResponseDto.BankDto bank = new QuotationResponseDto.BankDto();
        bank.setAccountName(quotation.getBankAccountName());
        bank.setBankName(quotation.getBankName());
        bank.setAccountNumber(quotation.getBankAccountNumber());
        bank.setIfscCode(quotation.getBankIfsc());
        bank.setBranch(quotation.getBankBranch());
        response.setBank(bank);

        QuotationResponseDto.CostsDto costs = new QuotationResponseDto.CostsDto();
        costs.setTransport(quotation.getTransportCharge());
        costs.setLoading(quotation.getLoadingCharge());
        costs.setOtherLabel(quotation.getOtherChargeLabel());
        costs.setOther(quotation.getOtherCharge());
        response.setCosts(costs);

        List<QuotationResponseDto.ItemDto> items = new ArrayList<>();
        for (QuotationItem item : quotation.getItems()) {
            QuotationResponseDto.ItemDto itemDto = new QuotationResponseDto.ItemDto();
            itemDto.setId(String.valueOf(item.getId()));
            itemDto.setProductId(item.getProductId());
            itemDto.setName(item.getName());
            itemDto.setCategory(item.getCategory());
            itemDto.setQty(Double.valueOf(item.getQty()));
            itemDto.setRate(item.getRate());
            itemDto.setAmount(item.getAmount());
            itemDto.setHsnCode(item.getHsnCode());
            itemDto.setGstRate(item.getGstRate());
            itemDto.setPowerHP(item.getPowerHP());
            itemDto.setPowerKW(item.getPowerKW());
            itemDto.setInCustomerScope(item.getInCustomerScope());
            itemDto.setShedSize(item.getShedSize());
            itemDto.setLabor(item.getLabor());
            itemDto.setProduction(item.getProduction());
            itemDto.setImageUrl(item.getImageUrl());
            items.add(itemDto);
        }
        response.setItems(items);

        QuotationResponseDto.PlantOverviewDto plantOverview = new QuotationResponseDto.PlantOverviewDto();
        plantOverview.setModel(quotation.getPlantModel());
        plantOverview.setProductionCapacity(quotation.getPlantProductionCapacity());
        plantOverview.setBricksSize(quotation.getPlantBricksSize());
        plantOverview.setPalletSize(quotation.getPlantPalletSize());
        plantOverview.setRequiredShedArea(quotation.getPlantShedArea());
        plantOverview.setTotalLand(quotation.getPlantTotalLand());
        plantOverview.setConnectedPower(quotation.getPlantConnectedPower());
        plantOverview.setLabourRequirement(quotation.getPlantLabourRequirement());
        response.setPlantOverview(plantOverview);

        QuotationResponseDto.ApprovalDto approval = new QuotationResponseDto.ApprovalDto();
        approval.setApprovedBy(quotation.getApprovedBy());
        approval.setApprovalDate(quotation.getApprovalDate() != null ? quotation.getApprovalDate().toString() : null);
        approval.setNotes(quotation.getApprovalNotes());
        response.setApproval(approval);

        if (quotation.getHistory() != null && !quotation.getHistory().isEmpty()) {
            try {
                List<Map<String, Object>> history = objectMapper.readValue(quotation.getHistory(), List.class);
                response.setHistory(history);
            } catch (Exception e) {
                response.setHistory(new ArrayList<>());
            }
        } else {
            response.setHistory(new ArrayList<>());
        }

        return response;
    }

    private QuotationListResponseDto toListResponseDto(Quotation quotation) {
        QuotationListResponseDto dto = new QuotationListResponseDto();
        dto.setId(quotation.getId());
        dto.setQuoteNo(quotation.getQuoteNo());
        dto.setDate(quotation.getDate() != null ? quotation.getDate().toString() : null);
        dto.setStatus(quotation.getStatus());
        dto.setAmount(quotation.getGrandTotal());
        if (quotation.getCustomer() != null) {
            dto.setCustomerName(quotation.getCustomer().getName());
            dto.setCustomerMobile(quotation.getCustomer().getMobilePrimary());
            dto.setCustomerEmail(quotation.getCustomer().getEmail());
        }
        return dto;
    }

    // ============================================================
    // SINGLE addHistoryEntry METHOD (KEEP ONLY THIS ONE)
    // ============================================================
    private void addHistoryEntry(Quotation quotation, String action, String details) {
        try {
            List<Map<String, Object>> history = new ArrayList<>();
            if (quotation.getHistory() != null && !quotation.getHistory().isEmpty()) {
                try {
                    // Use TypeReference for type safety
                    history = objectMapper.readValue(quotation.getHistory(),
                            new com.fasterxml.jackson.core.type.TypeReference<List<Map<String, Object>>>() {});
                } catch (Exception e) {
                    history = new ArrayList<>();
                }
            }

            Map<String, Object> entry = new LinkedHashMap<>();
            entry.put("ts", LocalDateTime.now().toString());
            entry.put("action", action);
            entry.put("by", ADMIN);
            entry.put("details", details);
            history.add(0, entry);

            if (history.size() > 100) {
                history = history.subList(0, 100);
            }

            quotation.setHistory(objectMapper.writeValueAsString(history));
            quotationRepository.save(quotation);

        } catch (JsonProcessingException e) {
            logger.error("Error adding history entry: {}", e.getMessage());
        }
    }
}