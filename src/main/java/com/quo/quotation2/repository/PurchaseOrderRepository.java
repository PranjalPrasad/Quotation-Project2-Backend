package com.quo.quotation2.repository;

import com.quo.quotation2.entity.PurchaseOrderEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface PurchaseOrderRepository extends JpaRepository<PurchaseOrderEntity, Long> {

    // Find by PO Number
    Optional<PurchaseOrderEntity> findByPoNo(String poNo);

    // Find by Status
    List<PurchaseOrderEntity> findByStatus(String status);

    // Find by Supplier ID
    List<PurchaseOrderEntity> findBySupplierId(Long supplierId);

    // Find by Date Range
    List<PurchaseOrderEntity> findByPoDateBetween(LocalDate startDate, LocalDate endDate);

    // Find by Status and Date Range
    List<PurchaseOrderEntity> findByStatusAndPoDateBetween(String status, LocalDate startDate, LocalDate endDate);

    // ✅ ADD THIS METHOD - Find top by ID descending (for generating PO number)
    Optional<PurchaseOrderEntity> findTopByOrderByIdDesc();

    // Paginated search with filters
    @Query("SELECT p FROM PurchaseOrderEntity p WHERE " +
            "(:status IS NULL OR p.status = :status) AND " +
            "(:supplierId IS NULL OR p.supplier.id = :supplierId) AND " +
            "(:startDate IS NULL OR p.poDate >= :startDate) AND " +
            "(:endDate IS NULL OR p.poDate <= :endDate) AND " +
            "(:search IS NULL OR LOWER(p.poNo) LIKE LOWER(CONCAT('%', :search, '%')) OR LOWER(p.supplier.name) LIKE LOWER(CONCAT('%', :search, '%')))")
    Page<PurchaseOrderEntity> findWithFilters(@Param("status") String status,
                                              @Param("supplierId") Long supplierId,
                                              @Param("startDate") LocalDate startDate,
                                              @Param("endDate") LocalDate endDate,
                                              @Param("search") String search,
                                              Pageable pageable);

    // Count by Status
    @Query("SELECT COUNT(p) FROM PurchaseOrderEntity p WHERE p.status = :status")
    Long countByStatus(@Param("status") String status);

    // Get total purchase value
    @Query("SELECT COALESCE(SUM(p.grandTotal), 0) FROM PurchaseOrderEntity p")
    Double sumAllGrandTotal();

    // Get total purchase value for a period
    @Query("SELECT COALESCE(SUM(p.grandTotal), 0) FROM PurchaseOrderEntity p WHERE p.poDate BETWEEN :startDate AND :endDate")
    Double sumGrandTotalBetweenDates(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);

    // Get recent purchases with limit
    @Query("SELECT p FROM PurchaseOrderEntity p ORDER BY p.poDate DESC")
    List<PurchaseOrderEntity> findRecentPurchases(Pageable pageable);

    // Check if PO number exists
    boolean existsByPoNo(String poNo);
}
