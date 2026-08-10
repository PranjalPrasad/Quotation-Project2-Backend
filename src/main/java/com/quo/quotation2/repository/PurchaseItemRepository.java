package com.quo.quotation2.repository;

import com.quo.quotation2.entity.PurchaseItemEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface PurchaseItemRepository extends JpaRepository<PurchaseItemEntity, Long> {

    // Find all items by purchase order ID
    List<PurchaseItemEntity> findByPurchaseOrderId(Long purchaseOrderId);

    // Delete all items by purchase order ID
    @Modifying
    @Transactional
    @Query("DELETE FROM PurchaseItemEntity pi WHERE pi.purchaseOrder.id = :purchaseOrderId")
    void deleteByPurchaseOrderId(@Param("purchaseOrderId") Long purchaseOrderId);

    // Get total amount by purchase order ID
    @Query("SELECT COALESCE(SUM(pi.amount), 0) FROM PurchaseItemEntity pi WHERE pi.purchaseOrder.id = :purchaseOrderId")
    Double sumAmountByPurchaseOrderId(@Param("purchaseOrderId") Long purchaseOrderId);

    // Count items by purchase order ID
    @Query("SELECT COUNT(pi) FROM PurchaseItemEntity pi WHERE pi.purchaseOrder.id = :purchaseOrderId")
    Long countItemsByPurchaseOrderId(@Param("purchaseOrderId") Long purchaseOrderId);

    // Find items by product name (search)
    @Query("SELECT pi FROM PurchaseItemEntity pi WHERE LOWER(pi.name) LIKE LOWER(CONCAT('%', :name, '%'))")
    List<PurchaseItemEntity> findByNameContaining(@Param("name") String name);

    // Find items by HSN code
    List<PurchaseItemEntity> findByHsnCode(String hsnCode);
}
