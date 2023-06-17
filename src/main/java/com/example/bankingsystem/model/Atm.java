package com.example.bankingsystem.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Table(name = "atm")
@Entity
@Getter
@Setter
public class Atm {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "address", nullable = false)
    private String address;

    @Column(name = "balance", nullable = false, precision = 131089)
    private BigDecimal balance;

    @Column(name = "status", nullable = false, length = 20)
    private String status;
}