package com.grayseal.transactapi.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Data
public class Account {

    @Id
    private String id;

    @Column(columnDefinition = "TEXT")
    private String accountNumber;

    @Column(columnDefinition = "TEXT")
    private String balance;

    // A customer can have many accounts
    @ManyToOne
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;

    // An account has many transactions
    @OneToMany(mappedBy = "account", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Transaction> transactions;
}
