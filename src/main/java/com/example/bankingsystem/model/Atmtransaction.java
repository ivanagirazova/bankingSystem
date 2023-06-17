package com.example.bankingsystem.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Table(name = "atmtransaction")
@Entity
@Getter
@Setter
public class Atmtransaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "type", nullable = false, length = 30)
    private String type;

    @ManyToOne(optional = false)
    @JoinColumn(name = "cardid", nullable = false)
    private Card cardid;

    @ManyToOne(optional = false)
    @JoinColumn(name = "atmid", nullable = false)
    private Atm atmid;

    @ManyToOne(optional = false)
    @JoinColumn(name = "accounttransactionid", nullable = false)
    private Accounttransaction accounttransactionid;

    public Accounttransaction getAccounttransactionid() {
        return accounttransactionid;
    }

    public void setAccounttransactionid(Accounttransaction accounttransactionid) {
        this.accounttransactionid = accounttransactionid;
    }
}