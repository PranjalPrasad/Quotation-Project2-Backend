package com.quo.quotation2.repository;

import com.quo.quotation2.entity.Quotation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public interface QuotationRepository extends JpaRepository<Quotation, Long> {
    Optional<Quotation> findByQuoteNo(String quoteNo);

    @Query("SELECT q FROM Quotation q WHERE q.deletedAt IS NULL " +
            "AND (:status IS NULL OR q.status = :status) " +
            "AND (:dateFrom IS NULL OR q.createdAt >= :dateFrom) " +
            "AND (:dateTo IS NULL OR q.createdAt <= :dateTo) " +
            "AND (:customerId IS NULL OR q.customer.id = :customerId)")
    Page<Quotation> findWithFilters(@Param("status") String status,
                                    @Param("dateFrom") LocalDateTime dateFrom,
                                    @Param("dateTo") LocalDateTime dateTo,
                                    @Param("customerId") Long customerId,
                                    Pageable pageable);

    @Query("SELECT MAX(CAST(SUBSTRING(q.quoteNo, 4) AS integer)) FROM Quotation q WHERE q.quoteNo LIKE 'SQ-%'")
    Integer findMaxQuoteNumber();

    @Query("SELECT q FROM Quotation q WHERE q.deletedAt IS NULL AND q.status = 'Pending' AND q.validUntil < :now")
    java.util.List<Quotation> findExpiredPendingQuotations(@Param("now") LocalDateTime now);
}
