package com.grayseal.transactapi.repository;

import com.grayseal.transactapi.entity.Account;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AccountRepository extends CrudRepository<Account, String> {
    Optional<Account> findByAccountNumber(String accountNumber);
}