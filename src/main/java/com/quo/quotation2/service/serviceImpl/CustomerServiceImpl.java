package com.quo.quotation2.service.serviceImpl;

import com.quo.quotation2.dto.requestdto.CustomerDto;
import com.quo.quotation2.entity.Customer;
import com.quo.quotation2.exception.CustomerNotFoundException;
import com.quo.quotation2.exception.QuotationException;
import com.quo.quotation2.repository.CustomerRepository;
import com.quo.quotation2.service.CustomerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@Transactional
public class CustomerServiceImpl implements CustomerService {

    private static final Logger logger = LoggerFactory.getLogger(CustomerServiceImpl.class);

    private final CustomerRepository customerRepository;

    public CustomerServiceImpl(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Override
    public Customer createCustomer(CustomerDto customerDto) {
        try {
            logger.info("Creating customer with name: {}", customerDto.getName());

            // Validate required fields
            if (customerDto.getName() == null || customerDto.getName().isBlank()) {
                throw new QuotationException("Customer name is required");
            }
            if (customerDto.getMobilePrimary() == null || customerDto.getMobilePrimary().isBlank()) {
                throw new QuotationException("Mobile number is required");
            }

            // Check if mobile number already exists
            if (customerRepository.existsByMobilePrimary(customerDto.getMobilePrimary())) {
                throw new QuotationException(
                        "Customer already exists with mobile number: " + customerDto.getMobilePrimary());
            }

            Customer customer = new Customer();
            customer.setCustomerCode(generateCustomerCode());
            customer.setName(customerDto.getName());
            customer.setCompany(customerDto.getCompany());
            customer.setMobilePrimary(customerDto.getMobilePrimary());
            customer.setMobileSecondary(customerDto.getMobileSecondary());
            customer.setEmail(customerDto.getEmail());
            customer.setAddress(customerDto.getAddress());
            customer.setBillingAddress(customerDto.getBillingAddress());
            customer.setSiteAddress(customerDto.getSiteAddress());
            customer.setCity(customerDto.getCity());
            customer.setState(customerDto.getState());
            customer.setStateCode(customerDto.getStateCode());
            customer.setPincode(customerDto.getPincode());
            customer.setGstin(customerDto.getGstin());
            customer.setType(customerDto.getType() != null ? customerDto.getType() : "Individual Buyer");
            customer.setLeadSource(customerDto.getLeadSource() != null ? customerDto.getLeadSource() : "Direct Enquiry");
            customer.setStatus(customerDto.getStatus() != null ? customerDto.getStatus() : "Active");
            customer.setRequirement(customerDto.getRequirement());
            customer.setSiteDetails(customerDto.getSiteDetails());
            customer.setNotes(customerDto.getNotes());
            customer.setTotalOrders(customerDto.getTotalOrders() != null ? customerDto.getTotalOrders() : 0);
            customer.setTotalBusiness(customerDto.getTotalBusiness() != null ? customerDto.getTotalBusiness() : 0.0);

            // Set dates
            String today = LocalDateTime.now().toString().substring(0, 10);
            customer.setLastActivity(customerDto.getLastActivity() != null ? customerDto.getLastActivity() : today);
            customer.setCreated(customerDto.getCreated() != null ? customerDto.getCreated() : today);
            customer.setCreatedAt(LocalDateTime.now());
            customer.setUpdatedAt(LocalDateTime.now());

            Customer savedCustomer = customerRepository.save(customer);
            logger.info("Customer created successfully with ID: {}", savedCustomer.getId());
            return savedCustomer;

        } catch (QuotationException e) {
            throw e;
        } catch (Exception e) {
            logger.error("Error creating customer: {}", e.getMessage(), e);
            throw new RuntimeException("Failed to create customer: " + e.getMessage(), e);
        }
    }

    @Override
    public Customer updateCustomer(Long id, CustomerDto customerDto) {
        try {
            logger.info("Updating customer with ID: {}", id);

            Customer customer = customerRepository.findById(id)
                    .orElseThrow(() -> new CustomerNotFoundException("Customer not found with ID: " + id));

            if (customerDto.getName() != null) customer.setName(customerDto.getName());
            if (customerDto.getCompany() != null) customer.setCompany(customerDto.getCompany());
            if (customerDto.getMobilePrimary() != null) customer.setMobilePrimary(customerDto.getMobilePrimary());
            if (customerDto.getMobileSecondary() != null) customer.setMobileSecondary(customerDto.getMobileSecondary());
            if (customerDto.getEmail() != null) customer.setEmail(customerDto.getEmail());
            if (customerDto.getAddress() != null) customer.setAddress(customerDto.getAddress());
            if (customerDto.getBillingAddress() != null) customer.setBillingAddress(customerDto.getBillingAddress());
            if (customerDto.getSiteAddress() != null) customer.setSiteAddress(customerDto.getSiteAddress());
            if (customerDto.getCity() != null) customer.setCity(customerDto.getCity());
            if (customerDto.getState() != null) customer.setState(customerDto.getState());
            if (customerDto.getStateCode() != null) customer.setStateCode(customerDto.getStateCode());
            if (customerDto.getPincode() != null) customer.setPincode(customerDto.getPincode());
            if (customerDto.getGstin() != null) customer.setGstin(customerDto.getGstin());
            if (customerDto.getType() != null) customer.setType(customerDto.getType());
            if (customerDto.getLeadSource() != null) customer.setLeadSource(customerDto.getLeadSource());
            if (customerDto.getStatus() != null) customer.setStatus(customerDto.getStatus());
            if (customerDto.getRequirement() != null) customer.setRequirement(customerDto.getRequirement());
            if (customerDto.getSiteDetails() != null) customer.setSiteDetails(customerDto.getSiteDetails());
            if (customerDto.getNotes() != null) customer.setNotes(customerDto.getNotes());
            if (customerDto.getTotalOrders() != null) customer.setTotalOrders(customerDto.getTotalOrders());
            if (customerDto.getTotalBusiness() != null) customer.setTotalBusiness(customerDto.getTotalBusiness());
            if (customerDto.getLastActivity() != null) customer.setLastActivity(customerDto.getLastActivity());
            if (customerDto.getCreated() != null) customer.setCreated(customerDto.getCreated());

            customer.setUpdatedAt(LocalDateTime.now());

            Customer updatedCustomer = customerRepository.save(customer);
            logger.info("Customer updated successfully with ID: {}", updatedCustomer.getId());
            return updatedCustomer;

        } catch (CustomerNotFoundException e) {
            throw e;
        } catch (Exception e) {
            logger.error("Error updating customer: {}", e.getMessage(), e);
            throw new RuntimeException("Failed to update customer: " + e.getMessage(), e);
        }
    }

    @Override
    public Customer patchCustomer(Long id, CustomerDto customerDto) {
        try {
            logger.info("Patching customer with ID: {}", id);

            Customer customer = customerRepository.findById(id)
                    .orElseThrow(() -> new CustomerNotFoundException("Customer not found with ID: " + id));

            if (customerDto.getMobilePrimary() != null && !customerDto.getMobilePrimary().isBlank()
                    && !customerDto.getMobilePrimary().equals(customer.getMobilePrimary())
                    && customerRepository.existsByMobilePrimary(customerDto.getMobilePrimary())) {
                throw new QuotationException(
                        "Customer already exists with mobile number: " + customerDto.getMobilePrimary());
            }

            if (customerDto.getName() != null) customer.setName(customerDto.getName());
            if (customerDto.getCompany() != null) customer.setCompany(customerDto.getCompany());
            if (customerDto.getMobilePrimary() != null) customer.setMobilePrimary(customerDto.getMobilePrimary());
            if (customerDto.getMobileSecondary() != null) customer.setMobileSecondary(customerDto.getMobileSecondary());
            if (customerDto.getEmail() != null) customer.setEmail(customerDto.getEmail());
            if (customerDto.getAddress() != null) customer.setAddress(customerDto.getAddress());
            if (customerDto.getBillingAddress() != null) customer.setBillingAddress(customerDto.getBillingAddress());
            if (customerDto.getSiteAddress() != null) customer.setSiteAddress(customerDto.getSiteAddress());
            if (customerDto.getCity() != null) customer.setCity(customerDto.getCity());
            if (customerDto.getState() != null) customer.setState(customerDto.getState());
            if (customerDto.getStateCode() != null) customer.setStateCode(customerDto.getStateCode());
            if (customerDto.getPincode() != null) customer.setPincode(customerDto.getPincode());
            if (customerDto.getGstin() != null) customer.setGstin(customerDto.getGstin());
            if (customerDto.getType() != null) customer.setType(customerDto.getType());
            if (customerDto.getLeadSource() != null) customer.setLeadSource(customerDto.getLeadSource());
            if (customerDto.getStatus() != null) customer.setStatus(customerDto.getStatus());
            if (customerDto.getRequirement() != null) customer.setRequirement(customerDto.getRequirement());
            if (customerDto.getSiteDetails() != null) customer.setSiteDetails(customerDto.getSiteDetails());
            if (customerDto.getNotes() != null) customer.setNotes(customerDto.getNotes());
            if (customerDto.getTotalOrders() != null) customer.setTotalOrders(customerDto.getTotalOrders());
            if (customerDto.getTotalBusiness() != null) customer.setTotalBusiness(customerDto.getTotalBusiness());
            if (customerDto.getLastActivity() != null) customer.setLastActivity(customerDto.getLastActivity());
            if (customerDto.getCreated() != null) customer.setCreated(customerDto.getCreated());

            customer.setUpdatedAt(LocalDateTime.now());

            Customer patchedCustomer = customerRepository.save(customer);
            logger.info("Customer patched successfully with ID: {}", patchedCustomer.getId());
            return patchedCustomer;

        } catch (CustomerNotFoundException | QuotationException e) {
            throw e;
        } catch (Exception e) {
            logger.error("Error patching customer: {}", e.getMessage(), e);
            throw new RuntimeException("Failed to patch customer: " + e.getMessage(), e);
        }
    }

    @Override
    public Customer getCustomer(Long id) {
        try {
            return customerRepository.findById(id)
                    .orElseThrow(() -> new CustomerNotFoundException("Customer not found with ID: " + id));
        } catch (CustomerNotFoundException e) {
            throw e;
        } catch (Exception e) {
            logger.error("Error getting customer: {}", e.getMessage(), e);
            throw new RuntimeException("Failed to get customer: " + e.getMessage(), e);
        }
    }

    @Override
    public List<Customer> getAllCustomers() {
        try {
            return customerRepository.findByDeletedAtIsNull();
        } catch (Exception e) {
            logger.error("Error getting all customers: {}", e.getMessage(), e);
            throw new RuntimeException("Failed to get customers: " + e.getMessage(), e);
        }
    }

    @Override
    public List<Customer> searchCustomersByName(String name) {
        try {
            return customerRepository.findByNameContainingIgnoreCaseAndDeletedAtIsNull(name);
        } catch (Exception e) {
            logger.error("Error searching customers: {}", e.getMessage(), e);
            throw new RuntimeException("Failed to search customers: " + e.getMessage(), e);
        }
    }

    @Override
    public void deleteCustomer(Long id) {
        try {
            logger.info("Deleting customer with ID: {}", id);

            Customer customer = customerRepository.findById(id)
                    .orElseThrow(() -> new CustomerNotFoundException("Customer not found with ID: " + id));
            customer.setDeletedAt(LocalDateTime.now());
            customerRepository.save(customer);

            logger.info("Customer deleted successfully with ID: {}", id);

        } catch (CustomerNotFoundException e) {
            throw e;
        } catch (Exception e) {
            logger.error("Error deleting customer: {}", e.getMessage(), e);
            throw new RuntimeException("Failed to delete customer: " + e.getMessage(), e);
        }
    }

    private String generateCustomerCode() {
        return "CUST-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
    }
}