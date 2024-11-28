package com.grayseal.transactapi.repository;

import com.grayseal.transactapi.entity.Customer;
import org.springframework.data.repository.CrudRepository;

public interface CustomerRepository extends CrudRepository<Customer, String> {
    Customer findByEmail(String email);
}