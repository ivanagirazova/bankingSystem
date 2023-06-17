package com.example.bankingsystem.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Table(name = "account", indexes = {
        @Index(name = "account_accountnumber_key", columnList = "accountnumber", unique = true),
        @Index(name = "idx_customer_customeridto", columnList = "customerid")
})
@Entity
@Getter
@Setter
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "accountnumber", nullable = false, unique = true, length = 15)
    private String accountnumber;

    @Column(name = "accounttype", nullable = false, length = 50)
    private String accounttype;

    @Column(name = "balance", nullable = false, precision = 131089)
    private BigDecimal balance;

    @ManyToOne(optional = false)
    @JoinColumn(name = "customerid", nullable = false)
    private Customer customerid;

    @ManyToOne(optional = false)
    @JoinColumn(name = "currencyid", nullable = false)
    private Currency currencyid;

    @ManyToOne(optional = false)
    @JoinColumn(name = "branchid", nullable = false)
    private Branch branchid;
}