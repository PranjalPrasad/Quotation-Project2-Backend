package com.quo.quotation2.service.serviceImpl;
import com.quo.quotation2.dto.CustomerDto;
import com.quo.quotation2.entity.Customer;
import com.quo.quotation2.exception.CustomerNotFoundException;
import com.quo.quotation2.exception.QuotationException;
import com.quo.quotation2.repository.CustomerRepository;
import com.quo.quotation2.service.CustomerService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
@Service
@Transactional
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;

    public CustomerServiceImpl(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Override
    public Customer createCustomer(CustomerDto customerDto) {
        if (customerDto.getMobilePrimary() != null
                && customerRepository.existsByMobilePrimary(customerDto.getMobilePrimary())) {
            throw new QuotationException(
                    "Customer already exists with mobile number: " + customerDto.getMobilePrimary());
        }

        Customer customer = new Customer();
        customer.setCustomerCode(
                customerDto.getCustomerCode() != null && !customerDto.getCustomerCode().isBlank()
                        ? customerDto.getCustomerCode()
                        : generateCustomerCode());
        customer.setName(customerDto.getName());
        customer.setMobilePrimary(customerDto.getMobilePrimary());
        customer.setMobileSecondary(customerDto.getMobileSecondary());
        customer.setEmail(customerDto.getEmail());
        customer.setAddress(customerDto.getAddress());
        customer.setCity(customerDto.getCity());
        customer.setState(customerDto.getState());
        customer.setStateCode(customerDto.getStateCode());
        customer.setPincode(customerDto.getPincode());
        customer.setGstin(customerDto.getGstin());
        customer.setCreatedAt(LocalDateTime.now());
        customer.setUpdatedAt(LocalDateTime.now());

        return customerRepository.save(customer);
    }

    @Override
    public Customer updateCustomer(Long id, CustomerDto customerDto) {
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new CustomerNotFoundException("Customer not found with ID: " + id));

        customer.setName(customerDto.getName());
        customer.setMobilePrimary(customerDto.getMobilePrimary());
        customer.setMobileSecondary(customerDto.getMobileSecondary());
        customer.setEmail(customerDto.getEmail());
        customer.setAddress(customerDto.getAddress());
        customer.setCity(customerDto.getCity());
        customer.setState(customerDto.getState());
        customer.setStateCode(customerDto.getStateCode());
        customer.setPincode(customerDto.getPincode());
        customer.setGstin(customerDto.getGstin());
        customer.setUpdatedAt(LocalDateTime.now());

        return customerRepository.save(customer);
    }

    @Override
    public Customer patchCustomer(Long id, CustomerDto customerDto) {
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new CustomerNotFoundException("Customer not found with ID: " + id));

        if (customerDto.getMobilePrimary() != null && !customerDto.getMobilePrimary().isBlank()
                && !customerDto.getMobilePrimary().equals(customer.getMobilePrimary())
                && customerRepository.existsByMobilePrimary(customerDto.getMobilePrimary())) {
            throw new QuotationException(
                    "Customer already exists with mobile number: " + customerDto.getMobilePrimary());
        }

        if (customerDto.getName() != null) customer.setName(customerDto.getName());
        if (customerDto.getMobilePrimary() != null) customer.setMobilePrimary(customerDto.getMobilePrimary());
        if (customerDto.getMobileSecondary() != null) customer.setMobileSecondary(customerDto.getMobileSecondary());
        if (customerDto.getEmail() != null) customer.setEmail(customerDto.getEmail());
        if (customerDto.getAddress() != null) customer.setAddress(customerDto.getAddress());
        if (customerDto.getCity() != null) customer.setCity(customerDto.getCity());
        if (customerDto.getState() != null) customer.setState(customerDto.getState());
        if (customerDto.getStateCode() != null) customer.setStateCode(customerDto.getStateCode());
        if (customerDto.getPincode() != null) customer.setPincode(customerDto.getPincode());
        if (customerDto.getGstin() != null) customer.setGstin(customerDto.getGstin());
        customer.setUpdatedAt(LocalDateTime.now());

        return customerRepository.save(customer);
    }

    @Override
    public Customer getCustomer(Long id) {
        return customerRepository.findById(id)
                .orElseThrow(() -> new CustomerNotFoundException("Customer not found with ID: " + id));
    }

    @Override
    public List<Customer> getAllCustomers() {
        return customerRepository.findByDeletedAtIsNull();
    }

    @Override
    public List<Customer> searchCustomersByName(String name) {
        return customerRepository.findByNameContainingIgnoreCaseAndDeletedAtIsNull(name);
    }

    @Override
    public void deleteCustomer(Long id) {
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new CustomerNotFoundException("Customer not found with ID: " + id));

        customer.setDeletedAt(LocalDateTime.now());
        customerRepository.save(customer);
    }

    private String generateCustomerCode() {
        return "CUST-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
    }
}