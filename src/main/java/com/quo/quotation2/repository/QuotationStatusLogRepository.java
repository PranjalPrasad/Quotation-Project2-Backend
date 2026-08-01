package com.quo.quotation2.repository;

import com.quo.quotation2.entity.QuotationStatusLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QuotationStatusLogRepository extends JpaRepository<QuotationStatusLog, Long> {
}
