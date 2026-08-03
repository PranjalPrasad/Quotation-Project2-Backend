package com.quo.quotation2.service;


import com.quo.quotation2.dto.CustomerDto;
import com.quo.quotation2.entity.Customer;

import java.util.List;

public interface CustomerService {
    Customer createCustomer(CustomerDto customerDto);
    Customer updateCustomer(Long id, CustomerDto customerDto);
    Customer patchCustomer(Long id, CustomerDto customerDto);
    Customer getCustomer(Long id);
    List<Customer> getAllCustomers();
    List<Customer> searchCustomersByName(String name);
    void deleteCustomer(Long id);
}
