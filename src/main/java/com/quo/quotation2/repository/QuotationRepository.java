package com.quo.quotation2.repository;

import com.quo.quotation2.entity.Quotation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
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
    List<Quotation> findExpiredPendingQuotations(@Param("now") LocalDateTime now);

    long countByStatus(String status);

    long countByDate(LocalDate date);

    @Query("SELECT SUM(q.grandTotal) FROM Quotation q WHERE q.deletedAt IS NULL")
    Double sumGrandTotal();

    // ---- Reports: fetch quotations (with items, to avoid N+1) in a date range ----
    // Pass dateFrom/dateTo = null to mean "no lower/upper bound".
    @Query("SELECT DISTINCT q FROM Quotation q LEFT JOIN FETCH q.items " +
            "WHERE q.deletedAt IS NULL " +
            "AND (:dateFrom IS NULL OR q.date >= :dateFrom) " +
            "AND (:dateTo IS NULL OR q.date <= :dateTo) " +
            "ORDER BY q.date DESC")
    List<Quotation> findForReport(@Param("dateFrom") LocalDate dateFrom,
                                  @Param("dateTo") LocalDate dateTo);
}