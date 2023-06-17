package com.example.bankingsystem.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Table(name = "exchange_rate_bank_accounts")
@Entity
@Getter
@Setter
public class ExchangeRateBankAccount {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "currecny_id", nullable = false)
    private Integer currecnyId;

    @Column(name = "bankaccount_id")
    private Integer bankaccountId;

}