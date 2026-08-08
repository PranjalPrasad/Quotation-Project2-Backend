package com.quo.quotation2.repository;

import com.quo.quotation2.entity.Quotation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface DashboardRepository extends JpaRepository<Quotation, Long> {

    // ============================================================
    // Customer Count
    // ============================================================

    @Query("SELECT COUNT(c) FROM Customer c")
    Integer countAllCustomers();

    // ============================================================
    // Count Queries
    // ============================================================

    @Query("SELECT COUNT(q) FROM Quotation q")
    Integer countAllQuotations();

    @Query("SELECT COUNT(q) FROM Quotation q WHERE q.quoteDate BETWEEN :start AND :end")
    Integer countQuotationsBetweenDates(@Param("start") LocalDateTime start, @Param("end") LocalDateTime end);

    @Query("SELECT COUNT(q) FROM Quotation q WHERE q.status = :status")
    Integer countByStatus(@Param("status") String status);

    @Query("SELECT COUNT(q) FROM Quotation q WHERE q.status = 'Accepted' OR q.status = 'ACCEPTED'")
    Integer countAcceptedQuotations();

    // ============================================================
    // Sum Queries
    // ============================================================

    @Query("SELECT COALESCE(SUM(q.grandTotal), 0) FROM Quotation q")
    Double sumAllQuotationValues();

    @Query("SELECT COALESCE(SUM(q.grandTotal), 0) FROM Quotation q WHERE q.quoteDate BETWEEN :start AND :end")
    Double sumQuotationValuesBetweenDates(@Param("start") LocalDateTime start, @Param("end") LocalDateTime end);

    // ============================================================
    // Top Machines - Using qi.name from quotation_items
    // ============================================================

    @Query("SELECT qi.name as model, COUNT(qi) as units, SUM(qi.amount) as revenue " +
            "FROM Quotation q JOIN q.items qi " +
            "WHERE q.quoteDate BETWEEN :start AND :end " +
            "AND (q.status = 'Accepted' OR q.status = 'ACCEPTED') " +
            "GROUP BY qi.name " +
            "ORDER BY units DESC")
    List<Object[]> findTopMachinesByMonth(@Param("start") LocalDateTime start, @Param("end") LocalDateTime end);

    // ============================================================
    // Recent Quotations
    // ============================================================

    @Query("SELECT q FROM Quotation q ORDER BY q.quoteDate DESC LIMIT :limit")
    List<Quotation> findRecentQuotationsWithLimit(@Param("limit") int limit);

    // ============================================================
    // Monthly Trend
    // ============================================================

    @Query("SELECT MONTH(q.quoteDate) as month, YEAR(q.quoteDate) as year, " +
            "COALESCE(SUM(q.grandTotal), 0) as total, COUNT(q) as count " +
            "FROM Quotation q " +
            "WHERE q.quoteDate >= :startDate " +
            "AND (q.status = 'Accepted' OR q.status = 'ACCEPTED') " +
            "GROUP BY YEAR(q.quoteDate), MONTH(q.quoteDate) " +
            "ORDER BY year DESC, month DESC")
    List<Object[]> getMonthlyQuotationTrend(@Param("startDate") LocalDateTime startDate);

    // ============================================================
    // Category Revenue - Using qi.name from quotation_items
    // ============================================================

    @Query("SELECT qi.name, COALESCE(SUM(qi.amount), 0) as revenue, COUNT(qi) as count " +
            "FROM Quotation q JOIN q.items qi " +
            "WHERE (q.status = 'Accepted' OR q.status = 'ACCEPTED') " +
            "GROUP BY qi.name " +
            "ORDER BY revenue DESC")
    List<Object[]> getCategoryRevenue();
}

