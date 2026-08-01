package com.quo.quotation2.scheduler;

import com.quo.quotation2.service.QuotationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class ExpiredQuotationScheduler {

    private static final Logger logger = LoggerFactory.getLogger(ExpiredQuotationScheduler.class);
    private final QuotationService quotationService;

    public ExpiredQuotationScheduler(QuotationService quotationService) {
        this.quotationService = quotationService;
    }

    // Run every hour
    @Scheduled(cron = "0 0 * * * *")
    public void processExpiredQuotations() {
        logger.info("Starting expired quotations processing...");
        try {
            quotationService.processExpiredQuotations();
            logger.info("Expired quotations processed successfully");
        } catch (Exception e) {
            logger.error("Error processing expired quotations: {}", e.getMessage(), e);
        }
    }
}
