package com.example.bankingsystem.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Table(name = "exchangerate")
@Entity
@Getter
@Setter
public class Exchangerate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "Date", nullable = false)
    private LocalDate date;

    @Column(name = "exchangerate", nullable = false, precision = 131089)
    private BigDecimal exchangerate;

    @ManyToOne(optional = false)
    @JoinColumn(name = "currencyid", nullable = false)
    private Currency currencyid;

    @ManyToOne(optional = false)
    @JoinColumn(name = "basecurrency", nullable = false)
    private Currency basecurrency;
}