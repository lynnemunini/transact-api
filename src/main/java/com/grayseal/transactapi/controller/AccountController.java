package com.grayseal.transactapi.controller;

import com.grayseal.transactapi.dtos.AccountReqDTO;
import com.grayseal.transactapi.dtos.AccountResponseDTO;
import com.grayseal.transactapi.dtos.FundsTransferReqDTO;
import com.grayseal.transactapi.service.AccountService;
import com.grayseal.transactapi.service.RabbitMqProducer;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/accounts")
public class AccountController {


    private final AccountService accountService;


    private final RabbitMqProducer rabbitMqProducer;

    public AccountController(AccountService accountService, RabbitMqProducer rabbitMqProducer) {
        this.accountService = accountService;
        this.rabbitMqProducer = rabbitMqProducer;
    }

    @PostMapping("/create")
    public ResponseEntity<AccountResponseDTO> createAccount(@RequestBody @Valid AccountReqDTO accountReqDTO) {
        try {
            AccountResponseDTO resp = accountService.createAccount(accountReqDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(resp);
        } catch (Exception e) {
            // log to file
            log.error("Error creating account: {}", e.getMessage(), e);
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    @GetMapping("/{accountNumber}/balance")
    public ResponseEntity<String> getAccountBalance(@PathVariable("accountNumber") String accountNumber) {
        try {
            String balance = accountService.getAccountBalance(accountNumber);
            return ResponseEntity.status(HttpStatus.OK).body(balance);
        } catch (Exception e) {
            // log to file
            log.error("Error fetching account balance: {}", e.getMessage(), e);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @PostMapping("/fundstransfer")
    public ResponseEntity<String> transferFunds(@RequestBody @Valid FundsTransferReqDTO fundsTransferReqDTO) {
        try {
            // Do FT and send transaction to rabbit mq if FT is successful
            boolean ftStatus = accountService.transferFunds(fundsTransferReqDTO.getSourceAccountNumber(), fundsTransferReqDTO.getDestinationAccountNumber(), fundsTransferReqDTO.getAmount());
            if (ftStatus) {
                String transactionDetails = String.format(
                        "Transaction successful: From %s to %s, Amount: %s",
                        fundsTransferReqDTO.getSourceAccountNumber(),
                        fundsTransferReqDTO.getDestinationAccountNumber(),
                        fundsTransferReqDTO.getAmount()
                );
                rabbitMqProducer.sendTransactionMessage(transactionDetails);
                ResponseEntity.status(HttpStatus.OK).body("Funds transferred successfully");
            }
        } catch (Exception e) {
            // Log to file
            log.error("Error transferring funds: {}", e.getMessage(), e);
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Funds transfer failed");
    }
}