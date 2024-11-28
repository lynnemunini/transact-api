package com.grayseal.transactapi.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
public class Transaction {

    @Id
    private String id;

    @Column(columnDefinition = "TEXT")
    private String transactionType;

    @Column(columnDefinition = "TEXT")
    private String amount;

    @Column(columnDefinition = "TEXT")
    private String sourceAccount;

    @Column(columnDefinition = "TEXT")
    private String destinationAccount;

    @Column(columnDefinition = "TEXT")
    private String referenceNo;

    @Column(nullable = false)
    private LocalDateTime timestamp;

    // An account has many transactions
    @ManyToOne
    @JoinColumn(name = "account_id", nullable = false)
    private Account account;
}
