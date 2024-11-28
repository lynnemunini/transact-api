package com.grayseal.transactapi.service;

import com.grayseal.transactapi.dtos.CustomerReqDTO;
import com.grayseal.transactapi.dtos.CustomerResponseDTO;
import com.grayseal.transactapi.entity.Customer;
import com.grayseal.transactapi.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CustomerService {

    private final CustomerRepository customerRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public CustomerService(CustomerRepository customerRepository, PasswordEncoder passwordEncoder) {
        this.customerRepository = customerRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public CustomerResponseDTO createCustomer(CustomerReqDTO customerDTO) {
        Customer customer = new Customer();
        customer.setName(customerDTO.getName());
        customer.setEmail(customerDTO.getEmail());
        customer.setPhone(customerDTO.getPhone());
        customer.setId(customerDTO.getId());
        customer.setPassword(passwordEncoder.encode(customerDTO.getPassword()));
        Customer savedCustomer = customerRepository.save(customer);

        CustomerResponseDTO resp = new CustomerResponseDTO();
        resp.setId(savedCustomer.getId());
        resp.setName(savedCustomer.getName());
        resp.setEmail(savedCustomer.getEmail());
        resp.setPhone(savedCustomer.getPhone());
        return resp;
    }

    public Customer getCustomerById(String id) {
        Optional<Customer> customer = customerRepository.findById(id);
        if (customer.isPresent()) {
            return customer.get();
        } else throw new RuntimeException("Customer not found");
    }
}
