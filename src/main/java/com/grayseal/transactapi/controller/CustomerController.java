package com.grayseal.transactapi.controller;

import com.grayseal.transactapi.dtos.CustomerReqDTO;
import com.grayseal.transactapi.dtos.CustomerResponseDTO;
import com.grayseal.transactapi.service.CustomerService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/customers")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @PostMapping("/register")
    public ResponseEntity<CustomerResponseDTO> createCustomer(@RequestBody @Valid CustomerReqDTO customer) {
        CustomerResponseDTO customerResponseDTO = customerService.createCustomer(customer);
        return ResponseEntity.status(HttpStatus.CREATED).body(customerResponseDTO);
    }
}