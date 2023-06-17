package com.example.bankingsystem.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Table(name = "card", indexes = {
        @Index(name = "card_cardnumber_key", columnList = "cardnumber", unique = true)
})
@Entity
@Getter
@Setter
public class Card {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "cardnumber", nullable = false, unique = true, length = 16)
    private String cardnumber;

    @Column(name = "cardtype", nullable = false, length = 50)
    private String cardtype;

    @Column(name = "ccv", nullable = false, length = 3)
    private String ccv;

    @Column(name = "expiredate", nullable = false)
    private LocalDate expiredate;

    @ManyToOne(optional = false)
    @JoinColumn(name = "accountid", nullable = false)
    private Account accountid;
}