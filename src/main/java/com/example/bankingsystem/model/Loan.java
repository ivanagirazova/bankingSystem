package com.example.bankingsystem.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Table(name = "loan")
@Entity
@Getter
@Setter
public class Loan {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "amountborrowed", nullable = false)
    private Integer amountborrowed;

    @Column(name = "amountowed", nullable = false)
    private Integer amountowed;

    @Column(name = "type", nullable = false, length = 30)
    private String type;

    @Column(name = "startdate", nullable = false)
    private LocalDate startdate;

    @Column(name = "enddate", nullable = false)
    private LocalDate enddate;

    @ManyToOne(optional = false)
    @JoinColumn(name = "customerid", nullable = false)
    private Customer customerid;

    @ManyToOne(optional = false)
    @JoinColumn(name = "responsibleempid", nullable = false)
    private Employee responsibleempid;

    @ManyToOne(optional = false)
    @JoinColumn(name = "accountid", nullable = false)
    private Account accountid;
}