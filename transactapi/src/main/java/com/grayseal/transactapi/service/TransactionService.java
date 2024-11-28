package com.grayseal.transactapi.service;

import com.grayseal.transactapi.entity.Account;
import com.grayseal.transactapi.entity.Transaction;
import com.grayseal.transactapi.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class TransactionService {

    @Autowired
    private TransactionRepository transactionRepository;

    public Transaction createTransaction(String transactionType, String amount, String sourceAccount, String destinationAccount, String referenceNo, Account account) {
        Transaction transaction = new Transaction();
        transaction.setAmount(amount);
        transaction.setSourceAccount(sourceAccount);
        transaction.setDestinationAccount(destinationAccount);
        transaction.setReferenceNo(referenceNo);
        transaction.setTimestamp(LocalDateTime.now());
        transaction.setAccount(account);
        transaction.setTransactionType(transactionType);
        transactionRepository.save(transaction);
        return transaction;
    }
}