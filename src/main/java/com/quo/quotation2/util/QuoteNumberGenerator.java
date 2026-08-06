package com.quo.quotation2.util;

import com.quo.quotation2.repository.QuotationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class QuoteNumberGenerator {

    @Autowired
    private QuotationRepository quotationRepository;

    public String generateNext() {
        Integer maxNumber = quotationRepository.findMaxQuoteNumber();
        int nextNumber = 1001;
        if (maxNumber != null) {
            nextNumber = maxNumber + 1;
        }
        return "SQ-" + nextNumber;
    }
}
