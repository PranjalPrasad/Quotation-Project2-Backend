package com.quo.quotation2.repository;

import com.quo.quotation2.entity.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<ProductEntity, Long> {

    Optional<ProductEntity> findBySku(String sku);

    List<ProductEntity> findByCategory(String category);

    List<ProductEntity> findByStatus(String status);

    @Query("SELECT p FROM ProductEntity p WHERE p.stock <= p.threshold")
    List<ProductEntity> findLowStockProducts();

    @Query("SELECT p FROM ProductEntity p WHERE LOWER(p.name) LIKE LOWER(CONCAT('%', :keyword, '%')) OR LOWER(p.description) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    List<ProductEntity> searchByKeyword(@Param("keyword") String keyword);

    boolean existsBySku(String sku);
}