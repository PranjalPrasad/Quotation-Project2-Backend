package com.quo.quotation2.service.serviceImpl;

import com.quo.quotation2.dto.requestdto.ConvertToInvoiceRequestDto;
import com.quo.quotation2.dto.requestdto.QuotationRequestDto;
import com.quo.quotation2.dto.requestdto.StatusUpdateRequestDto;
import com.quo.quotation2.dto.responsedto.PageResponseDto;
import com.quo.quotation2.dto.responsedto.QuotationListResponseDto;
import com.quo.quotation2.dto.responsedto.QuotationResponseDto;
import com.quo.quotation2.service.QuotationService;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class QuotationServiceImpl implements QuotationService {

    // In-memory storage for quotations (replace with database later)
    private final Map<Long, QuotationRequestDto> quotations = new HashMap<>();
    private Long idCounter = 1L;

    @Override
    public QuotationResponseDto createQuotation(QuotationRequestDto requestDto, String authHeader) {
        Long id = idCounter++;
        quotations.put(id, requestDto);

        // Convert to response
        QuotationResponseDto response = new QuotationResponseDto();
        response.setId(id);
        response.setQuoteNo(requestDto.getQuoteNo());
        response.setDate(requestDto.getDate());
        response.setStatus(requestDto.getStatus());
        response.setAmount(requestDto.getAmount());
        response.setTotal(requestDto.getTotal());
        response.setItemsTotal(requestDto.getItemsTotal());
        response.setCreatedAt(LocalDateTime.now());
        response.setUpdatedAt(LocalDateTime.now());

        // Copy customer
        if (requestDto.getCustomer() != null) {
            QuotationResponseDto.CustomerDto customer = new QuotationResponseDto.CustomerDto();
            customer.setName(requestDto.getCustomer().getName());
            customer.setMobilePrimary(requestDto.getCustomer().getMobilePrimary());
            customer.setMobileSecondary(requestDto.getCustomer().getMobileSecondary());
            customer.setEmail(requestDto.getCustomer().getEmail());
            customer.setAddress(requestDto.getCustomer().getAddress());
            customer.setCity(requestDto.getCustomer().getCity());
            customer.setState(requestDto.getCustomer().getState());
            customer.setPincode(requestDto.getCustomer().getPincode());
            customer.setGst(requestDto.getCustomer().getGst());
            response.setCustomer(customer);
        }

        // Copy items
        if (requestDto.getItems() != null) {
            List<QuotationResponseDto.ItemDto> items = new ArrayList<>();
            for (QuotationRequestDto.ItemDto item : requestDto.getItems()) {
                QuotationResponseDto.ItemDto responseItem = new QuotationResponseDto.ItemDto();
                responseItem.setProductId(item.getProductId());
                responseItem.setName(item.getName());
                responseItem.setCategory(item.getCategory());
                responseItem.setQty(item.getQty());
                responseItem.setRate(item.getRate());
                responseItem.setAmount(item.getAmount());
                responseItem.setHsnCode(item.getHsnCode());
                responseItem.setGstRate(item.getGstRate());
                responseItem.setPowerHP(item.getPowerHP());
                responseItem.setPowerKW(item.getPowerKW());
                responseItem.setInCustomerScope(item.getInCustomerScope());
                responseItem.setShedSize(item.getShedSize());
                responseItem.setLabor(item.getLabor());
                responseItem.setProduction(item.getProduction());
                responseItem.setImageUrl(item.getImageUrl());
                items.add(responseItem);
            }
            response.setItems(items);
        }

        // Copy costs
        if (requestDto.getCosts() != null) {
            QuotationResponseDto.CostsDto costs = new QuotationResponseDto.CostsDto();
            costs.setTransport(requestDto.getCosts().getTransport());
            costs.setLoading(requestDto.getCosts().getLoading());
            costs.setOtherLabel(requestDto.getCosts().getOtherLabel());
            costs.setOther(requestDto.getCosts().getOther());
            response.setCosts(costs);
        }

        response.setGstPercent(requestDto.getGstPercent());
        response.setDiscountType(requestDto.getDiscountType());
        response.setDiscountValue(requestDto.getDiscountValue());
        response.setSubtotal(requestDto.getSubtotal());
        response.setDiscountAmount(requestDto.getDiscountAmount());
        response.setTaxable(requestDto.getTaxable());
        response.setDeliveryTimeline(requestDto.getDeliveryTimeline());
        response.setValidUntil(requestDto.getValidUntil());
        response.setPaymentType(requestDto.getPaymentType());

        // Copy payment terms
        if (requestDto.getPaymentTerms() != null) {
            QuotationResponseDto.PaymentTermsDto paymentTerms = new QuotationResponseDto.PaymentTermsDto();
            paymentTerms.setAdvance(requestDto.getPaymentTerms().getAdvance());
            paymentTerms.setMaterial(requestDto.getPaymentTerms().getMaterial());
            paymentTerms.setInstallation(requestDto.getPaymentTerms().getInstallation());
            paymentTerms.setBalance(requestDto.getPaymentTerms().getBalance());
            response.setPaymentTerms(paymentTerms);
        }

        // Copy bank details
        if (requestDto.getBank() != null) {
            QuotationResponseDto.BankDto bank = new QuotationResponseDto.BankDto();
            bank.setAccountName(requestDto.getBank().getAccountName());
            bank.setBankName(requestDto.getBank().getBankName());
            bank.setAccountNumber(requestDto.getBank().getAccountNumber());
            bank.setIfscCode(requestDto.getBank().getIfscCode());
            bank.setBranch(requestDto.getBank().getBranch());
            response.setBank(bank);
        }

        response.setTermsAndConditions(requestDto.getTermsAndConditions());
        response.setAdditionalNotes(requestDto.getAdditionalNotes());

        // Copy product images
        if (requestDto.getProductImages() != null) {
            List<QuotationResponseDto.ProductImageDto> images = new ArrayList<>();
            for (QuotationRequestDto.ProductImageDto img : requestDto.getProductImages()) {
                QuotationResponseDto.ProductImageDto responseImg = new QuotationResponseDto.ProductImageDto();
                responseImg.setProductId(img.getProductId());
                responseImg.setProductName(img.getProductName());
                responseImg.setImageUrl(img.getImageUrl());
                images.add(responseImg);
            }
            response.setProductImages(images);
        }

        return response;
    }

    @Override
    public QuotationResponseDto getQuotation(Long id) {
        QuotationRequestDto dto = quotations.get(id);
        if (dto == null) {
            return null;
        }
        QuotationResponseDto response = new QuotationResponseDto();
        response.setId(id);
        response.setQuoteNo(dto.getQuoteNo());
        response.setDate(dto.getDate());
        response.setStatus(dto.getStatus());
        response.setAmount(dto.getAmount());
        response.setTotal(dto.getTotal());
        return response;
    }

    @Override
    public QuotationResponseDto getQuotationByQuoteNo(String quoteNo) {
        for (Map.Entry<Long, QuotationRequestDto> entry : quotations.entrySet()) {
            if (entry.getValue().getQuoteNo().equals(quoteNo)) {
                QuotationResponseDto response = new QuotationResponseDto();
                response.setId(entry.getKey());
                response.setQuoteNo(entry.getValue().getQuoteNo());
                response.setDate(entry.getValue().getDate());
                response.setStatus(entry.getValue().getStatus());
                response.setAmount(entry.getValue().getAmount());
                response.setTotal(entry.getValue().getTotal());
                return response;
            }
        }
        return null;
    }

    @Override
    public PageResponseDto<QuotationListResponseDto> getQuotations(String status, String dateFrom, String dateTo, Long customerId, Pageable pageable) {
        List<QuotationListResponseDto> list = new ArrayList<>();

        for (Map.Entry<Long, QuotationRequestDto> entry : quotations.entrySet()) {
            QuotationRequestDto dto = entry.getValue();

            if (status != null && !status.isEmpty() && !status.equals(dto.getStatus())) {
                continue;
            }

            QuotationListResponseDto item = new QuotationListResponseDto();
            item.setId(entry.getKey());
            item.setQuoteNo(dto.getQuoteNo());
            item.setDate(dto.getDate());
            item.setStatus(dto.getStatus());
            item.setAmount(dto.getAmount());
            if (dto.getCustomer() != null) {
                item.setCustomerName(dto.getCustomer().getName());
                item.setCustomerMobile(dto.getCustomer().getMobilePrimary());
                item.setCustomerEmail(dto.getCustomer().getEmail());
            }
            list.add(item);
        }

        int start = (int) pageable.getOffset();
        int end = Math.min(start + pageable.getPageSize(), list.size());
        List<QuotationListResponseDto> pageContent = start < list.size() ? list.subList(start, end) : new ArrayList<>();

        return new PageResponseDto<>(pageContent, pageable.getPageNumber(), pageable.getPageSize(), list.size());
    }

    @Override
    public QuotationResponseDto updateQuotation(Long id, QuotationRequestDto requestDto, String authHeader) {
        if (!quotations.containsKey(id)) {
            return null;
        }
        quotations.put(id, requestDto);
        return getQuotation(id);
    }

    @Override
    public QuotationResponseDto updateQuotationStatus(Long id, StatusUpdateRequestDto requestDto, String authHeader) {
        QuotationRequestDto dto = quotations.get(id);
        if (dto == null) {
            return null;
        }
        dto.setStatus(requestDto.getStatus());
        quotations.put(id, dto);
        return getQuotation(id);
    }

    @Override
    public QuotationResponseDto convertToInvoice(Long id, ConvertToInvoiceRequestDto requestDto, String authHeader) {
        QuotationRequestDto dto = quotations.get(id);
        if (dto == null) {
            return null;
        }
        dto.setStatus("INVOICED");
        quotations.put(id, dto);
        return getQuotation(id);
    }

    @Override
    public QuotationResponseDto duplicateQuotation(Long id, String authHeader) {
        QuotationRequestDto dto = quotations.get(id);
        if (dto == null) {
            return null;
        }
        Long newId = idCounter++;
        QuotationRequestDto newDto = dto;
        quotations.put(newId, newDto);
        return getQuotation(newId);
    }

    @Override
    public void deleteQuotation(Long id, String authHeader) {
        quotations.remove(id);
    }

    @Override
    public void processExpiredQuotations() {
        // Get today's date
        String today = LocalDate.now().format(DateTimeFormatter.ISO_LOCAL_DATE);

        List<Long> expiredIds = new ArrayList<>();

        for (Map.Entry<Long, QuotationRequestDto> entry : quotations.entrySet()) {
            QuotationRequestDto dto = entry.getValue();

            // Check if quotation is expired (validUntil < today) and status is not already expired
            if (dto.getValidUntil() != null && dto.getValidUntil().compareTo(today) < 0) {
                if (!"Expired".equals(dto.getStatus()) && !"Rejected".equals(dto.getStatus())) {
                    expiredIds.add(entry.getKey());
                }
            }
        }

        // Update expired quotations
        for (Long id : expiredIds) {
            QuotationRequestDto dto = quotations.get(id);
            if (dto != null) {
                dto.setStatus("Expired");
                quotations.put(id, dto);
                System.out.println("Quotation " + dto.getQuoteNo() + " marked as expired.");
            }
        }

        if (!expiredIds.isEmpty()) {
            System.out.println("Processed " + expiredIds.size() + " expired quotations.");
        }
    }
}