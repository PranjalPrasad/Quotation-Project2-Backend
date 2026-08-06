package com.quo.quotation2.repository;

import com.quo.quotation2.entity.QuotationItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface QuotationItemRepository extends JpaRepository<QuotationItem, Long> {

    @Modifying
    @Transactional
    @Query("DELETE FROM QuotationItem qi WHERE qi.quotation.id = :quotationId")
    void deleteByQuotationId(@Param("quotationId") Long quotationId);
}