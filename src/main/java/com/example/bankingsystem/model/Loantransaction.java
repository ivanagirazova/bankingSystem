package com.example.bankingsystem.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Table(name = "loantransaction", indexes = {
        @Index(name = "idx_account_customeridloan", columnList = "accountfrom")
})
@Entity
@Getter
@Setter
public class Loantransaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "Date", nullable = false)
    private LocalDate date;

    @Column(name = "amount", nullable = false)
    private Integer amount;

    @Column(name = "principalamount", nullable = false)
    private Integer principalamount;

    @Column(name = "interestamount", nullable = false)
    private Integer interestamount;

    @ManyToOne(optional = false)
    @JoinColumn(name = "loanid", nullable = false)
    private Loan loanid;

    @ManyToOne(optional = false)
    @JoinColumn(name = "accountfrom", nullable = false)
    private Account accountfrom;

}