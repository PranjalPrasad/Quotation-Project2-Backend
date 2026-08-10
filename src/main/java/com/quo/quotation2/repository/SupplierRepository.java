package com.quo.quotation2.repository;

import com.quo.quotation2.entity.SupplierEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SupplierRepository extends JpaRepository<SupplierEntity, Long> {

    // Find by GSTIN
    Optional<SupplierEntity> findByGstin(String gstin);

    // Find by Mobile
    Optional<SupplierEntity> findByMobile(String mobile);

    // Find by Email
    Optional<SupplierEntity> findByEmail(String email);

    // Search by name (contains)
    List<SupplierEntity> findByNameContainingIgnoreCase(String name);

    // Find by Status
    List<SupplierEntity> findByStatus(String status);

    // Find by State
    List<SupplierEntity> findByState(String state);

    // Search with pagination
    @Query("SELECT s FROM SupplierEntity s WHERE " +
            "(:search IS NULL OR LOWER(s.name) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
            "LOWER(s.gstin) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
            "LOWER(s.mobile) LIKE LOWER(CONCAT('%', :search, '%'))) AND " +
            "(:status IS NULL OR s.status = :status) AND " +
            "(:state IS NULL OR LOWER(s.state) = LOWER(:state))")
    Page<SupplierEntity> searchSuppliers(@Param("search") String search,
                                         @Param("status") String status,
                                         @Param("state") String state,
                                         Pageable pageable);

    // Count by Status
    @Query("SELECT COUNT(s) FROM SupplierEntity s WHERE s.status = :status")
    Long countByStatus(@Param("status") String status);

    // Check if GSTIN exists
    boolean existsByGstin(String gstin);

    // Check if mobile exists
    boolean existsByMobile(String mobile);

    // Get active suppliers
    @Query("SELECT s FROM SupplierEntity s WHERE s.status = 'Active' ORDER BY s.name ASC")
    List<SupplierEntity> findActiveSuppliers();

    // Get all suppliers with name and id only (for dropdown)
    @Query("SELECT s.id, s.name FROM SupplierEntity s WHERE s.status = 'Active' ORDER BY s.name ASC")
    List<Object[]> findActiveSupplierNames();
}
