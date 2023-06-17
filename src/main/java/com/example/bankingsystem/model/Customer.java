package com.example.bankingsystem.model;

import com.example.bankingsystem.model.Onlinebanking;
import com.example.bankingsystem.model.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Table(name = "customer", indexes = {
        @Index(name = "uniquecustomer", columnList = "userid", unique = true)
})
@Entity
@Getter
@Setter
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "userid", nullable = false)
    private User userid;

    @ManyToOne
    @JoinColumn(name = "onlinebankingid")
    private Onlinebanking onlinebankingid;
}