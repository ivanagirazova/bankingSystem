package com.example.bankingsystem.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.Instant;

@Table(name = "foreignexchangetransaction")
@Entity
@Getter
@Setter
public class Foreignexchangetransaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "Date", nullable = false)
    private Instant date;

    @Column(name = "amount", nullable = false, precision = 131089)
    private BigDecimal amount;

    @Column(name = "convertedamount", nullable = false, precision = 131089)
    private BigDecimal convertedamount;

    @ManyToOne(optional = false)
    @JoinColumn(name = "fromcuurency", nullable = false)
    private Currency fromcuurency;

    @ManyToOne(optional = false)
    @JoinColumn(name = "tocurrency", nullable = false)
    private Currency tocurrency;

    @ManyToOne(optional = false)
    @JoinColumn(name = "fromexchangerate", nullable = false)
    private Exchangerate fromexchangerate;

    @ManyToOne(optional = false)
    @JoinColumn(name = "toexchangerate", nullable = false)
    private Exchangerate toexchangerate;

    @ManyToOne(optional = false)
    @JoinColumn(name = "accountidfrom", nullable = false)
    private Account accountidfrom;

    @Column(name = "accountidto", nullable = false)
    private Integer accountidto;
}