package com.quo.quotation2.controller;
import com.quo.quotation2.dto.requestdto.CustomerDto;
import com.quo.quotation2.dto.responsedto.ApiResponseDto;
import com.quo.quotation2.entity.Customer;
import com.quo.quotation2.service.CustomerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/customers")
public class CustomerController {

    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    // CREATE Customer
    @PostMapping("/create-customer")
    public ResponseEntity<ApiResponseDto<Customer>> createCustomer(@RequestBody CustomerDto customerDto) {
        Customer customer = customerService.createCustomer(customerDto);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponseDto.success("Customer created successfully", customer));
    }

    // UPDATE Customer
    @PutMapping("/update-customer/{id}")
    public ResponseEntity<ApiResponseDto<Customer>> updateCustomer(
            @PathVariable Long id,
            @RequestBody CustomerDto customerDto) {
        Customer customer = customerService.updateCustomer(id, customerDto);
        return ResponseEntity.ok(ApiResponseDto.success("Customer updated successfully", customer));
    }

    // PATCH Customer (partial update)
    @PatchMapping("/patch-customer/{id}")
    public ResponseEntity<ApiResponseDto<Customer>> patchCustomer(
            @PathVariable Long id,
            @RequestBody CustomerDto customerDto) {
        Customer customer = customerService.patchCustomer(id, customerDto);
        return ResponseEntity.ok(ApiResponseDto.success("Customer updated successfully", customer));
    }

    // GET Customer by ID
    @GetMapping("/get-customer/{id}")
    public ResponseEntity<ApiResponseDto<Customer>> getCustomer(@PathVariable Long id) {
        Customer customer = customerService.getCustomer(id);
        return ResponseEntity.ok(ApiResponseDto.success("Customer retrieved successfully", customer));
    }

    // GET All Customers (optionally filter by name)
    @GetMapping("/get-all-customers")
    public ResponseEntity<ApiResponseDto<List<Customer>>> getAllCustomers(
            @RequestParam(required = false) String name) {
        List<Customer> customers = (name != null && !name.isBlank())
                ? customerService.searchCustomersByName(name)
                : customerService.getAllCustomers();
        return ResponseEntity.ok(ApiResponseDto.success("Customers retrieved successfully", customers));
    }

    // DELETE Customer (Soft Delete)
    @DeleteMapping("/delete-customer/{id}")
    public ResponseEntity<ApiResponseDto<Void>> deleteCustomer(@PathVariable Long id) {
        customerService.deleteCustomer(id);
        return ResponseEntity.ok(ApiResponseDto.success("Customer deleted successfully", null));
    }
}