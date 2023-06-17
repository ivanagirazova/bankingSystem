package com.example.bankingsystem.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Table(name = "accounttransaction", indexes = {
        @Index(name = "idx_account_customeridfrom", columnList = "accountfrom"),
        @Index(name = "idx_account_customeridto", columnList = "accountto")
})
@Entity
@Getter
@Setter
public class Accounttransaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "type", nullable = false, length = 30)
    private String type;

    @Column(name = "Date", nullable = false)
    private LocalDate date;

    @Column(name = "amount", nullable = false)
    private Integer amount;

    @ManyToOne(optional = false)
    @JoinColumn(name = "currencyid", nullable = false)
    private Currency currencyid;

    @ManyToOne(optional = false)
    @JoinColumn(name = "accountfrom", nullable = false)
    private Account accountfrom;

    @ManyToOne(optional = false)
    @JoinColumn(name = "accountto", nullable = false)
    private Account accountto;
}