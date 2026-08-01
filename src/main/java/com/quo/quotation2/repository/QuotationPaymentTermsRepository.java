package com.quo.quotation2.repository;

import com.quo.quotation2.entity.QuotationPaymentTerms;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface QuotationPaymentTermsRepository extends JpaRepository<QuotationPaymentTerms, Long> {

    Optional<QuotationPaymentTerms> findByQuotationId(Long quotationId);

    @Modifying
    @Transactional
    @Query("DELETE FROM QuotationPaymentTerms qpt WHERE qpt.quotation.id = :quotationId")
    void deleteByQuotationId(@Param("quotationId") Long quotationId);
}
