package com.grayseal.transactapi.repository;

import com.grayseal.transactapi.entity.Transaction;
import org.springframework.data.repository.CrudRepository;

public interface TransactionRepository extends CrudRepository<Transaction, String> {
}