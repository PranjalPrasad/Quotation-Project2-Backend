package com.quo.quotation2.repository;

import com.quo.quotation2.entity.QuotationItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QuotationItemRepository extends JpaRepository<QuotationItem, Long> {
    void deleteByQuotationId(Long quotationId);
}
