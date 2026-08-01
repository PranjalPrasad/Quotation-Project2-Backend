package com.quo.quotation2.repository;

import com.quo.quotation2.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
    Optional<Customer> findByMobilePrimary(String mobilePrimary);
    Optional<Customer> findByCustomerCode(String customerCode);
    boolean existsByMobilePrimary(String mobilePrimary);
}
