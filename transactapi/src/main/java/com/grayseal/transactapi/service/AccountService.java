package com.grayseal.transactapi.service;

import com.grayseal.transactapi.dtos.AccountReqDTO;
import com.grayseal.transactapi.dtos.AccountResponseDTO;
import com.grayseal.transactapi.entity.Account;
import com.grayseal.transactapi.entity.Customer;
import com.grayseal.transactapi.entity.Transaction;
import com.grayseal.transactapi.repository.AccountRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

@Service
@Slf4j
public class AccountService {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private TransactionService transactionService;

    @Autowired
    private CustomerService customerService;

    public AccountResponseDTO createAccount(AccountReqDTO accountDTO) {
        Account account = new Account();

        account.setId(accountDTO.getId());
        account.setAccountNumber(accountDTO.getAccountNumber());
        account.setBalance(accountDTO.getBalance());

        // Retrieve the associated customer
        Customer customer = customerService.getCustomerById(accountDTO.getCustomerId());
        account.setCustomer(customer);
        Account savedAccount = accountRepository.save(account);

        AccountResponseDTO resp = new AccountResponseDTO();
        resp.setId(savedAccount.getId());
        resp.setAccountNumber(savedAccount.getAccountNumber());
        resp.setCustomerId(savedAccount.getCustomer().getId());

        return resp;
    }

    public String getAccountBalance(String accountNumber) {
        Optional<Account> account = accountRepository.findByAccountNumber(accountNumber);
        if (account.isPresent()) {
            return account.get().getBalance();
        } else throw new RuntimeException("Account not found");
    }

    @Transactional
    public boolean transferFunds(String sourceAccountNumber, String destinationAccountNumber, String amount) {
        String referenceNo = UUID.randomUUID().toString();
        Optional<Account> sourceAccount = accountRepository.findByAccountNumber(sourceAccountNumber);
        Optional<Account> destinationAccount = accountRepository.findByAccountNumber(destinationAccountNumber);
        if (sourceAccount.isPresent()) {
            Account source = sourceAccount.get();
            if (destinationAccount.isPresent()) {
                Account destination = destinationAccount.get();
                // Check if source account has enough balance
                double srcBalance = Double.parseDouble(source.getBalance());
                if (srcBalance >= Double.parseDouble(amount)) {
                    // DO FT
                    double balance = srcBalance - Double.parseDouble(amount);
                    log.error("THIS HAS BEEN REACHED {}", srcBalance);
                    source.setBalance(String.valueOf(balance));
                    log.error("NEW SRC ACCOUNT {}", sourceAccount.get());
                    destination.setBalance(String.valueOf(Double.parseDouble(destination.getBalance()) + Double.parseDouble(amount)));

                    // Update accounts
                    accountRepository.save(source);
                    accountRepository.save(destination);

                } else throw new RuntimeException("Insufficient funds");

            } else throw new RuntimeException("Destination account not found");
        } else throw new RuntimeException("Source account not found");

        // Persist Debit transaction
        Transaction debitTransaction = transactionService.createTransaction("DEBIT", amount, sourceAccountNumber, destinationAccountNumber, referenceNo, sourceAccount.get());
        log.error("Debit Transaction {}", debitTransaction);
        // Persist Credit transaction
        Transaction creditTransaction = transactionService.createTransaction("CREDIT", amount, sourceAccountNumber, destinationAccountNumber, referenceNo, destinationAccount.get());
        log.error("Credit Transaction {}", creditTransaction);
        return debitTransaction != null && creditTransaction != null;
    }
}
